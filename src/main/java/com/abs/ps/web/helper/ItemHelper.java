package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.util.StringHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abs.core.paging.IPaging;
import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.Department;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ItemDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.json.ItemOptDto;
import com.abs.ps.web.dto.json.ItemPriDto;
import com.abs.ps.web.dto.json.OptionDto;

public class ItemHelper implements IControllerHelper{
	private Logger logger=Logger.getLogger(ItemHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private BaseInfoService baseInfoService;
	private ActionLogHelper actionLogHelper = null;
	
	public ItemHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.baseInfoService = (BaseInfoService) factory.getBeanByName("baseInfoService");
		actionLogHelper = new ActionLogHelper(request, response, context,10);
	}
	
	public void getItems() throws ServletException, IOException{
		String name = request.getParameter("name");
		String typeOidStr =  request.getParameter("type_oid");
		long typeOid = 0;
		if (!StringHelper.isEmpty(typeOidStr)) {
			typeOid = FilterUtil.filterString2Long(typeOidStr);
		}
		List<ItemInfo> lists = baseInfoService.findItems(name, typeOid);
		List<JSONObject> optionDtos = new ArrayList<>();
		for (ItemInfo obj : lists) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("value", obj.getOid().toString());
			jsonObj.put("text", obj.getName());
			jsonObj.put("itemCode", obj.getCode());
			jsonObj.put("itemColor", obj.getColor());
			if (sessionUserDto.isAdmin()) {
				jsonObj.put("unitPrice", obj.getUnitPrice());
			}
			optionDtos.add(jsonObj);
		}
		
