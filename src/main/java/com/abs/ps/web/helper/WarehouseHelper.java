package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.util.StringHelper;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.StockType;
import com.abs.ps.domain.Warehouse;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.WarehouseDto;

public class WarehouseHelper implements IControllerHelper{
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private ActionLogHelper actionLogHelper = null;
	private BaseInfoService baseInfoService;
	private int pageSize = 10;
	
	public WarehouseHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context,int pageSize){
		this.request = request;
		this.response = response;
		this.context = context;
		this.pageSize=pageSize;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.baseInfoService = (BaseInfoService) factory.getBeanByName("baseInfoService");
		actionLogHelper = new ActionLogHelper(request, response, context, pageSize);
	}
	
	@Override
	public void doDelete() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					Warehouse warehouse =  (Warehouse)baseInfoService.getEntityByOid(Warehouse.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
							actionLogHelper.generateActionLog(
								ActionLogHelper.ACTION_TYPE_DELETE,
								QueueConstants.AM_WHS,
								"仓库名称:" + warehouse.getName(),
								""
							)
						);
					baseInfoService.delete(Warehouse.class, Long.parseLong(delId));
				}
			} else {

				Warehouse warehouse =  (Warehouse)baseInfoService.getEntityByOid(Warehouse.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_WHS,
							"仓库名称:" + warehouse.getName(),
							""
						)
					);
				baseInfoService.delete(Warehouse.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
	}

	@Override
	public void doSave() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oid = request.getParameter("oid");
		String name = request.getParameter("name");
		
		Warehouse obj = null;
		
		
		if (StringHelper.isEmpty(oid)) {
			obj = new Warehouse();
			
			 actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_ADD,
							QueueConstants.AM_WHS,
							"仓库名称:" + name,
							""
						)
					);
			
		} else {
			obj = (Warehouse) baseInfoService.getEntityByOid(Warehouse.class,Long.parseLong(oid));
			
			actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_MODIFY,
						QueueConstants.AM_WHS,
						"仓库名称:" + obj.getName(),name
					)
				);
		}
		obj.setName(name);
	
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
		
		if (!StringHelper.isEmpty(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		String name = request.getParameter("search_name");
		
		ListResult<WarehouseDto> results = baseInfoService.findWarehouseWithPaging(pageNumber, pageSize, name);
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
