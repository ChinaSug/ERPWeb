package com.abs.ps.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.abs.ps.util.AMConstants;
import com.abs.ps.util.Constants;
import com.abs.ps.util.ERPWebUtil;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.helper.ActionLogHelper;
import com.abs.ps.web.helper.BaseInfoHelper;
import com.abs.ps.web.helper.BomInfoHelper;
import com.abs.ps.web.helper.CheckHelper;
import com.abs.ps.web.helper.CustomerHelper;
import com.abs.ps.web.helper.DamageHelper;
import com.abs.ps.web.helper.HelperFactory;
import com.abs.ps.web.helper.IControllerHelper;
import com.abs.ps.web.helper.ItemHelper;
import com.abs.ps.web.helper.OrderManagerHelper;
import com.abs.ps.web.helper.ProductControlHelper;
import com.abs.ps.web.helper.ScheduleHelper;
import com.abs.ps.web.helper.StockCheckPointHelper;
import com.abs.ps.web.helper.StockInfoHelper;
import com.abs.ps.web.helper.SupplierHelper;
import com.abs.ps.web.helper.UserHelper;
import com.abs.ps.web.helper.WorkbenchHelper;

/**
 * A central controller for biz operation.
 * @author huangwi2
 * @since 2016.4.1
 *
 */
public class ManagementController {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private Logger logger=Logger.getLogger(ManagementController.class.getName()); 
	
	public static String HTML_VERSION_PARAM = "?" + ERPWebUtil.HTML_VERSION; 
	
	public ManagementController(HttpServletRequest request, HttpServletResponse response, ServletContext context)  {
		this.request = request;
		this.response = response;
		this.context = context;
	}
	
