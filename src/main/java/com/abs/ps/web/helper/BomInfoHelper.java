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

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.util.StringHelper;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.domain.BomMain;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.Mould;
import com.abs.ps.service.BomInfoService;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.BomDetailDto;
import com.abs.ps.web.dto.BomMainDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.UserDto;

/**
 * 更新： 苏建生 2017-7-26
 */
public class BomInfoHelper implements IControllerHelper {
	private Logger logger = Logger.getLogger(BomInfoHelper.class.getName());
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private BomInfoService bomInfoService;
	private String customerName;
	private ActionLogHelper actionLogHelper;
	private int pageSize;
	
	public BomInfoHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context,int pageSize){
		this.request = request;
		this.response = response;
		this.context = context;
		this.pageSize = pageSize;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.bomInfoService = (BomInfoService) factory.getBeanByName("bomInfoService");
		actionLogHelper = new ActionLogHelper(request, response, context, pageSize);
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			
			
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					
					BomMain bomMain =  (BomMain)bomInfoService.getEntityByOid(BomMain.class, Long.parseLong(delId));
					
					actionLogHelper.saveActionLog(
							actionLogHelper.generateActionLog(
								ActionLogHelper.ACTION_TYPE_DELETE,
								QueueConstants.AM_BOMINFO,
								"bom编号:" + bomMain.getBomNum(),
								""
							)
						);
					bomInfoService.delete(BomMain.class, Long.parseLong(delId));
				}
			} else {
				BomMain bomMain =  (BomMain)bomInfoService.getEntityByOid(BomMain.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_BOMINFO,
							"bom编号:" + bomMain.getBomNum(),
							""
						)
					);
				bomInfoService.delete(BomMain.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
	}
	
	public void deleteDetail() throws ServletException, IOException {
		String BomOid = request.getParameter("bomOid");	
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					BomDetail bomDetail =  (BomDetail)bomInfoService.getEntityByOid(BomDetail.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_SUPPLIER,
							"bom名称:" + bomDetail.getName(),
							""
						)
					);
						
					bomInfoService.delete(BomDetail.class, Long.parseLong(delId));
				}
			} else {
				 BomDetail bomDetail =  (BomDetail)bomInfoService.getEntityByOid(BomDetail.class, Long.parseLong(deleteIds));
				 actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_SUPPLIER,
							"bom名称:" + bomDetail.getName(),
							""
						)
					);
				bomInfoService.delete(BomDetail.class, Long.parseLong(deleteIds));
			}
		}
		findBomDetailDtos(BomOid);
	}	
	
	@Override
	public void doSave() throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String customerOid = request.getParameter("customerOid");
		String bomNum = request.getParameter("bomNum");
		String confirmPerson = request.getParameter("confirmPerson");
		String respPerson = request.getParameter("respPerson");
		BomMain obj = null;
		if (StringHelper.isEmpty(oid)) {
			obj = new BomMain();
			if (sessionUserDto != null) {
				if (sessionUserDto != null) {
					obj.setCreateBy(this.sessionUserDto.getUserId());
				}
			}
			obj.setCreateDate(new Date());
		} else {
			obj = (BomMain) bomInfoService.getEntityByOid(BomMain.class,Long.parseLong(oid));
		}

		
		if (!StringHelper.isEmpty(customerOid)) {
			obj.setCustomerOid(Long.parseLong(customerOid));
		}
		obj.setBomNum(bomNum);
		obj.setConfirmPerson(confirmPerson);
		obj.setRespPerson(respPerson);
		obj.setStatus("1");
		
		if (sessionUserDto != null) {
			obj.setLastModifyBy(this.sessionUserDto.getUserId());
			obj.setLastModifyDate(new Date());
		}
		
		if (StringHelper.isEmpty(oid)) {
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_ADD,
					QueueConstants.AM_BOMINFO,
					"BOM编号:" + bomNum,
					""
				)
			);
		} else {
			BomMain oldBomMain = (BomMain) bomInfoService.getEntityByOid(BomMain.class, Long.parseLong(oid));
			Map<String,String> map = FieldValueComparator.compareObject(obj,oldBomMain);
			if (oldBomMain != null && "2".equals(oldBomMain.getStatus())) {
				obj.setStatus("3"); // 设置为重审状态
			} else if (sessionUserDto.isAdmin() && "0".equals(oldBomMain.getStatus())) {
				obj.setStatus("0");
			}
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_BOMINFO,
					values[0],
					values[1]
				)
			);
		}
		bomInfoService.saveObject(obj);
		doQuery();
	}

	
	public void saveDetail() throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String bomOid = request.getParameter("bomOid");
		String prodId = request.getParameter("prodId");
		String mouldOid = request.getParameter("mouldOid");
		String name = request.getParameter("name");
		String prodPicUrl = request.getParameter("prodPicUrl");
		String itemOid = request.getParameter("itemOid");
		String pinkOid = request.getParameter("pinkOid");
		String alloyOid = request.getParameter("alloyOid");
		String projectId = request.getParameter("projectId");
		String fetchStandardNum = request.getParameter("fetchStandardNum");
		String fetchActualNum = request.getParameter("fetchActualNum");
		String rawItemStdPrice = request.getParameter("rawItemStdPrice");
		String rawItemActPrice = request.getParameter("rawItemActPrice");
		String shapeStdPd = request.getParameter("shapeStdPd");
		String shapeActPd = request.getParameter("shapeActPd");
		String prodStandardWgt = request.getParameter("prodStandardWgt");
		String prodActualWgt = request.getParameter("prodActualWgt");
		String pwStandardWgt = request.getParameter("pwStandardWgt");
		String pwActualWgt = request.getParameter("pwActualWgt");
		String shotStandardWgt = request.getParameter("shotStandardWgt");
		String shotActualWgt = request.getParameter("shotActualWgt");
		String stardardMachine = request.getParameter("stardardMachine");
		String actualMachine = request.getParameter("actualMachine");
		String dailyPc = request.getParameter("dailyPc");
		String unitPrice = request.getParameter("unitPrice");
		String avgProdAmt = request.getParameter("avgProdAmt");
		String reqAmt = request.getParameter("reqAmt");
		String pkgBoxMd = request.getParameter("pkgBoxMd");
		String pkgSuckMd = request.getParameter("pkgSuckMd");
		String pkgBagMd = request.getParameter("pkgBagMd");
		String pkgFilm = request.getParameter("pkgFilm");
		String pkgRemark = request.getParameter("pkgRemark");
		String remark = request.getParameter("remark");
		
		String materialUpc = request.getParameter("materialUpc");
		String pieUpc = request.getParameter("pieUpc");
		String damageUpc = request.getParameter("damageUpc");
		String materialType = request.getParameter("materialType");
		String safeAmt = request.getParameter("safeAmt");
		
		BomDetail obj = null;
		if (StringHelper.isEmpty(oid)) {
			obj = new BomDetail();
			if (sessionUserDto != null) {
				if (sessionUserDto != null) {
					obj.setCreateBy(this.sessionUserDto.getUserId());
				}
			}
			obj.setCreateDate(new Date());
			
		} else {
			obj = (BomDetail) bomInfoService.getEntityByOid(BomDetail.class,Long.parseLong(oid));
		}
		if(!StringHelper.isEmpty(bomOid)){
			obj.setBomOid(Long.parseLong(bomOid));
		}
		obj.setProdId(prodId);
		if(!StringHelper.isEmpty(mouldOid)){
			obj.setMouldOid(Long.parseLong(mouldOid));
		}
		obj.setName(name);
		obj.setProdPicUrl(prodPicUrl);
		if (!StringHelper.isEmpty(itemOid)) {
			obj.setItemOid(Long.parseLong(itemOid));
		}
		if (!StringHelper.isEmpty(pinkOid)) {
			obj.setPinkOid(Long.parseLong(pinkOid));
		}
		if (!StringHelper.isEmpty(alloyOid)) {
			obj.setAlloyOid(Long.parseLong(alloyOid));
		}
		obj.setProjectId(projectId);
		obj.setFetchStandardNum(fetchStandardNum);
		obj.setFetchActualNum(fetchActualNum);
		obj.setRawItemStdPrice(rawItemStdPrice);
		obj.setRawItemActPrice(rawItemActPrice);
		obj.setShapeStdPd(shapeStdPd);
		obj.setShapeActPd(shapeActPd);
		if (StringHelper.isEmpty(shapeActPd)) {
			obj.setShapeActPd("0");
		}
		obj.setProdStandardWgt(prodStandardWgt);
		obj.setProdActualWgt(prodActualWgt);
		obj.setPwStandardWgt(pwStandardWgt);
		obj.setPwActualWgt(pwActualWgt);
		obj.setShotStandardWgt(shotStandardWgt);
		obj.setShotActualWgt(shotActualWgt);
		obj.setStardardMachine(stardardMachine);
		obj.setActualMachine(actualMachine);
		obj.setDailyPc(dailyPc);
		obj.setAvgProdAmt(avgProdAmt);
		obj.setReqAmt(reqAmt);
		obj.setPkgBoxMd(pkgBoxMd);
		obj.setPkgSuckMd(pkgSuckMd);
		obj.setPkgBagMd(pkgBagMd);
		obj.setPkgFilm(pkgFilm);
		obj.setPkgRemark(pkgRemark);
		obj.setSafeAmt(FilterUtil.filterString2Double(safeAmt));
		
		obj.setRemark(remark);
		obj.setStatus("1");
		if (sessionUserDto.isAdmin()) {
			obj.setUnitPrice(FilterUtil.filterString2Double(unitPrice));
			obj.setMaterialUpc(FilterUtil.filterString2Double(materialUpc));
			obj.setPieUpc(FilterUtil.filterString2Double(pieUpc));
			obj.setDamageUpc(FilterUtil.filterString2Double(damageUpc));
		}
		obj.setMaterialType(FilterUtil.filterString2Long(materialType));
		
		if (StringHelper.isEmpty(oid)) {
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_ADD,
					QueueConstants.AM_BOM_DETAIL,
					"BOM名称:" + name,
					""
				)
			);
		} else {
			BomDetail oldBomDetail = (BomDetail) bomInfoService.getEntityByOid(BomDetail.class, Long.parseLong(oid));
			Map<String,String> map = FieldValueComparator.compareObject(obj,oldBomDetail);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_BOM_DETAIL,
					values[0],
					values[1]
				)
			);
		}
		bomInfoService.saveObject(obj);
		
		findBomDetailDtos(bomOid);
	}
	
	@Override
	public void doModify() throws ServletException, IOException {

	}

	@Override
	public void doAdd() throws ServletException, IOException {

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
		String search_status = request.getParameter("search_status");
		String search_bom = request.getParameter("search_bom");
		
		BomMainDto criteria = new BomMainDto();
		criteria.setCustomerName(this.customerName);
		criteria.setStatus(search_status);
		criteria.setBomNum(search_bom);
		ListResult<BomMainDto> resuts = bomInfoService.findBomMainByPaging(pageNumber, pageSize, criteria);

		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(resuts));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		out.flush();
		out.close();
	}
	
	public void findBomDetailDtos(String bomOid) throws ServletException, IOException{
		Long bomInfoOid = FilterUtil.filterString2Long(request.getParameter("bomOid"));		

		List<BomDetailDto> details = new ArrayList<BomDetailDto>();
		details = bomInfoService.findSBomDetailDtos(bomInfoOid.toString());
		// 若当前用户不是管理员，则隐藏金额信息
		if (!sessionUserDto.isAdmin()) {
			for (BomDetailDto dto : details) {
				dto.setUnitPrice(0);
				dto.setPieUpc(0);
				dto.setMaterialUpc(0);
				dto.setDamageUpc(0);
			}
		}
		String resutlStr = JsonUtils.toJSONString(details);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	public void getBomByOid() throws ServletException, IOException{
		String bomOid = request.getParameter("bomOid");
		BomMainDto bomMainDto = null;
		if(FilterUtil.filterString2Long(bomOid) > 0){
			 bomMainDto = bomInfoService.getBomByOid(bomOid);
		}
		
		// 非管理员，BOM附表金额设置为0
		if (!sessionUserDto.isAdmin() && bomMainDto != null) {
			List<BomDetailDto> details = bomMainDto.getDetails();
			if (details != null) {
				for (BomDetailDto dto : details) {
					dto.setUnitPrice(0);
					dto.setMaterialUpc(0);
					dto.setPieUpc(0);
					dto.setDamageUpc(0);
				}
			}
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("IsAdmin", sessionUserDto.isAdmin());
		jsonObj.put("BomMain", bomMainDto);
		
		
		PrintWriter out = response.getWriter();
		out.print(jsonObj.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 获取BOM表详细对象
	 * @throws IOException 
	 */
	public void getBomDetailByOid() throws IOException {
		long bomDetailOid = FilterUtil.filterString2Long(request.getParameter("bomDetailOid"));
		
		BomDetail obj = (BomDetail) bomInfoService.getEntityByOid(BomDetail.class, bomDetailOid);
		BomDetailDto dto = FilterUtil.convertObjectClass(obj, BomDetailDto.class);
		if (dto != null) {
			BomMainDto bomMain = bomInfoService.getBomByOid(dto.getBomOid());
			if (bomMain != null) {
				dto.setBomNum(bomMain.getBomNum());
			}
			
			Mould mould = (Mould) bomInfoService.getEntityByOid(Mould.class, obj.getMouldOid());
			if (mould != null) {
				dto.setMouldName(mould.getName());
			}
			
			ItemInfo item = (ItemInfo) bomInfoService.getEntityByOid(ItemInfo.class, obj.getItemOid());
			if (item != null) {
				dto.setItemCode(item.getCode());
				dto.setItemColor(item.getColor());
			}
		}
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(dto));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		out.flush();
		out.close();
	}
	
	public void verifyBomMain() throws IOException {
		
		long bomOid = FilterUtil.filterString2Long(request.getParameter("bomOid"));
		String verify = request.getParameter("verify");
		boolean success = false;
		
		if (sessionUserDto.isAdmin()) {
			BomMain bomMain = (BomMain) bomInfoService.getEntityByOid(BomMain.class, bomOid);
			if (bomMain != null) {
				if ("1".equals(verify)) { // 通过审核，状态改为已审0
					bomMain.setStatus("0");
				} else if ("0".equals(verify)) { // 未通过审核，状态改为2未通过审核
					bomMain.setStatus("2");
				}
				bomInfoService.saveObject(bomMain);
				success = true;
			}
		}
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", success);
		out.print(jsonObj.toString());
		
		out.flush();
		out.close();
		
	}
	
	
	
}
