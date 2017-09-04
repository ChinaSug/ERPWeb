package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.BomMain;
import com.abs.ps.domain.Department;
import com.abs.ps.domain.Supplier;
import com.abs.ps.domain.Warehouse;
import com.abs.ps.service.DepartmentService;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.DepartDto;
import com.abs.ps.web.dto.DepartmentDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.UserDto;
import com.sun.jdi.LongValue;

public class DepartmentHelper implements IControllerHelper {
	private Logger logger = Logger.getLogger(DepartmentHelper.class.getName());

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private DepartmentService departmentService = null;
	private int pageSize = 10;
	private UserDto sessionUserDto = null;
	private String orgName;
	private ActionLogHelper actionLogHelper = null;

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public DepartmentHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context,
			int pageSize) {
		this.request = request;
		this.response = response;
		this.context = context;
		this.departmentService = (DepartmentService) AbsBeanFactory.getBeanFactory(this.context).getBeanByName(
				"departmentService");
		this.pageSize = pageSize;

		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		actionLogHelper = new ActionLogHelper(request, response, context, pageSize);
	}

	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					Department depart = (Department) departmentService.getEntityByOid(Department.class,
							Long.parseLong(delId));
					actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_DEPARTMENT, "部门名称:" + depart.getDepartName(), ""));
					departmentService.delete(Department.class, Long.parseLong(delId));
				}
			} else {
				Department depart = (Department) departmentService.getEntityByOid(Department.class,
						Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_DELETE,
						QueueConstants.AM_DEPARTMENT, "部门名称:" + depart.getDepartName(), ""));
				departmentService.delete(Department.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
	}

	public void doSave() throws ServletException, IOException {
		logger.debug("dSave() ...");
		
		String oid = request.getParameter("id");
		String name = request.getParameter("departName");
		Department depart = null;
		if (StringHelper.isEmpty(oid)) {
			depart = new Department();
			actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_ADD,
					QueueConstants.AM_DEPARTMENT, "部门名称:" + depart.getDepartName() + name, ""));

		} else {
			depart = (Department) departmentService.getEntityByOid(Department.class, Long.parseLong(oid));

			actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_DEPARTMENT, "部门名称:" + depart.getDepartName(), name));
		}
		depart.setDepartName(name);
		depart.setOrgOid(Long.valueOf("1"));
		departmentService.saveObject(depart);
		doQuery();
	}

	public void doModify() throws ServletException, IOException {

	}

	public void doAdd() throws ServletException, IOException {

	}

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
		String name = request.getParameter("search_name");
		DepartDto criteria = new DepartDto();
		criteria.setDepartName(name);
		ListResult<DepartDto> results = departmentService.findObjectWithPaging(pageNumber, pageSize, criteria);
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(results));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		out.flush();
		out.close();
	}
	
}
