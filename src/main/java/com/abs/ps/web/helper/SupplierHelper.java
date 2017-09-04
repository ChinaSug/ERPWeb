package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.util.StringHelper;

import com.abs.core.paging.IPaging;
import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.DateHelper;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.Supplier;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.SupplierDto;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.json.OptionDto;

public class SupplierHelper implements IControllerHelper{
	private Logger logger=Logger.getLogger(SupplierHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private BaseInfoService baseInfoService;
	private ActionLogHelper actionLogHelper = null;

	public SupplierHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.baseInfoService = (BaseInfoService) factory.getBeanByName("baseInfoService");
		actionLogHelper = new ActionLogHelper(request, response, context,10);
	}
	
	
	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		
		
		
		if (!StringHelper.isEmpty(deleteIds)) {
			
			
			
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					Supplier supplier = baseInfoService.getSupplierById(Long.parseLong(delId));
					actionLogHelper.saveActionLog(
							actionLogHelper.generateActionLog(
								ActionLogHelper.ACTION_TYPE_DELETE,
								QueueConstants.AM_SUPPLIER,
								"供应商名称:" + supplier.getSupplierName(),
								""
							)
						);
					baseInfoService.delete(Supplier.class, Long.parseLong(delId));
				}
			} else {
				Supplier supplier = baseInfoService.getSupplierById(Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_SUPPLIER,
							"供应商名称:" + supplier.getSupplierName(),
							""
						)
					);
				baseInfoService.delete(Supplier.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
		
		
	}	

	@Override
	public void doSave() throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String supplierId = request.getParameter("supplierId");
		String supplierName = request.getParameter("supplierName");
		String type = request.getParameter("type");
		String prodName = request.getParameter("prodName");
		String contactPerson = request.getParameter("contactPerson");
		String contactPhone = request.getParameter("contactPhone");
		String email = request.getParameter("email");
		String status = request.getParameter("status");
		Supplier supplier = null;
		if (StringHelper.isEmpty(oid)) {
			supplier = new Supplier();
			if (sessionUserDto != null) {
				supplier.setCreateBy(this.sessionUserDto.getUserId());
			}
			supplier.setCreateDate(new Date());
			
		} else {
			supplier = baseInfoService.getSupplierById(Long.parseLong(oid));
		
		}
		
		supplier.setContactPerson(contactPerson);
		supplier.setEmail(email);
		if (sessionUserDto != null) {
			supplier.setLastModifyBy(this.sessionUserDto.getUserId());
		}
		supplier.setLastModifyDate(new Date());
		supplier.setContactPhone(contactPhone);
		supplier.setSupplierName(supplierName);
		supplier.setSupplierId(supplierId);
		supplier.setProdName(prodName);
		supplier.setStatus(status);
		supplier.setType(type);
		
		
		
        /*Supplier oldSupplier = baseInfoService.getSupplierBysupplierId(supplierId);*/
		if(StringHelper.isEmpty(oid)){
			actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_ADD,
						QueueConstants.AM_SUPPLIER,
						"供应商名称:" + supplierName,
						""
					)
				);
			
		}else{
			Supplier oldSupplier = baseInfoService.getSupplierById(Long.parseLong(oid));
			Map<String,String> map = FieldValueComparator.compareObject(supplier,oldSupplier);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_MODIFY,
						QueueConstants.AM_SUPPLIER,
						values[0].length() > 100 ? values[0].substring(0, 100) : values[0],
						values[1].length() > 100 ? values[1].substring(0, 100) : values[1]
					)
				);
		}
		baseInfoService.saveObject(supplier);
		doQuery();
	}

	@Override
	public void doQuery() throws ServletException, IOException {
		
		/**	Enumeration pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
		    String name=(String)pNames.nextElement();
		    String value=request.getParameter(name);
		    System.out.println(name + "=" + value);
		}
		
		 * start=2
limit=2
pageIndex=2
		 * 
		 */
		
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
		
		SupplierDto supplierDto = new SupplierDto();
		String supplierName = request.getParameter("supplier_name");
		
		supplierDto.setSupplierName(supplierName);
		
		IPaging page = baseInfoService.findSupplierWithPaging(pageNumber, pageSize, supplierDto);
		ListResult<SupplierDto> result = new ListResult<SupplierDto>();
		result.setResults("0");
		if (page != null && page.getThisPageElements() != null) {			
			List<SupplierDto> dtoList = new ArrayList<SupplierDto>();
			
			for (int i = 0, j = page.getThisPageElements().size(); i < j; i++) {
				Supplier supplier = (Supplier) page.getThisPageElements().get(i);
				SupplierDto dto = new SupplierDto();
				dto.setOid(supplier.getOid().toString());
				dto.setContactPerson(supplier.getContactPerson());
				dto.setContactPhone(supplier.getContactPhone());
				dto.setCreateBy(supplier.getCreateBy());
				dto.setCreateDate(DateHelper.convert2String(supplier.getCreateDate(), DateHelper.DATETIME_FORMATE));
				dto.setEmail(supplier.getEmail());
				dto.setProdName(supplier.getProdName());
				dto.setStatus(supplier.getStatus());
				dto.setSupplierId(supplier.getSupplierId());
				dto.setSupplierName(supplier.getSupplierName());
				dto.setType(supplier.getType());
				dtoList.add(dto);
			}

			result.setRows(dtoList);
			result.setResults(String.valueOf(page.getTotalNumberOfElements()));

		}
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(result));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
		
	}
	
	
	public void getSuppliers() throws ServletException, IOException{
		String name = request.getParameter("name");
		List<Supplier> lists = baseInfoService.findSuppliers(name);
		List<OptionDto> optionDtos = new ArrayList<OptionDto>();
//		optionDtos.add(new OptionDto());
		for (Supplier obj : lists) {
			OptionDto dto = new OptionDto();
			dto.setValue(obj.getOid().toString());
			dto.setText(obj.getSupplierName());
			optionDtos.add(dto);
		}
		
		String resutlStr = JsonUtils.toJSONString(optionDtos);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	public void getList() {
		List<OptionDto> list = new ArrayList<OptionDto>();
		OptionDto dto = new OptionDto();
		dto.setValue("1");
		dto.setText("李长明");
		list.add(dto);
		dto = new OptionDto();
		dto.setValue("2");
		dto.setText("刘三");
		list.add(dto);
		
		dto = new OptionDto();
		dto.setValue("3");
		dto.setText("赵四");
		list.add(dto);
		
		dto = new OptionDto();
		dto.setValue("4");
		dto.setText("王无");
		list.add(dto);
		
		dto = new OptionDto();
		dto.setValue("5");
		dto.setText("张六");
		list.add(dto);
		
		dto = new OptionDto();
		dto.setValue("6");
		dto.setText("人民的名义1");
		list.add(dto);
		
		dto = new OptionDto();
		dto.setValue("7");
		dto.setText("人民的名义2");
		list.add(dto);
		
		
		dto = new OptionDto();
		dto.setValue("8");
		dto.setText("人民的名义3");
		list.add(dto);
		
		
		dto = new OptionDto();
		dto.setValue("9");
		dto.setText("人民的名义4");
		list.add(dto);
		
		
		dto = new OptionDto();
		dto.setValue("10");
		dto.setText("人民的名义5");
		list.add(dto);
		
		
		dto = new OptionDto();
		dto.setValue("11");
		dto.setText("人民的名义6");
		list.add(dto);
		
		String resutlStr = JsonUtils.toJSONString(list);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(resutlStr);
		
		out.flush();
		out.close();
		
	}

	
	public static void main(String[] argu) {
		
	}


	@Override
	public void doModify() throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doAdd() throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
}