	public void execute() throws ServletException, IOException{
		String action = request.getParameter("op_action");
		logger.debug("current operation is " + action + " at " + new Date());
		
		String pageSizeStr = (String) this.context.getAttribute(QueueConstants.QUEUE_PAGE_SIZE);
		int pageSize = Integer.valueOf(pageSizeStr);
		
		if (QueueConstants.USER_OP_USER_PRIV_PAGE.equals(action)) {
			UserHelper userHelper = new UserHelper(request, response, context, pageSize);
			userHelper.loadPrivPage();
		} else if (QueueConstants.USER_OP_USER_PRIV_SAVE.equals(action)) {
			UserHelper userHelper = new UserHelper(request, response, context, pageSize);
			userHelper.saveUserPriv();
		} else if (QueueConstants.USER_OP_SYSTEM_LOG.equals(action)) {
			ActionLogHelper alHelper = new ActionLogHelper(request, response, context, pageSize);
			alHelper.doQuery();
		} else if (isAppRequest(action)) {
		
		} else if ("APP_DEBUG_LOG".equals(action)) {
			ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, context, pageSize);
			actionLogHelper.doAppDebugLogQuery();
		} else if ("APP_REQUEST_DEBUG".equals(action)) {
			ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, context, pageSize);
			actionLogHelper.gotoAppRequestDebugPage();
		}
		// new application
		else if ("SYS_MAIN".equals(action)) {
			PageDispatcher.forward(request, response, "jsp/index.jsp");
		} else if ("DASHBOARD".equals(action)) {
			PageDispatcher.forward(request, response, "jsp/dashboard.jsp");
		} else if ("USER_TYPE_CHG".equals(action)) {
			UserHelper userHelper = new UserHelper(request, response, context, pageSize);
			userHelper.changeUserType();
		}
		
		// 工作台页面
		else if ("WORK_BENCH".equals(action)) {
			WorkbenchHelper workbench = new WorkbenchHelper(request, response, context, pageSize);
			workbench.forwardWorkbenchInfo();
		} 
		else if ("SUPPLIER_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/supplier_list.html" + HTML_VERSION_PARAM);
		} else if ("GET_LIST".equals(action)) {
			SupplierHelper helper = new SupplierHelper(request, response, context);
			helper.getList();
		}else if("CUSTOMER_OPT".equals(action)){
			CustomerHelper helper = new CustomerHelper(request, response, context, pageSize);
			helper.getCustomers();
		}else if ("SUPPLIER_OPT".equals(action)) {
			SupplierHelper helper = new SupplierHelper(request, response, context);
			helper.getSuppliers();
		} else if ("ITEM_TYPE_OPT".equals(action)) {
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getItemTypes();
		} else if ("MACHINE_OPT".equals(action)) {
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getMachines();
		} else if ("MOULD_OPT".equals(action)) {
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getMoulds();
		} else if ("STOCK_TYPE_OPT".equals(action)) {
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getStockTypes();
		} else if ("WHS_OPT".equals(action)) {
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getWarehouses();
		} else if ("DEPART_OPT".equals(action)) {
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getDepartments();
		}else if ("ITEM_OPT".equals(action)) {
			ItemHelper helper = new ItemHelper(request, response, context);
			helper.getItems();
		}else if("ITEM_OPTC".equals(action)){
			ItemHelper helper = new ItemHelper(request, response, context);
			helper.getItemsAndColor();
		}else if("ITEM_PRI_OPT".equals(action )){
			ItemHelper helper = new ItemHelper(request, response, context);
			helper.getItemsPrice();
		}else if ("WHS_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/warehouse_list.html" + HTML_VERSION_PARAM);
		} else if ("MACHINE_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/machine_list.html" + HTML_VERSION_PARAM);
		} else if ("MOULD_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/mould_list.html" + HTML_VERSION_PARAM);
		} else if ("STOCK_TYPE_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/stock_type_list.html" + HTML_VERSION_PARAM);
		} else if ("ITEM_TYPE_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/item_type_list.html" + HTML_VERSION_PARAM);
		} else if ("ITEM_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/item_info_list.html" + HTML_VERSION_PARAM);
		} else if ("CP_DETAIL".equals(action)) {
			StockCheckPointHelper helper = new StockCheckPointHelper(request, response, context);
			helper.findStockCheckPointDetailInfos();
		} else if ("STOCK_SEARCH".equals(action)) {
			StockInfoHelper helper = new StockInfoHelper(request, response, context);
			helper.searchCheckPoint();
		} else if ("ST_IN_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/stock_in_list.html" + HTML_VERSION_PARAM);
		} else if ("ST_OUT_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/stock_out_list.html" + HTML_VERSION_PARAM);
		} else if ("ST_SCH_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/stock_query.html" + HTML_VERSION_PARAM);
		} else if ("ST_CP_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/stock_check_point.html" + HTML_VERSION_PARAM);
		} else if ("CP_DETAIL_SAVE".equals(action)) {
			StockCheckPointHelper helper = new StockCheckPointHelper(request, response, context);
			helper.saveCpDetail();
		} else if ("DAM_DE_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/damage_list.html" + HTML_VERSION_PARAM);
		} else if("CP_GETBOM".equals(action)){
			StockCheckPointHelper helper = new StockCheckPointHelper(request, response, context);
			helper.findBomDetailDtos(null);
		}else if("CP_GETBOMDETAIL".equals(action)){
			StockCheckPointHelper helper = new StockCheckPointHelper(request, response, context);
			helper.findBomDetailById(null);
		}
		else if ("SCHED_SUB_LIST".equals(action)) {
			ScheduleHelper scheduleHelper = new ScheduleHelper(request, response, context);
			scheduleHelper.findScheduleDetailDtos(null);			
		} else if ("SCHED_SUB_SAVE".equals(action)) {
			ScheduleHelper scheduleHelper = new ScheduleHelper(request, response, context);
			scheduleHelper.saveDetail();
		} else if ("SCHED_SUB_DEL".equals(action)) {
			ScheduleHelper scheduleHelper = new ScheduleHelper(request, response, context);
			scheduleHelper.deleteDetail();
		} else if ("GET_SCHED".equals(action)) {
			ScheduleHelper scheduleHelper = new ScheduleHelper(request, response, context);
			scheduleHelper.getScheduleByOid();
		} else if("GET_BOM".equals(action)){
			BomInfoHelper bomInfoHelper = new BomInfoHelper(request,response, context,pageSize);
			bomInfoHelper.getBomByOid();
		}else if("BOMDETAIL_LIST".equals(action)){
			BomInfoHelper bomInfoHelper = new BomInfoHelper(request, response, context, pageSize);
			bomInfoHelper.findBomDetailDtos(null);
		} else if("BOMDETAIL_SAVE".equals(action)){
			BomInfoHelper bomInfoHelper = new BomInfoHelper(request, response, context, pageSize);
			bomInfoHelper.saveDetail();
		}else if("BOMDETAIL_DEL".equals(action)){
			BomInfoHelper bomInfoHelper = new BomInfoHelper(request, response, context, pageSize);
			bomInfoHelper.deleteDetail();
		} else if ("PROJ_MANA_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/project_schedule_list.html" + HTML_VERSION_PARAM);
		} else if ("GET_PC".equals(action)) {
			ProductControlHelper helper = new ProductControlHelper(request, response, context);
			helper.getProductControlByPcOid();
		} else if ("PC_SUB_LIST".equals(action)) {
			ProductControlHelper helper = new ProductControlHelper(request, response, context);
			helper.findProductControlDetails(null);
		} else if ("PC_SUB_SAVE".equals(action)) {
			ProductControlHelper helper = new ProductControlHelper(request, response, context);
			helper.saveDetail();
		} else if ("PC_SUB_DEL".equals(action)) {
			ProductControlHelper helper = new ProductControlHelper(request, response, context);
			helper.deleteDetail();
		} else if ("PROD_PLAN_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/produce_control_list.html" + HTML_VERSION_PARAM);
		} else if ("DEPART_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/depart_list.html" + HTML_VERSION_PARAM);
		} else if ("BOMINFO_PAGE".equals(action)) {
			PageDispatcher.forward(request, response, "page/bom_list.html" + HTML_VERSION_PARAM);
		} else if ("DAM_SUM_PAGE".equals(action)) { // 报废汇总
			DamageHelper helper = new DamageHelper(request, response, context);
			helper.getDamageSummary();
		} else if ("WEEK_PLAN".equals(action)) { // 周计划
			ProductControlHelper helper = new ProductControlHelper(request, response, context);
			helper.getWeeklyPlan();
		} else if ("PC_EXCEL".equals(action)) { // 导出生产控制单Excel表
			ProductControlHelper helper = new ProductControlHelper(request, response, context);
			helper.exportWeeklyPlanExcel();
		} else if ("GET_MATERIAL".equals(action)) { // 获取物料下拉选择框内容
			ItemHelper helper = new ItemHelper(request, response, context);
			helper.getMaterialSelect();
		} else if ("GET_BOM_DETAIL".equals(action)) { // 获取BOM详细
			BomInfoHelper helper = new BomInfoHelper(request, response, context, pageSize);
			helper.getBomDetailByOid();
		} else if ("IS_EXIST".equals(action)) { // 检查数据是否已存在
			CheckHelper helper = new CheckHelper(request, response, context);
			helper.doCheck();
		} else if ("GET_BOM_NUM".equals(action)) { // 获取Bom编号下拉选择框内容
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getBomNumNotProduct();
		} else if ("BOM_DETAIL_SG".equals(action)) { // 获取Bom附表下拉选择框内容
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getAllValidBomDetail();
		} else if ("BOM_VERIFY".equals(action)) { // 审核BOM表
			BomInfoHelper helper = new BomInfoHelper(request, response, context, pageSize);
			helper.verifyBomMain();
		} else if ("DAM_MAT_SL".equals(action)) { // 获取报废物品单价
			DamageHelper helper = new DamageHelper(request, response, context);
			helper.getDamageMaterialInfo();
		} else if ("STOCK_BOM_DETAIL_SG".equals(action)) { // 获取库存类型为产品时的产品下拉选择框
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getStockBomDetailSG();
		} else if ("CUR_USER".equals(action)) { // 获取当前用户
			BaseInfoHelper helper = new BaseInfoHelper(request, response, context);
			helper.getCurUser();
		} else if ("STOCK_PROD_SEARCH".equals(action)) { // 产品库存量查询
			StockInfoHelper helper = new StockInfoHelper(request, response, context);
			helper.getStockProdView();
		} else if ("CHECK_STOCK_WH".equals(action)) { // 判断盘点仓库是否在待盘点中
			CheckHelper helper = new CheckHelper(request, response, context);
			helper.checkIsCheckStockWarehourse();
		} else if ("CAN_DELETE_ST".equals(action)) { // 判断库存数据是否为可删除，待盘点仓库库存不允许删除
			StockInfoHelper helper = new StockInfoHelper(request, response, context);
			helper.canDeleteStock();
		} else if ("ORDER_MANAGER_LIST".equals(action)) { // 
			OrderManagerHelper helper = new OrderManagerHelper(request, response, context);
			helper.doQuery();
		} else if ("ORDER_MANAGER_SAVE".equals(action)) { //
			OrderManagerHelper helper = new OrderManagerHelper(request, response, context);
			helper.doSave();
		} else if ("ORDER_MANAGER_DEL".equals(action)) { //
			OrderManagerHelper helper = new OrderManagerHelper(request, response, context);
			helper.doDelete();
		} else if ("ORDER_MNG".equals(action)) {
			PageDispatcher.forward(request, response, "page/order_management.html");
		} 
		
		IControllerHelper helper = HelperFactory.getInstance(request, response, context, pageSize);
		if (helper == null) {
			return;
		}
		if (isQueryList(action)) {			
			helper.doQuery();
		} else if (isAdd(action)) {
			helper.doAdd();
		} else if (isSave(action)) {
			helper.doSave();
			request.setAttribute(QueueConstants.QUEUE_MESSAGE, QueueConstants.MESSAGE_SAVE_SUCCESS);

		} else if (isModify(action)) {
			helper.doModify();
		} else if (isDelete(action)) {
			helper.doDelete();
			request.setAttribute(QueueConstants.QUEUE_MESSAGE, QueueConstants.MESSAGE_DELETE_SUCCESS);
		} 
		
	}
	
	public static boolean isAppRequest(String action) {
		if (AMConstants.AM_APP_OP_SKU_SEARCH.equals(action) ) {
			return true;
		}
		return false;
	}

	private boolean isQueryList(String action) {
		if (QueueConstants.USER_OP_USER_LIST.equals(action) || QueueConstants.USER_OP_ORG_LIST.equals(action) ||"DEPART_LIST".equals(action)
				|| QueueConstants.USER_OP_EMP_LIST.equals(action) || Constants.USER_OP_ENT_LIST.equals(action) || Constants.USER_OP_ENT_DATA_LIST.equals(action) 
				|| Constants.USER_OP_PI_LIST.equals(action) || Constants.USER_OP_PI_REG_LIST.equals(action) || Constants.USER_OP_PI_COM_LIST.equals(action)
				|| "HOUSE_LIST".equals(action) || "CONT_LIST".equals(action) || "SUPPLIER_LIST".equals(action) || "SCHED_LIST".equals(action) 
				|| "ITEM_LIST".equals(action) || "ST_LIST".equals(action) || "IT_LIST".equals(action)  || "MOULD_LIST".equals(action) || "MACHINE_LIST".equals(action) 
				|| "WHS_LIST".equals(action) || "STOCK_IN_LIST".equals(action) || "STOCK_OUT_LIST".equals(action) || "CP_LIST".equals(action) || "DM_LIST".equals(action) ||QueueConstants.USER_OP_CUSTOMER_LIST.equals(action)
				|| "SCHED_SUB_LIST".equals(action)||"BOMINFO_LIST".equals(action) || "PC_LIST".equals(action)
			) {
			return true;
		}
		return false;
	}
	
	private boolean isAdd(String action) {
		if (QueueConstants.USER_OP_USER_ADD.equals(action) || QueueConstants.USER_OP_ORG_ADD.equals(action) || QueueConstants.USER_OP_EMP_ADD.equals(action) || 
				Constants.USER_OP_ENT_INFO_ADD.equals(action) || Constants.USER_OP_ENT_DATA_ADD.equals(action) || Constants.USER_OP_PI_ADD.equals(action)
				|| "HOUSE_ADD".equals(action) || "CONT_ADD".equals(action) || QueueConstants.USER_OP_CUSTOMER_ADD.equals(action)||"DEPART_ADD".equals(action)) {
			return true;
		}
		return false;
	}
	
	private boolean isSave(String action) {
		if (QueueConstants.USER_OP_USER_SAVE.equals(action) || QueueConstants.USER_OP_ORG_SAVE.equals(action)|| QueueConstants.USER_OP_EMP_SAVE.equals(action) ||
				Constants.USER_OP_ENT_INFO_SAVE.equals(action) || Constants.USER_OP_ENT_DATA_SAVE.equals(action) || Constants.USER_OP_PI_SAVE.equals(action)
				|| "HOUSE_SAVE".equals(action) || "CONT_SAVE".equals(action) || "SUPPLIER_SAVE".equals(action) || "SCHED_SAVE".equals(action) 
				|| "ITEM_SAVE".equals(action) || "ST_SAVE".equals(action) || "IT_SAVE".equals(action) || "MOULD_SAVE".equals(action) || "MACHINE_SAVE".equals(action) 
				|| "WHS_SAVE".equals(action) || "STOCK_IN_SAVE".equals(action) || "STOCK_OUT_SAVE".equals(action) || "CP_SAVE".equals(action) || "DM_SAVE".equals(action) ||QueueConstants.USER_OP_CUSTOMER_SAVE.equals(action)
				||"BOMINFO_SAVE".equals(action) || "PC_SAVE".equals(action) ||"DEPART_SAVE".equals(action) 
		) {
			return true;
		}
		return false;
	}
	
	private boolean isModify(String action) {
		if (QueueConstants.USER_OP_USER_MODIFY.equals(action) || QueueConstants.USER_OP_ORG_MODIFY.equals(action)|| QueueConstants.USER_OP_EMP_MODIFY.equals(action) ||
				Constants.USER_OP_ENT_INFO_MODIFY.equals(action) || Constants.USER_OP_ENT_DATA_MODIFY.equals(action) || Constants.USER_OP_PI_MODIFY.equals(action)
				|| "HOUSE_MODIFY".equals(action) || "CONT_MODIFY".equals(action) || QueueConstants.USER_OP_CUSTOMER_MODIFY.equals(action)
				) {
			return true;
		}
		return false;
	}
	
	private boolean isDelete(String action) {
		if (QueueConstants.USER_OP_USER_DELETE.equals(action) || QueueConstants.USER_OP_ORG_DELETE.equals(action) || QueueConstants.USER_OP_EMP_DELETE.equals(action) ||
				Constants.USER_OP_ENT_INFO_DELETE.equals(action) || Constants.USER_OP_ENT_DATA_DELETE.equals(action) || Constants.USER_OP_PI_DELETE.equals(action) || Constants.USER_OP_PI_REG_DELETE.equals(action) || Constants.USER_OP_PI_COM_DELETE.equals(action)
				|| "HOUSE_DELETE".equals(action) || "CONT_DELETE".equals(action) || "SUPPLIER_DEL".equals(action) || "SCHED_DEL".equals(action) 
				|| "ITEM_DEL".equals(action) || "ST_DEL".equals(action) || "IT_DEL".equals(action) || "MOULD_DEL".equals(action) || "MACHINE_DEL".equals(action) 
				|| "WHS_DEL".equals(action) || "STOCK_IN_DEL".equals(action) || "STOCK_OUT_DEL".equals(action)  || "CP_DEL".equals(action) || "DM_DEL".equals(action) || QueueConstants.USER_OP_CUSTOMER_DELETE.equals(action)
				||"BOMINFO_DEL".equals(action) || "PC_DEL".equals(action)||"DEPART_DELETE".equals(action)
			) {
			return true;
		}
		return false;
	}
	
	
	
}
