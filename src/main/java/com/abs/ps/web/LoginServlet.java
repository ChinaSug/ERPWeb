package com.abs.ps.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.abs.core.util.AbsBeanFactory;
import com.abs.ps.service.UserService;
import com.abs.ps.util.CookieHelper;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.MenuItemDto;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.helper.ActionLogHelper;

public class LoginServlet extends HttpServlet implements HttpSessionListener{
	private static final long serialVersionUID = 1L;
	private Logger logger=Logger.getLogger(LoginServlet.class.getName()); 

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("op_action");
		if (QueueConstants.USER_OP_LOGIN.equals(action)) {
			login(request, response);
		} else if ((QueueConstants.USER_OP_LOGOUT).equals(action)) {
			logout(request, response);
		} else {
			PageDispatcher.forward(request, response, "jsp/login.jsp");
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, this.getServletContext(), 10);
		actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_LOGOUT, ActionLogHelper.ACTION_TYPE_LOGOUT, "", ""));
		
		HttpSession session = request.getSession(); 
		session.setAttribute(QueueConstants.QUEUE_USER_SESSION, null);
		request.setAttribute(QueueConstants.QUEUE_MESSAGE, "");
		PageDispatcher.forward(request, response, "jsp/login.jsp");
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		logger.debug("Starting to login system...");
		String userId = request.getParameter("userid");
		String password = request.getParameter("password");
		if (!StringHelper.isEmpty(userId)) {
			userId = userId.toUpperCase();
		}
		UserService userService = (UserService) AbsBeanFactory.getBeanFactory(this.getServletContext()).getBeanByName("userService");
		UserDto userDto = new UserDto();
		userDto.setUserId(userId);
		userDto.setPassword(password);
		String flag = userService.isValidUser(userDto);
		logger.debug("flag = " + flag);
		logger.info("userid = " +  userId + "; password = " + password + "; result = " + flag);
		if (flag.equals(QueueConstants.LOGIN_SUCCESS)) {

				HttpSession session = request.getSession(); 
				userDto = userService.getUserByUserId(userId);

				session.setAttribute(QueueConstants.QUEUE_USER_SESSION, userDto);
				
				String remember = request.getParameter("remember");
				if ("Y".equals(remember)) {
					CookieHelper.addCookie(request, response, CookieHelper.ABS_USERID_COO, userId, CookieHelper.MAX_AGE);
					//CookieHelper.addCookie(request, response, CookieHelper.ABS_PWD_COO, password, CookieHelper.MAX_AGE);
					CookieHelper.addCookie(request, response, CookieHelper.ABS_REMEMBER, "Y", CookieHelper.MAX_AGE);
				} else {
					CookieHelper.addCookie(request, response, CookieHelper.ABS_USERID_COO, null, 0);
					//CookieHelper.addCookie(request, response, CookieHelper.ABS_PWD_COO, null, 0);
					CookieHelper.addCookie(request, response, CookieHelper.ABS_REMEMBER, null, 0);
				}
				
	
				String menuJson = userService.assembleUserMenu2Json(Long.valueOf(userDto.getId()), request.getContextPath());
				session.setAttribute(QueueConstants.QUEUE_MENU, menuJson);
				logger.debug("menuJson [" + menuJson + "]");
				
				ActionLogHelper actionLogHelper = new ActionLogHelper(request, response, this.getServletContext(), 10);
				actionLogHelper.saveActionLog(actionLogHelper.generateActionLog(ActionLogHelper.ACTION_TYPE_LOGIN, ActionLogHelper.ACTION_TYPE_LOGIN, "", ""));
				
				logger.debug("forwarding to home page...");
				forward(userDto, null, request, response, this.getServletContext());
			
		} else if (flag.equals(QueueConstants.LOGIN_INVALID_USERID) || flag.equals(QueueConstants.LOGIN_INVALID_PASSWORD)) {
			if (QueueConstants.LOGIN_INVALID_USERID.equals(flag)) {
				request.setAttribute(QueueConstants.QUEUE_MESSAGE, QueueConstants.LOGIN_INVALID_USERID);
			} else {
				request.setAttribute(QueueConstants.QUEUE_MESSAGE, QueueConstants.LOGIN_INVALID_PASSWORD);
			}
			request.setAttribute("userid", userId);
			PageDispatcher.forward(request, response, "jsp/login.jsp");

		}
	}
	
	private void forward(UserDto userDto, Map<String, List<MenuItemDto>> map, HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
		PageDispatcher.forward(request, response, "jsp/index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}
}