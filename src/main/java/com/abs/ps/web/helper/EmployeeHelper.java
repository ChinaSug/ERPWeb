package com.abs.ps.web.helper;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.abs.core.paging.IPaging;
import com.abs.core.util.AbsBeanFactory;
import com.abs.ps.service.DepartmentService;
import com.abs.ps.service.EmployeeService;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.AMEmployeeDto;
import com.abs.ps.web.dto.DepartmentDto;
import com.abs.ps.web.dto.UserDto;

public class EmployeeHelper  implements IControllerHelper{
	private Logger logger=Logger.getLogger(EmployeeHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private EmployeeService employeeService = null;
	private int pageSize = 10;
	
	public EmployeeHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context, int pageSize){
		this.request = request;
		this.response = response;
		this.context = context;
		this.employeeService = (EmployeeService) AbsBeanFactory.getBeanFactory(this.context).getBeanByName("employeeService");
		this.pageSize =pageSize;
	}

	public void doDelete() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] delIds = request.getParameterValues("delid");
		employeeService.deleteObjectById(delIds);
		ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, context, 10);
		actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_DELETE, "AM_EMPLOYEE", "", ""));
		doQuery();
	}

	public void doSave() throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("doSave()...");
		AMEmployeeDto centerDto = new AMEmployeeDto();
		String id = request.getParameter("oid");
		if (!StringHelper.isEmpty(id) && !"-1".equals(id)) {
			centerDto.setId(Long.parseLong(id));
		}
		
		String departOid = request.getParameter("depart_oid");
		centerDto.setDepartOid(departOid);
		centerDto.setPositionCode(request.getParameter("positionCode"));
		centerDto.setStatus(request.getParameter("status"));
		centerDto.setEmpName(StringHelper.getNewString(request.getParameter("empName")));

		employeeService.save(centerDto);
		
		ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, context, pageSize);
		if (!StringHelper.isEmpty(id) && !"-1".equals(id)) {
			actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_MODIFY, "AM_EMPLOYEE", centerDto.getEmpName(), ""));
		} else {
			actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_ADD, "AM_EMPLOYEE", centerDto.getEmpName(), ""));
		}

		doQuery();
	}

	public void doModify() throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*AMEmployeeDto empDto = employeeService.getObjectById(Long.valueOf(request.getParameter("oid")));
		request.setAttribute(QueueConstants.AM_EMPLOYEE, empDto);
		
		DepartmentService departmentService = (DepartmentService) AbsBeanFactory.getBeanFactory(context).getBeanByName("departmentService");

		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		List<DepartmentDto> departDtos  = null;
//		if (sessionUserDto != null && !"ADMIN".equals(sessionUserDto.getUserId())) {
//			departDtos = new ArrayList<DepartmentDto>(); 
//			departDtos = departmentService.getDepartmentByOrg(sessionUserDto.getOrgOid());
//		} else {
//			departDtos = departmentService.getAllDepartment();
//		}
		
		request.setAttribute(QueueConstants.AM_DEPARTMENT_LIST, departDtos);
		
		PageDispatcher.forward(request, response, "jsp/employee_maintain.jsp");*/
	}

	public void doAdd() throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*DepartmentService departmentService = (DepartmentService) AbsBeanFactory.getBeanFactory(context).getBeanByName("departmentService");
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		List<DepartmentDto> departDtos  = null;
//		if (sessionUserDto != null && !"ADMIN".equals(sessionUserDto.getUserId())) {
//			departDtos = new ArrayList<DepartmentDto>(); 
//			departDtos = departmentService.getDepartmentByOrg(sessionUserDto.getOrgOid());
//		} else {
//			departDtos = departmentService.getAllDepartment();
//		}
		
		request.setAttribute(QueueConstants.AM_DEPARTMENT_LIST, departDtos);
		PageDispatcher.forward(request, response, "jsp/employee_maintain.jsp");*/
	}

	public void doQuery() throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("doQuery()...");
		
		String pageNumStr = request.getParameter("pageNumber");
		int pageNum = 1;
		if (!StringHelper.isEmpty(pageNumStr)) {
			pageNum = Integer.parseInt(pageNumStr);
		}
		String orgOid = "";
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		if (sessionUserDto != null && !"ADMIN".equals(sessionUserDto.getUserId())) {
			orgOid = String.valueOf(sessionUserDto.getOrgOid());
		}
		
		IPaging paging = employeeService.findObjectWithPaging(pageNum, pageSize, orgOid);
		paging.setURL(request.getContextPath() + "/mgHandler.html?op_action=EMP_LIST");
		
		request.setAttribute(QueueConstants.QUEUE_PAGING, paging);
		
		PageDispatcher.forward(request, response, "jsp/employee_list.jsp");
	}
	
}
