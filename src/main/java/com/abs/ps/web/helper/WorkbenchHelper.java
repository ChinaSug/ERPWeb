package com.abs.ps.web.helper;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.abs.core.util.AbsBeanFactory;
import com.abs.ps.service.OrgService;
import com.abs.ps.service.UserService;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.UserDto;

public class WorkbenchHelper {

	private Logger logger=Logger.getLogger(WorkbenchHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;

	private UserService userService = null;
	private OrgService orgService = null;
	
	public WorkbenchHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context,int pageSize){
		this.request = request;
		this.response = response;
		this.context = context;
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.userService = (UserService) factory.getBeanByName("userService");
		this.orgService = (OrgService) factory.getBeanByName("orgService");
	}
	
	/**
	 * 返回工作台页面信息
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void forwardWorkbenchInfo() throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		if(sessionUserDto == null){
			PageDispatcher.forward(request, response, "jsp/login.jsp");
			return;
		}
		
		long orgOid = sessionUserDto.getOrgOid();
		//OrganizationDto org = orgService.getOrganizationById(orgOid);

		//request.setAttribute("DUE_CONT_LIST", contractService.getDueDateList(org.getMaxAppointDay(), orgOid));
		//request.setAttribute("DUE_DAY", org.getMaxAppointDay());
		PageDispatcher.forward(request, response, "jsp/workbench.jsp");
	}
	
}