		String resutlStr = JsonUtils.toJSONString(optionDtos);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	public void getItemsAndColor() throws ServletException, IOException{
		String name = request.getParameter("name");
		String typeOidStr =  request.getParameter("type_oid");
		long typeOid = 0;
		if (!StringHelper.isEmpty(typeOidStr)) {
			typeOid = Long.parseLong(typeOidStr);
		}
		List<ItemInfo> lists = baseInfoService.findItems(name, typeOid);
		List<ItemOptDto> optionDtos = new ArrayList<ItemOptDto>();
//		optionDtos.add(new ItemOptDto());
		for (ItemInfo obj : lists) {
			ItemOptDto dto = new ItemOptDto();
			dto.setValue(obj.getOid().toString());
			dto.setText(obj.getName());
			dto.setColor(obj.getColor());
			optionDtos.add(dto);
		}
		String resutlStr = JsonUtils.toJSONString(optionDtos);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	public void getItemsPrice() throws ServletException, IOException{
		String name = request.getParameter("name");
		String oid =  request.getParameter("oid");
		long Oid = 0;
		if (!StringHelper.isEmpty(oid)) {
			Oid = Long.parseLong(oid);
		}
		List<ItemInfo> lists = baseInfoService.findItemsPrice(name, Oid);
		List<ItemPriDto> optionDtos = new ArrayList<ItemPriDto>();
		for (ItemInfo obj : lists) {
			ItemPriDto dto = new ItemPriDto();
			dto.setValue(obj.getOid().toString());
			dto.setText(obj.getName());
			if(obj.getUnitPrice() != null) {
				dto.setUnitPrice(obj.getUnitPrice().toString());
			}
			if(obj.getDamPrice()!=null){
				dto.setDamPrice(obj.getDamPrice().toString());
			}
			if(obj.getMatPrice()!=null){
				dto.setMatPrice(obj.getMatPrice().toString());
			}
			if(obj.getPiePrice()!=null){
				dto.setPiePrice(obj.getPiePrice().toString());		
			}
			
			optionDtos.add(dto);
		}
		String resutlStr = JsonUtils.toJSONString(optionDtos);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}

	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					
					ItemInfo itemInfo =  (ItemInfo)baseInfoService.getEntityByOid(ItemInfo.class, Long.parseLong(delId));
					
					actionLogHelper.saveActionLog(
							actionLogHelper.generateActionLog(
								ActionLogHelper.ACTION_TYPE_DELETE,
								QueueConstants.AM_ITEM,
								"物品名称:" + itemInfo.getName(),
								""
							)
						);
					
					baseInfoService.delete(ItemInfo.class, Long.parseLong(delId));
				}
			} else {
				ItemInfo itemInfo =  (ItemInfo)baseInfoService.getEntityByOid(ItemInfo.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_ITEM,
							"物品名称:" + itemInfo.getName(),
							""
						)
					);
				baseInfoService.delete(ItemInfo.class, Long.parseLong(deleteIds));
			}
		}
		
		doQuery();
		
	}

	@Override
	public void doSave() throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String typeOid = request.getParameter("typeOid");
		String model = request.getParameter("model");
		String color = request.getParameter("color");
		String spec = request.getParameter("spec");
		String unit = request.getParameter("unit");
		String unitPrice = request.getParameter("unitPrice");
		String damPrice = request.getParameter("damPrice");
		String matPrice = request.getParameter("matPrice");
		String piePrice = request.getParameter("piePrice");
		String safeAmt = request.getParameter("safeAmt");
		String supplierOid = request.getParameter("supplierOid");
		String customerOid = request.getParameter("customerOid");
		String status = request.getParameter("status");
		
		ItemInfo item = null;
		if (StringHelper.isEmpty(oid)) {
			item = new ItemInfo();
			if (sessionUserDto != null) {
				if (sessionUserDto != null) {
					item.setCreateBy(this.sessionUserDto.getUserId());
					
				}
			}
			item.setCreateDate(new Date());
		} else {
			item = (ItemInfo) baseInfoService.getEntityByOid(ItemInfo.class,Long.parseLong(oid));
			
		}

		if (sessionUserDto != null) {
			item.setLastModifyBy(this.sessionUserDto.getUserId());
		}
		
		item.setCode(code);
		item.setColor(color);
		if (!StringHelper.isEmpty(customerOid)) {
			item.setCustomerOid(Long.parseLong(customerOid));
		}
		item.setModel(model);
		item.setName(name);
		if (!StringHelper.isEmpty(safeAmt)) {
			item.setSafeAmt(Double.parseDouble(safeAmt));
		}
		item.setSpec(spec);
		item.setStatus(status);
		if (!StringHelper.isEmpty(supplierOid)) {
			item.setSupplierOid(Long.parseLong(supplierOid));
		}
		
		if (!StringHelper.isEmpty(typeOid)) {
			item.setTypeOid(Long.parseLong(typeOid));
		}
		item.setUnit(unit);
		if (!StringHelper.isEmpty(unitPrice)) {
			item.setUnitPrice(Double.parseDouble(unitPrice));
		}
		if(!StringHelper.isEmpty(damPrice)){
			item.setDamPrice(Double.parseDouble(damPrice));
		}
		if(!StringHelper.isEmpty(matPrice)){
			item.setMatPrice(Double.parseDouble(matPrice));
		}
		if(!StringHelper.isEmpty(piePrice)){
			item.setPiePrice(Double.parseDouble(piePrice));
		}
		
		if(StringHelper.isEmpty(oid)){
			actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_ADD,
						QueueConstants.AM_ITEM,
						"物品名称:" + name,
						""
					)
				);
			
		}else{
			String oldCode = request.getParameter("oldCode");
			ItemInfo oldItem = baseInfoService.getItembyCode(oldCode);
			Map<String,String> map = FieldValueComparator.compareObject(item,oldItem);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_MODIFY,
						QueueConstants.AM_ITEM,
						values[0],
						values[1]
					)
				);
		}
		baseInfoService.saveObject(item);
		
		doQuery();
	}

	@Override
	public void doQuery() throws ServletException, IOException {
		String pageNumStr = request.getParameter("pageIndex");
		int pageNumber = 1;
		if (!StringHelper.isEmpty(pageNumStr)) {
			pageNumber = Integer.parseInt(pageNumStr);
		}
		
		String pageSizeStr = request.getParameter("limit");
		int pageSize = 10;
		if (!StringHelper.isEmpty(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		String search_typeOid = request.getParameter("search_typeOid");
		String search_name = request.getParameter("search_name");
		
		
		ItemDto criteria = new ItemDto();
		criteria.setTypeOid(search_typeOid);
		criteria.setName(search_name);
		
		IPaging page = baseInfoService.findItemsByPaging(pageNumber, pageSize, criteria);
		ListResult<ItemDto> result = new ListResult<ItemDto>();
		result.setResults("0");
		if (page != null && page.getThisPageElements() != null) {			
			result.setRows(page.getThisPageElements());
			result.setResults(String.valueOf(page.getTotalNumberOfElements()));
		}
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(result));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	public static void main(String[] argu) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"coreContext.xml", "beanContext.xml"});
		BaseInfoService baseInfoService = (BaseInfoService) context.getBean("baseInfoService");
		
		ItemInfo item = new ItemInfo();
		item.setCode("111");
		item.setName("test");
		baseInfoService.saveObject(item);
		System.out.println("done");
	}
	
	@Override
	public void doModify() throws ServletException, IOException {
		
	}


	@Override
	public void doAdd() throws ServletException, IOException {
		
	}
	
	/**
	 * 获取物料下拉选择框
	 * @throws IOException 
	 */
	public void getMaterialSelect() throws IOException {
		String name = request.getParameter("name");
		String typeOidStr =  request.getParameter("type_oid");
		long typeOid = 0;
		if (!StringHelper.isEmpty(typeOidStr)) {
			typeOid = Long.parseLong(typeOidStr);
		}
		List<ItemInfo> lists = baseInfoService.findItems(name, typeOid);
		List<JSONObject> jsonList = new LinkedList<>();
		for (ItemInfo obj : lists) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("itemOid", obj.getOid());
			jsonObj.put("itemName", obj.getName());
			jsonObj.put("itemCode", obj.getCode());
			jsonObj.put("itemColor", obj.getColor());
			
			jsonList.add(jsonObj);
		}
		
		String resutlStr = JsonUtils.toJSONString(jsonList);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
}
