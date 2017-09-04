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
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.StockInfo;
import com.abs.ps.service.StockInfoService;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.JsonUtils;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.StockInfoDto;
import com.abs.ps.web.dto.StockSearchDto;
import com.abs.ps.web.dto.UserDto;

public class StockInfoHelper implements IControllerHelper{
	private Logger logger = Logger.getLogger(StockInfoHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private StockInfoService stockInfoService;
	private String stockType;
	private ActionLogHelper actionLogHelper = null;

	
	public StockInfoHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.stockInfoService = (StockInfoService) factory.getBeanByName("stockInfoService");
		actionLogHelper = new ActionLogHelper(request, response, context,10);
	}
	
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					
					StockInfo stockInfo =  (StockInfo)stockInfoService.getEntityByOid(StockInfo.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_STOCKINFO,
							"库存管理编号:" + stockInfo.getStockNum(),
							""
						)
					);
					stockInfoService.delete(StockInfo.class, Long.parseLong(delId));
				}
			} else {
				StockInfo stockInfo =  (StockInfo)stockInfoService.getEntityByOid(StockInfo.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_DELETE,
						QueueConstants.AM_STOCKINFO,
						"库存管理编号:" + stockInfo.getStockNum(),
						""
					)
				);
				stockInfoService.delete(StockInfo.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
	}

	@Override
	public void doSave() throws ServletException, IOException {
		Map<String, Object> paramMap = FilterUtil.getParamStringMap(request.getParameterMap(), false);
		StockInfo obj = FilterUtil.getInstance(paramMap, StockInfo.class);
		if (obj == null) {
			return;
		}
		
		// 若库存物品类型为产品，则将物料ItemOid设置为-1，相反，若类型为物料，则将BomDetailOid设置为-1
		if ("1".equals(obj.getStockItemType())) {
			obj.setItemOid(null);
			// obj.setBomDetailOid(FilterUtil.filterString2Long(request.getParameter("itemOid")));
		} else if ("2".equals(obj.getStockItemType())) {
			obj.setBomDetailOid(null);
		}
		obj.setStockDate(DateHelper.convert2Date(request.getParameter("stockDate"), DateHelper.DATE_FORMATE));
		obj.setStatus("1"); // 默认状态为1
		obj.setLastModifyBy(sessionUserDto.getUserId());
		obj.setLastModifyDate(new Date());
		
		// 非管理员将自动设置单价和金额
		if (!sessionUserDto.isAdmin()) {
			ItemInfo itemInfo = (ItemInfo) stockInfoService.getEntityByOid(ItemInfo.class,
					FilterUtil.filterString2Long(obj.getItemOid().toString()));
			if (itemInfo != null) {
				obj.setUnitPrice(itemInfo.getUnitPrice());
			}
			double stockA = FilterUtil.filterObj2Double(obj.getStockAmt());
			double unp = FilterUtil.filterObj2Double(itemInfo.getUnitPrice());
			obj.setTotalPrice(stockA * unp);
		}
		
		long oid = obj.getOid() != null ? obj.getOid() : 0;
		if (oid > 0) {
			StockInfo oldStockInfo = (StockInfo) stockInfoService.getEntityByOid(StockInfo.class, oid);
			Map<String,String> map = FieldValueComparator.compareObject(obj, oldStockInfo);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_SCHD_MAIN,
					values[0],
					values[1]
				)
			);
		} else {
			obj.setOid(null);
			obj.setCreateBy(sessionUserDto.getUserId());
			obj.setCreateDate(new Date());
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_ADD,
					QueueConstants.AM_STOCKINFO,
					"库存编号:" + obj.getStockNum(),
					""
				)
			);
		}
		stockInfoService.saveObject(obj);
		
		PrintWriter out = response.getWriter();
		out.print("{}");
		out.flush();
		out.close();
		// doQuery();
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
		
		String stockNum = request.getParameter("stock_num");
		String whsOid = request.getParameter("search_whs_oid");
		
		String search_stockTypeOid = request.getParameter("search_stockTypeOid");
		String search_stockDate = request.getParameter("search_stockDate");
		
		StockInfoDto criteria = new StockInfoDto();
		criteria.setStockNum(stockNum);
		criteria.setStockInfoType(this.stockType);
		
		criteria.setWarehouseOid(whsOid);
		
		criteria.setStockTypeOid(search_stockTypeOid);
		criteria.setStockDate(search_stockDate);
		
		ListResult<StockInfoDto> resuts = stockInfoService.findStocksByPaging(pageNumber, pageSize, criteria);
		if (!sessionUserDto.isAdmin()) {
			for (StockInfoDto dto : resuts.getRows()) {
				dto.setUnitPrice(null);
				dto.setTotalPrice(null);
			}
		}
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(resuts));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	
	public void searchCheckPoint() throws ServletException, IOException {
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
		
		String warehouseOid = request.getParameter("warehouse_oid");
		String searchName = request.getParameter("search_name");
		StockSearchDto criteria = new StockSearchDto();
		criteria.setWarehouseOid(warehouseOid);
		criteria.setItemName(searchName);
		ListResult<StockSearchDto> resuts = stockInfoService.searchCheckPointByPaging(pageNumber, pageSize, criteria);

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
	
	/**
	 * 获取库存产品视图
	 * @throws IOException
	 */
	public void getStockProdView() throws IOException {
		
		String pageNumStr = request.getParameter("pageIndex");
		int pageNumber = 1;
		if (!StringHelper.isEmpty(pageNumStr)) {
			pageNumber = FilterUtil.filterString2Int(pageNumStr);
		}
		
		String pageSizeStr = request.getParameter("limit");
		int pageSize = 10;
		if (!StringHelper.isEmpty(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		Map<String, String> searchMap = FilterUtil.convertStrMap(request.getParameterMap(), false);
		
		ListResult<JSONObject> result = stockInfoService.findStockProdView(pageNumber, pageSize, searchMap);
		
		PrintWriter out = response.getWriter();
		out.print(JsonUtils.filter(JsonUtils.toJSONString(result)));
		out.flush();
		out.close();
	}
	
	/**
	 * 查找要删除的库存中是否有待盘点的仓库
	 * @throws IOException 
	 */
	public void canDeleteStock() throws IOException {
		String stockIds = request.getParameter("stockIds");
		
		PrintWriter out = response.getWriter();
		List<StockInfoDto> list = stockInfoService.getCheckingStock(stockIds);
		JSONObject jsonObj = new JSONObject();
		if (list != null && list.size() > 0) {
			jsonObj.put("success", false);
			StringBuffer sb = new StringBuffer();
			for (StockInfoDto d : list) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(d.getWarehouseName());
			}
			jsonObj.put("error", "不能删除以下待盘点的仓库库存数据：<br>" + sb.toString());
		} else {
			jsonObj.put("success", true);
		}
		
		out.print(jsonObj);
		out.flush();
		out.close();
	}
	
}
