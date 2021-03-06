package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.util.StringHelper;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.StockInfo;
import com.abs.ps.domain.StockType;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.StockTypeDto;
import com.abs.ps.web.dto.UserDto;

public class StockTypeHelper implements IControllerHelper{
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private BaseInfoService baseInfoService;
	private ActionLogHelper actionLogHelper = null;

	
	public StockTypeHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.baseInfoService = (BaseInfoService) factory.getBeanByName("baseInfoService");
		actionLogHelper = new ActionLogHelper(request, response, context, 10);
	}
	@Override
	public void doDelete() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					StockType stockType =  (StockType)baseInfoService.getEntityByOid(StockType.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
							actionLogHelper.generateActionLog(
								ActionLogHelper.ACTION_TYPE_DELETE,
								QueueConstants.AM_STOCKTYPE,
								"库存类型:" + stockType.getName(),
								""
							)
						);
					baseInfoService.delete(StockType.class, Long.parseLong(delId));
				}
			} else {
				StockType stockType =  (StockType)baseInfoService.getEntityByOid(StockType.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_STOCKTYPE,
							"库存类型:" + stockType.getName(),
							""
						)
					);
				baseInfoService.delete(StockType.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
	}

	@Override
	public void doSave() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oid = request.getParameter("oid");
		String name = request.getParameter("name");
		String dimension = request.getParameter("dimension");
		
		StockType obj = null;
		if (StringHelper.isEmpty(oid)) {
			obj = new StockType();
			
		} else {
			obj = (StockType) baseInfoService.getEntityByOid(StockType.class,Long.parseLong(oid));
		}
		obj.setName(name);
		obj.setDimension(dimension);
		baseInfoService.saveObject(obj);
		
		doQuery();
	}

	@Override
	public void doQuery() throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		String name = request.getParameter("search_name");
		
		ListResult<StockTypeDto> results = baseInfoService.findStockTypeWithPaging(pageNumber, pageSize, name);
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(results));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
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
