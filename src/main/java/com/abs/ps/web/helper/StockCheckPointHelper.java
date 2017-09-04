package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.abs.core.util.AbsBeanFactory;
import com.abs.ps.domain.CheckPointDetail;
import com.abs.ps.domain.CheckPointMain;
import com.abs.ps.service.BomInfoService;
import com.abs.ps.service.StockInfoService;
import com.abs.ps.util.Constants;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.JsonUtils;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.BomDetailDto;
import com.abs.ps.web.dto.CheckPointDetailDto;
import com.abs.ps.web.dto.CheckPointMainDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.UserDto;

public class StockCheckPointHelper implements IControllerHelper{
	private Logger logger=Logger.getLogger(StockCheckPointHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private BomInfoService bomInfoService;
	private StockInfoService stockInfoService;
	private ActionLogHelper actionLogHelper = null;
	
	public StockCheckPointHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		actionLogHelper = new ActionLogHelper(request, response, context, 10);
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.stockInfoService = (StockInfoService) factory.getBeanByName("stockInfoService");
		this.bomInfoService = (BomInfoService) factory.getBeanByName("bomInfoService");
	}
	
	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					CheckPointMain checkPointMain =  (CheckPointMain)stockInfoService.getEntityByOid(CheckPointMain.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_SCP,
							"盘点编号:" + checkPointMain.getCheckNum(),
							""
						)
					);
					stockInfoService.delete(CheckPointMain.class, Long.parseLong(delId));
					stockInfoService.deleteCheckPointDetail(delId);
				}
			} else {
				CheckPointMain checkPointMain =  (CheckPointMain)stockInfoService.getEntityByOid(CheckPointMain.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_DELETE,
						QueueConstants.AM_SCP,
						"盘点编号:" + checkPointMain.getCheckNum(),
						""
					)
				);
				stockInfoService.delete(CheckPointMain.class, Long.parseLong(deleteIds));
				stockInfoService.deleteCheckPointDetail(deleteIds);
			}
		}
		doQuery();
	}

	@Override
	public void doSave() throws ServletException, IOException {
		String checkNum = request.getParameter("checkNum");
		String peroidType = request.getParameter("peroidType");
		String peroidYear = request.getParameter("peroidYear");
		String peroidTime = request.getParameter("peroidTime");
		String checkPerson = StringHelper.getNewString(request.getParameter("checkPerson"));
		String warehouseOid = request.getParameter("warehouseOid");
		String remark = StringHelper.getNewString(request.getParameter("remark"));
		
		boolean isUnderCheckPoint = false;
		if (!StringHelper.isEmpty(warehouseOid)) {
			isUnderCheckPoint = stockInfoService.isUnderCheckPointByWHS(Long.parseLong(warehouseOid));
		}
		
		if (isUnderCheckPoint) {
			ListResult<CheckPointMainDto> result = new ListResult<CheckPointMainDto>();
			result.setHasError("true");
			result.setError(Constants.ERROR_CODE_UNDER_CP);
			
			String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(result));
			PrintWriter out = response.getWriter();
			out.print(resutlStr);
			
			out.flush();
			out.close();
		} else {
			CheckPointMainDto dto = new CheckPointMainDto();
			dto.setCheckNum(checkNum);
			dto.setCheckPerson(checkPerson);
			
			if (sessionUserDto != null) {
				if (sessionUserDto != null) {
					dto.setCreateBy(sessionUserDto.getUserId());
				}
			}
			dto.setPeroidTime(peroidTime);
			dto.setPeroidType(peroidType);
			dto.setPeroidYear(peroidYear);
			dto.setRemark(remark);
			dto.setWarehouseOid(warehouseOid);

			boolean isDuplicate = stockInfoService.isDuplicateCheckPoint(dto);
			if (isDuplicate) {
				ListResult<CheckPointMainDto> result = new ListResult<CheckPointMainDto>();
				result.setHasError("true");
				result.setError(Constants.ERROR_CODE_DUPLICATE);
				
				String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(result));
				PrintWriter out = response.getWriter();
				out.print(resutlStr);
				
				out.flush();
				out.close();
			} else {
				
				stockInfoService.generateCheckPoint(dto);
				actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_ADD,
						QueueConstants.AM_SCP,
						"盘点编号:" +checkNum,
						""
					)
				);
				
				doQuery();
			}
		}
	}
	
	/**
	 * 获取待盘点仓库子表信息，包括物料和产品
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findStockCheckPointDetailInfos() throws ServletException, IOException{
		String cpMainOid = request.getParameter("cp_main_oid");
		List<CheckPointDetailDto> itemList = null;
		List<CheckPointDetailDto> prodList = null;
		
		if (!StringHelper.isEmpty(cpMainOid)) {
			itemList = stockInfoService.findStockCheckPointDetailInfos(Long.parseLong(cpMainOid));
			prodList = stockInfoService.getCheckPointProd(cpMainOid);
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("items", itemList);
		
		JSONArray prodJArr = new JSONArray();
		for (CheckPointDetailDto pd : prodList) {
			JSONObject jo = new JSONObject();
			jo.put("oid", pd.getOid());
			jo.put("bomNum", pd.getBomNum());
			jo.put("prodId", pd.getProdId());
			jo.put("prodName", pd.getProdName());
			jo.put("currentStockAmt", pd.getCurrentStockAmt());
			jo.put("actualStockAmt", FilterUtil.filterNullStr(pd.getActualStockAmt()));
			jo.put("remark", FilterUtil.filterNullStr(pd.getRemark()));
			jo.put("safeAmt", FilterUtil.filterNullStr(pd.getSafeAmt()));
			
			prodJArr.add(jo);
		}
		jsonObj.put("prods", prodJArr);
		
		PrintWriter out = response.getWriter();
		out.print(jsonObj);
		out.flush();
		out.close();
	}
	
	public void saveCpDetail() throws ServletException, IOException {
		saveOrConfirmCp();
	}
	
	/**
	 * 保存或确认库存盘点表信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void saveOrConfirmCp()  throws ServletException, IOException {
		String cpMainOid = request.getParameter("cp_main_oid");
		String itemsJsonStr = request.getParameter("items");
		String prodsJsonStr = request.getParameter("prods");
		boolean isConfirm = FilterUtil.filterString2Boolean(request.getParameter("isConfirm"));

		CheckPointMain cpMain = (CheckPointMain) stockInfoService.getEntityByOid(CheckPointMain.class, Long.parseLong(cpMainOid));
		cpMain.setLastModifyBy(sessionUserDto.getUserId());
		cpMain.setLastModifyDate(new Date());
		
		stockInfoService.setCPDetail(cpMain, itemsJsonStr, prodsJsonStr, isConfirm);
		if(isConfirm){
//			System.out.println("=====盘点单确认");
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_SCP,
					"盘点单确认",
					""
				)
			);
		} else {
//			System.out.println("=====盘点单保存");
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_SCP,
					"盘点保存",
					""
				)
			);
		}
		
		findStockCheckPointDetailInfos();
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
		
		String peroidTime = request.getParameter("peroid_time");
		String status = request.getParameter("search_status");
		String warehouseOid = request.getParameter("warehouse_oid");
		String checkPerson = request.getParameter("checkPerson");
		CheckPointMainDto criteria = new CheckPointMainDto();
		
		criteria.setPeroidTime(peroidTime);
		criteria.setStatus(status);
		criteria.setWarehouseOid(warehouseOid);
		criteria.setCheckPerson(checkPerson);
		
		ListResult<CheckPointMainDto> resuts = stockInfoService.findCheckPointByPaging(pageNumber, pageSize, criteria);

		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(resuts));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	@Override
	public void doModify() throws ServletException, IOException {
		
	}
	@Override
	public void doAdd() throws ServletException, IOException {
		
	}
	
	public void findBomDetailDtos(String bomNums) throws ServletException, IOException{
		String bomNum = request.getParameter("bomNum");
		if (StringHelper.isEmpty(bomNums)) {
		  bomNums = bomNum;
		}

		List<BomDetailDto> details = new ArrayList<BomDetailDto>();
		details = bomInfoService.findSBomDetailDtosBybomNum(bomNums);
		String resutlStr = JsonUtils.toJSONString(details);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	public void findBomDetailById(String oids) throws ServletException, IOException{
		String oid = request.getParameter("oid");
		if (StringHelper.isEmpty(oids)) {
		  oids = oid;
		}

		List<BomDetailDto> details = new ArrayList<BomDetailDto>();
		details = bomInfoService.findBomDetailById(oids);
		String resutlStr = JsonUtils.toJSONString(details);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}

}
