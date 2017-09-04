package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
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

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.DateHelper;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.domain.DamageInfo;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.service.DamageService;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.DamageInfoDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.UserDto;

public class DamageHelper implements IControllerHelper{
	private Logger logger=Logger.getLogger(DamageHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private BaseInfoService baseInfoService;
	private ActionLogHelper actionLogHelper;
	private DamageService damageService;
	
	public DamageHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.baseInfoService = (BaseInfoService) factory.getBeanByName("baseInfoService");
		this.damageService = (DamageService) factory.getBeanByName("damageService");
		
		actionLogHelper = new ActionLogHelper(request, response, context, 10);
	}
	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					DamageInfo damageInfo =  (DamageInfo)baseInfoService.getEntityByOid(DamageInfo.class, Long.parseLong(delId));
					
					actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_DAMAGE,
							"报废编号:" + damageInfo.getNumber(),
							""
						)
					);
					baseInfoService.delete(DamageInfo.class, Long.parseLong(delId));
				}
			} else {
				DamageInfo damageInfo =  (DamageInfo)baseInfoService.getEntityByOid(DamageInfo.class, Long.parseLong(deleteIds));
				
				actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_DELETE,
						QueueConstants.AM_DAMAGE,
						"报废编号:" + damageInfo.getNumber(),
						""
					)
				);
				baseInfoService.delete(DamageInfo.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
	}

	@Override
	public void doSave() throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String number = request.getParameter("number");
		long itemOid = FilterUtil.filterString2Long(request.getParameter("itemOid"));
		String badAmt = request.getParameter("badAmt");
		String pieAmt = request.getParameter("pieAmt");
		String cost = request.getParameter("cost");
		String unitConsume = request.getParameter("unitConsume");
		String itemUnitPrice = request.getParameter("itemUnitPrice");
		String itemFee = request.getParameter("itemFee");
		String pieUnitPrice = request.getParameter("pieUnitPrice");
		String pieFee = request.getParameter("pieFee");
		String regSource = request.getParameter("regSource");
		String departOid = request.getParameter("departOid");
		String reportDate = request.getParameter("reportDate");
		String customerOid = request.getParameter("customerOid");
		long bomDetailOid = FilterUtil.filterString2Long(request.getParameter("bomDetailOid"));
		String damageType = request.getParameter("damageType");
		String damagePrice = request.getParameter("damagePrice");
		
		DamageInfo item = null;
		if (StringHelper.isEmpty(oid)) {
			item = new DamageInfo();
			if (sessionUserDto != null) {
				item.setCreateBy(this.sessionUserDto.getUserId());
			}
			item.setCreateDate(new Date());
			
		} else {
			item = (DamageInfo) baseInfoService.getEntityByOid(DamageInfo.class,Long.parseLong(oid));
		}

		if (sessionUserDto != null) {
			item.setLastModifyBy(this.sessionUserDto.getUserId());
		}
		item.setLastModifyDate(new Date());
		item.setNumber(number);
		if (!StringHelper.isEmpty(badAmt)) {
			item.setBadAmt(Double.parseDouble(badAmt));
		}
		if (!StringHelper.isEmpty(pieAmt)) {
			item.setPieAmt(Double.parseDouble(pieAmt));
		}
		if (!StringHelper.isEmpty(damagePrice)) {
			item.setUnitPrice(Double.parseDouble(damagePrice));
		}
		if (!StringHelper.isEmpty(cost)) {
			item.setCost(Double.parseDouble(cost));
		}
		if (!StringHelper.isEmpty(unitConsume)) {
			item.setUnitConsume(Double.parseDouble(unitConsume));
		}
		if (!StringHelper.isEmpty(itemUnitPrice)) {
			item.setItemUnitPrice(Double.parseDouble(itemUnitPrice));
		}
		if (!StringHelper.isEmpty(itemFee)) {
			item.setItemFee(Double.parseDouble(itemFee));
		}
		if (!StringHelper.isEmpty(pieUnitPrice)) {
			item.setPieUnitPrice(Double.parseDouble(pieUnitPrice));
		}
		if (!StringHelper.isEmpty(pieFee)) {
			item.setPieFee(Double.parseDouble(pieFee));
		}
		item.setRegSource(regSource);
		if (!StringHelper.isEmpty(departOid)) {
			item.setDepartOid(Long.parseLong(departOid));
		}
		if (!StringHelper.isEmpty(reportDate)) {
			item.setReportDate(DateHelper.convert2Date(reportDate, DateHelper.DATE_FORMATE));
		}
		if(!StringHelper.isEmpty(customerOid)) {
			item.setCustomerOid(Long.parseLong(customerOid));
		}
		
		item.setItemOid(itemOid);
		item.setBomDetailOid(bomDetailOid);
		item.setDamageType(damageType);
		
		if(StringHelper.isEmpty(oid)){
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_ADD,
					QueueConstants.AM_SCHD_MAIN,
					"名称:" + number,
					""
				)
			);
		} else {
			DamageInfo oldDamageInfo = (DamageInfo) baseInfoService.getEntityByOid(DamageInfo.class, Long.parseLong(oid));
			Map<String,String> map = FieldValueComparator.compareObject(item,oldDamageInfo);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_SCHD_MAIN,
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
		
		String itemName = request.getParameter("item_name");
		String search_reportDate = request.getParameter("search_reportDate");
		DamageInfoDto criteria = new DamageInfoDto();
		criteria.setItemName(itemName);
		criteria.setReportDate(search_reportDate);
		
		ListResult<DamageInfoDto> resuts = baseInfoService.findDamageInfosByPaging(pageNumber, pageSize, criteria);
		if (!sessionUserDto.isAdmin()) {
			for (DamageInfoDto dto : resuts.getRows()) {
				//判断是否管理员
				dto.setItemUnitPrice("0");
				dto.setItemFee("0");
				dto.setCost("0");
				dto.setDamagePrice("0");
				dto.setPieFee("0");
				dto.setPieFee("0");
				dto.setPieUnitPrice("0");
				dto.setUnitPrice("0");
			}
		}
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(resuts));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
	}
	
	@Override
	public void doModify() throws ServletException, IOException {
		
	}
	
	@Override
	public void doAdd() throws ServletException, IOException {
		
	}
	
	/**
	 * 报废汇总
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getDamageSummary() throws ServletException, IOException {
		String reportDate = request.getParameter("reportDate");
		if (StringHelper.isEmpty(reportDate)) {
			PageDispatcher.forward(request, response, "page/dam_sum.html");
			return;
		}
		
		Map<String, Object> jsonMap = new HashMap<>();
		List<Object> dsTableList = damageService.getDamageSumTable(reportDate);
		List<Object> custDSList = damageService.getCustomerDamageSummary(reportDate);
		List<Object> departDSList = damageService.getDepartDamageSummary(reportDate);
		
		jsonMap.put("DS_Table", dsTableList);
		jsonMap.put("DS_Customer", custDSList);
		jsonMap.put("DS_Depart", departDSList);
		
		PrintWriter out = response.getWriter();
		out.print(JsonUtils.toJSONArray(jsonMap).toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 获取报废物品信息
	 * 
	 * @throws IOException
	 */
	public void getDamageMaterialInfo() throws IOException {
		if (!sessionUserDto.isAdmin()) {
			return;
		}
		
		long bomDetailOid = FilterUtil.filterString2Long(request.getParameter("bomDetailOid"));
		long itemOid = FilterUtil.filterString2Long(request.getParameter("itemOid"));
		String damageType = request.getParameter("damageType");
		JSONObject jsonObj = new JSONObject();
		double itemUnitPrice = 0;
		double matPrice = 0; 
		double piePrice = 0;
		double damagePrice = 0;
		
		if ("1".equals(damageType) && bomDetailOid > 0) { // 报废产品
			BomDetail bomDetail = (BomDetail) baseInfoService.getEntityByOid(BomDetail.class, bomDetailOid);
			itemUnitPrice = FilterUtil.filterObj2Double(bomDetail.getUnitPrice());
			matPrice = FilterUtil.filterObj2Double(bomDetail.getMaterialUpc()); 
			piePrice = FilterUtil.filterObj2Double(bomDetail.getPieUpc());
			damagePrice = FilterUtil.filterObj2Double(bomDetail.getDamageUpc());
		} else if ("2".equals(damageType) && itemOid > 0) { // 报废物料
			ItemInfo item = (ItemInfo) baseInfoService.getEntityByOid(ItemInfo.class, itemOid);
			itemUnitPrice = FilterUtil.filterObj2Double(item.getUnitPrice());
			matPrice = FilterUtil.filterObj2Double(item.getMatPrice()); 
			piePrice = FilterUtil.filterObj2Double(item.getPiePrice());
			damagePrice = FilterUtil.filterObj2Double(item.getDamPrice());
		}
		
		jsonObj.put("itemUnitPrice", itemUnitPrice);
		jsonObj.put("matPrice", matPrice);
		jsonObj.put("piePrice", piePrice);
		jsonObj.put("damagePrice", damagePrice);
		
		PrintWriter out = response.getWriter();
		out.print(jsonObj.toString());
		out.flush();
		out.close();
	}
	
}
