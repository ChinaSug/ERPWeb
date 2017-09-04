package com.abs.ps.web.helper;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abs.core.paging.IPaging;
import com.abs.core.util.AbsBeanFactory;
import com.abs.ps.domain.ActionLog;
import com.abs.ps.service.ActionLogService;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.ActionLogDto;
import com.abs.ps.web.dto.UserDto;

public class ActionLogHelper {

	public final static String SOURCE_WX= "W";
	public final static String SOURCE_NON_WX= "N";
	
	public final static String ACTION_TYPE_LOGIN = "LOGIN";
	public final static String ACTION_TYPE_LOGOUT = "LOGOUT";
	public final static String ACTION_TYPE_ADD = "ADD";
	public final static String ACTION_TYPE_DELETE = "DELETE";
	public final static String ACTION_TYPE_MODIFY = "MODIFY";
	public final static String ACTION_TYPE_APP_SEARCH = "APP_SEARCH";
	public final static String ACTION_TYPE_APP_LOGIN = "APP_LOGIN";
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private int pageSize = 10;
	private ActionLogService actionLogService = null;
	private UserDto sessionUserDto = null;
	private String applicaton;
	
	
	public ActionLogHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context, int pageSize) {
		this.request = request;
		this.response = response;
		this.context = context;
		this.pageSize = pageSize;
		actionLogService = (ActionLogService) AbsBeanFactory.getBeanFactory(context).getBeanByName("actionLogService");
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
	}
	
	public void setApplication(String application) {
		this.applicaton = application;
	}
	
	public void saveActionLog(List<ActionLog> actionLogs) throws IOException {
		if (actionLogs != null && !actionLogs.isEmpty()) {
			actionLogService.save(actionLogs);
		}
	}
	
	public void saveActionLog(ActionLog actionLog) throws IOException {
		if (actionLog != null) {
			actionLogService.save(actionLog);
		}
	}
	
	public ActionLog generateActionLogStateless(String actionType, String fieldName, String fromValue, String toValue, String userId, String source) throws IOException{
		ActionLog actionLog = new ActionLog();
		actionLog.setUserId(userId);
		actionLog.setActionType(actionType);
		actionLog.setFieldName(fieldName);
		actionLog.setFromValue(fromValue);
		actionLog.setToValue(toValue);
		actionLog.setSource(source);
		actionLog.setCreateTime(new Date());
		actionLog.setIpAddr(getIpAddress(request));
		return actionLog;
	}
	
	public ActionLog generateActionLog(String actionType, String fieldName, String fromValue, String toValue) throws IOException{
		if (sessionUserDto != null) {
			ActionLog actionLog = new ActionLog();
			actionLog.setUserId(sessionUserDto.getUserId());
			actionLog.setActionType(actionType);
			if (!StringHelper.isEmpty(applicaton)) {
				actionLog.setFieldName(applicaton + "-" + fieldName);
			} else {
				actionLog.setFieldName(fieldName);
			}
			fromValue = fromValue != null ? (fromValue.length() > 100 ? fromValue.substring(0, 100) : fromValue) : "";
			actionLog.setFromValue(fromValue);
			toValue = toValue != null ? (toValue.length() > 100 ? toValue.substring(0, 100) : toValue) : "";
			actionLog.setToValue(toValue);
			actionLog.setCreateTime(new Date());
			actionLog.setIpAddr(getIpAddress(request));
			return actionLog;
		}
		return null;
	}
	
	public String getIpAddress(HttpServletRequest request) throws IOException {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");

			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");

			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");

			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");

			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	 
	
	public void doQuery() throws ServletException, IOException {
		String pageNumStr = request.getParameter("pageNumber");
		int pageNum = 1;
		if (!StringHelper.isEmpty(pageNumStr)) {
			pageNum = Integer.parseInt(pageNumStr);
		}
		HttpSession session = request.getSession();
		UserDto sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);

		String searchType = request.getParameter("search_type");
		String centerCode = "";//StringHelper.filterNullStr(request.getParameter("center_code"));
		String logDate = StringHelper.filterNullStr(request.getParameter("log_date"));
		String userId = request.getParameter("user_id");
		String actionType = request.getParameter("actionType");
		
		if (searchType == null) {
			logDate = DateHelper.convert2String(new Date(), DateHelper.DATE_FORMATE);
		}
		
		if (sessionUserDto != null && !"ADMIN".equals(sessionUserDto.getUserId())) {
			centerCode = String.valueOf(sessionUserDto.getOrgOid());
		}
		
		ActionLogDto criteria = new ActionLogDto();
		criteria.setCenterCode(centerCode);
		criteria.setCreateTime(logDate);
		criteria.setUserId(userId);
		criteria.setActionType(actionType);
		
		IPaging paging = actionLogService.findByPaging(criteria, pageNum, pageSize);
		paging.setURL(request.getContextPath() + "/mgHandler.html?op_action=SYSTEM_LOG&center_code="+criteria.getCenterCode() + "&log_date=" + criteria.getCreateTime()+"&user_id=" +criteria.getUserId()+"&search_type="+searchType+"&actionType=" +criteria.getActionType());
		
		request.setAttribute("center_code", criteria.getCenterCode());
		request.setAttribute("log_date", criteria.getCreateTime());
		request.setAttribute(QueueConstants.QUEUE_PAGING, paging);
		request.setAttribute("user_id", criteria.getUserId());
		request.setAttribute("actionType",criteria.getActionType());
		
		PageDispatcher.forward(request, response, "jsp/system_log.jsp");
		
	}
	
	public void doAppDebugLogQuery() throws ServletException, IOException {
		String pageNumStr = request.getParameter("pageNumber");
		int pageNum = 1;
		if (!StringHelper.isEmpty(pageNumStr)) {
			pageNum = Integer.parseInt(pageNumStr);
		}

		
		IPaging paging = actionLogService.findAppDebugPaging(pageNum, pageSize);
		paging.setURL(request.getContextPath() + "/mgHandler.html?op_action=APP_DEBUG_LOG");

		request.setAttribute(QueueConstants.QUEUE_PAGING, paging);
		
		
		PageDispatcher.forward(request, response, "jsp/abs_app_debug_log.jsp");
		
	}
	
	public void gotoAppRequestDebugPage() throws ServletException, IOException {
		PageDispatcher.forward(request, response, "jsp/am_app_request_debug.jsp");
		
	}
	
}
