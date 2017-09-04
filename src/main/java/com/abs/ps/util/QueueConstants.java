package com.abs.ps.util;

public final class QueueConstants { 
	private QueueConstants(){}
	
	public final static String QUEUE_USER_SESSION = "QUEUE_USER_SESSION";
	// properties key
	public final static String QUEUE_PAGE_SIZE = "abs.pagesize";
	public final static String QUEUE_REFRESH_INTEVAL = "abs.refresh.inteval";
	public final static String QUEUE_IDLE_INTEVAL = "abs.idle.inteval";
	public final static String QUEUE_INTEVAL_GAP_MINS = "abs.inteval.gap.mins";
	public final static String QUEUE_APPOINT_WS = "abs.appoint.ws";
	public final static String QUEUE_APPOINT_WS_BIP = "abs.appoint.ws.bip";
	public final static String QUEUE_APPOINT_WS_STARTIDX = "abs.appoint.ws.startidx";
	public final static String QUEUE_APPOINT_WS_ENDIDX = "abs.appoint.ws.endidx";
	
	
	public final static String QUEUE_PAGING = "QUEUE_PAGING";
	public final static String QUEUE_PAGING_URL = "QUEUE_PAGING_URL";
	public final static String QUEUE_CENTER_LIST = "QUEUE_CENTER_LIST";
	
	// Object
	public final static String QUEUE_USER = "QUEUE_USER";
	public final static String QUEUE_USER_LIST = "QUEUE_USER_LIST";
	public final static String QUEUE_SERVICE = "QUEUE_SERVICE";
	public final static String QUEUE_SERVICE_LIST = "QUEUE_SERVICE_LIST";
	public final static String QUEUE_CENTER = "QUEUE_CENTER";
	public final static String QUEUE_CENTER_NAME = "QUEUE_CENTER_NAME";
	public final static String QUEUE_CENTER_CODE = "QUEUE_CENTER_CODE";
	public final static String QUEUE_WINDOW = "QUEUE_WINDOW";
	public final static String QUEUE_WINDOW_NAME = "QUEUE_WINDOW_NAME";
	public final static String QUEUE_WINDOW_LIST = "QUEUE_WINDOW_LIST";
	public final static String QUEUE_BIZ_CONFIG = "QUEUE_BIZ_CONFIG";
	public final static String QUEUE_CONFIG_UI = "QUEUE_CONFIG_UI";
	public final static String QUEUE_MESSAGE = "QUEUE_MESSAGE";
	public final static String QUEUE_NUM_CALL = "QUEUE_NUM_CALL";
	public final static String QUEUE_USER_PRIV_LIST = "QUEUE_USER_PRIV_LIST";
	public final static String QUEUE_USER_PRIV = "QUEUE_USER_PRIV";
	public final static String QUEUE_MENU = "QUEUE_MENU";
	public final static String QUEUE_FETCH = "QUEUE_FETCH";
	public final static String QUEUE_USER_ADMIN = "QUEUE_USER_ADMIN";
	
	public final static String AM_DEPARTMENT= "AM_DEPARTMENT";
	public final static String AM_DEPARTMENT_LIST= "AM_DEPARTMENT_LIST";
	
	public final static String AM_ORG_LIST= "AM_ORG_LIST";
	public final static String AM_EMPLOYEE= "AM_EMPLOYEE";
	public final static String AM_CUSTOMER="AM_CUSTOMER";
	public final static String AM_EMPLOYEE_LIST= "AM_EMPLOYEE_LIST";
	public final static String AM_WHS="AM_WHS";
	public final static String AM_MOULD="AM_MOULD";
	public final static String AM_BOMINFO="AM_BOMINFO";
	public final static String AM_BOM_DETAIL="AM_BOM_DETAIL";
	public final static String AM_MACHINE="AM_MACHINE";
	public final static String AM_ITEMTYPE="AM_ITEMTYPE";
	public final static String AM_SUPPLIER="AM_SUPPLIER";
	public final static String AM_ITEM="AM_ITEM";
	public final static String AM_SCHD_MAIN = "AM_SCHD_MAIN";
	public final static String AM_SCHD_DETAIL = "AM_SCHD_DETAIL";
	public final static String AM_DAMAGE = "AM_DAMAGE";
	public final static String AM_SCP = "AM_SCP";
	public final static String AM_STOCKINFO="AM_STOCKINFO";
	public final static String AM_STOCKTYPE="AM_STOCKTYPE";
	public final static String AM_PC = "AM_PC";
	public final static String AM_SUB_PC = "AM_SUB_PC";
	
	
	public final static String QUEUE_FETCH_MENU_CODE = "BIZ_FET";
	public final static String QUEUE_CALL_MENU_CODE = "BIZ_CAL";
	
	
	// operation defined
	public final static String USER_OP_LOGIN = "LOGIN";
	public final static String USER_OP_LOGOUT = "LOGOUT";
	//login status
	public final static String LOGIN_SUCCESS = "LOGIN_SUCCESS";
	public final static String LOGIN_INVALID_USERID = "LOGIN_INVALID_USERID";
	public final static String LOGIN_DUPLICATE_LOGIN = "LOGIN_DUPLICATE_LOGIN";
	public final static String LOGIN_INVALID_PASSWORD = "LOGIN_INVALID_PASSWORD";	
	public final static String LOGIN_SESSION_TIMEOUT = "LOGIN_SESSION_TIMEOUT";
	public final static String INVALID_WORDING = "INVALID_WORDING";
	public final static String USER_EXPIRE = "USER_EXPIRE";
	public final static String USER_UNAVAILABLE = "USER_UNAVAILABLE";
	
	
	public final static String USER_OP_EMPLOYEE_INFO = "EMPLOYEE_INFO";
	public final static String USER_OP_APPOINT_INFO = "APPOINT_INFO";
	public final static String APPLICATION_SERVICE_CENTER = "APPLICATION_SERVICE_CENTER";
	public final static String APPLICATION_LOCK = "APPLICATION_LOCK";
	public final static String APPLICATION_START_TIME = "APPLICATION_START_TIME";
	
	
	public final static String USER_OP_REGISTER = "REGISTER";
	public final static String USER_OP_REGISTER_PAGE = "REGISTER_PAGE";
	//customer operation
	public final static String USER_OP_CUSTOMER_LIST = "CUSTOMER_LIST";
	public final static String USER_OP_CUSTOMER_ADD = "CUSTOMER_ADD";
	public final static String USER_OP_CUSTOMER_MODIFY = "CUSTOMER_MODIFY";
	public final static String USER_OP_CUSTOMER_DELETE = "CUSTOMER_DELETE";
	public final static String USER_OP_CUSTOMER_SAVE = "CUSTOMER_SAVE";
	// org operation
	public final static String USER_OP_ORG_LIST = "ORG_LIST";
	public final static String USER_OP_ORG_ADD = "ORG_ADD";
	public final static String USER_OP_ORG_MODIFY = "ORG_MODIFY";
	public final static String USER_OP_ORG_DELETE = "ORG_DELETE";
	public final static String USER_OP_ORG_SAVE = "ORG_SAVE";
	
	
	// depart operation
	public final static String USER_OP_DEPART_LIST = "DEPART_LIST";
	public final static String USER_OP_DEPART_DELETE = "DEPART_DELETE";
	public final static String USER_OP_DEPART_SAVE = "DEPART_SAVE";
	
	
	// employee operation
	public final static String USER_OP_EMP_LIST = "EMP_LIST";
	public final static String USER_OP_EMP_ADD = "EMP_ADD";
	public final static String USER_OP_EMP_MODIFY = "EMP_MODIFY";
	public final static String USER_OP_EMP_DELETE = "EMP_DELETE";
	public final static String USER_OP_EMP_SAVE = "EMP_SAVE";
	
	// user operation
	public final static String USER_OP_USER_LIST = "USER_LIST";
	public final static String USER_OP_USER_ADD = "USER_ADD";
	public final static String USER_OP_USER_MODIFY = "USER_MODIFY";
	public final static String USER_OP_USER_DELETE = "USER_DELETE";
	public final static String USER_OP_USER_SAVE = "USER_SAVE";
	public final static String USER_OP_USER_PRIV_PAGE = "USER_PRIV_PAGE";
	public final static String USER_OP_USER_PRIV_SAVE = "USER_PRIV_SAVE";
	
	// service operation
	public final static String USER_OP_SERVICE_LIST = "SERVICE_LIST";
	public final static String USER_OP_SERVICE_ADD = "SERVICE_ADD";
	public final static String USER_OP_SERVICE_MODIFY = "SERVICE_MODIFY";
	public final static String USER_OP_SERVICE_DELETE = "SERVICE_DELETE";
	public final static String USER_OP_SERVICE_SAVE = "SERVICE_SAVE";
	
	// center operation
	public final static String USER_OP_CENTER_LIST = "CENTER_LIST";
	public final static String USER_OP_CENTER_ADD = "CENTER_ADD";
	public final static String USER_OP_CENTER_MODIFY = "CENTER_MODIFY";
	public final static String USER_OP_CENTER_DELETE = "CENTER_DELETE";
	public final static String USER_OP_CENTER_SAVE = "CENTER_SAVE";
	
	// window operation
	public final static String USER_OP_WINDOW_LIST = "WINDOW_LIST";
	public final static String USER_OP_WINDOW_ADD = "WINDOW_ADD";
	public final static String USER_OP_WINDOW_MODIFY = "WINDOW_MODIFY";
	public final static String USER_OP_WINDOW_DELETE = "WINDOW_DELETE";
	public final static String USER_OP_WINDOW_SAVE = "WINDOW_SAVE";
	
	// biz config operation
	public final static String USER_OP_BIZ_LIST = "BIZ_LIST";
	public final static String USER_OP_BIZ_ADD = "BIZ_ADD";
	public final static String USER_OP_BIZ_MODIFY = "BIZ_MODIFY";
	public final static String USER_OP_BIZ_DELETE = "BIZ_DELETE";
	public final static String USER_OP_BIZ_SAVE = "BIZ_SAVE";
	public final static String USER_OP_BIZ_USERSRV_SAVE = "BIZ_USERSRV_SAVE";
	public final static String USER_OP_QUEUE_RENEW = "QUEUE_RENEW";
	
	
	// queue ui config
	public final static String USER_OP_QUEUE_UI_CONFIG_MODIFY = "UI_CONFIG_MODIFY";
	public final static String USER_OP_QUEUE_UI_CONFIG_DELETE = "UI_CONFIG_DELETE";
	public final static String USER_OP_QUEUE_UI_CONFIG_SAVE = "UI_CONFIG_SAVE";

	
	// message code
	public final static String MESSAGE_INVALID_SUBMIT = "MESSAGE_INVALID_SUBMIT";
	public final static String MESSAGE_SAVE_SUCCESS = "MESSAGE_SAVE_SUCCESS";
	public final static String MESSAGE_DELETE_SUCCESS = "MESSAGE_DELETE_SUCCESS";
	public final static String MESSAGE_SERVICE_NO_CONFIG = "MESSAGE_SERVICE_NO_CONFIG";
	public final static String MESSAGE_RECORD_DUPLICATE = "MESSAGE_RECORD_DUPLICATE";
	public final static String MESSAGE_RELOAD_SUCCESS = "MESSAGE_RELOAD_SUCCESS";
	
	//
	public final static String USER_OP_FETCH_QUEUE_NUM_PAGE = "FETCH_QUEUE_NUM_PAGE";
	public final static String USER_OP_CALL_NUM_PAGE = "CALL_NUM_PAGE";
	public final static String USER_OP_CALL_NUM = "CALL_NUM";
	public final static String USER_OP_CALL_CONTROL = "CALL_CONTROL";
	public final static String USER_OP_FETCH_QUEUE_NUM = "FETCH_QUEUE_NUM";
	public final static String USER_OP_SYNC_QUEUE_AMT = "SYNC_QUEUE_AMT";
	public final static String USER_OP_QUERY_QUEUE_NUM = "QUERY_QUEUE_NUM";
	public final static String USER_OP_FETCH_QUERY = "FETCH_QUERY";
	public final static String USER_OP_FETCH_APPOINT_NUM = "FETCH_APPOINT_NUM";
	public final static String USER_OP_CENTER_CHOICE = "CENTER_CHOICE";
	
	public final static String USER_OP_SYSTEM_LOG = "SYSTEM_LOG";
	
	public final static String USER_OP_DISPLAY_QUEUE = "DISPLAY_QUEUE";
	
	public final static String USER_OP_SEARCH_SKU_LIST = "SKU_LIST";
	public final static String USER_OP_SEARCH_SKU_OPR = "SKU_OPR";
	public final static String USER_OP_SEARCH_ROLLOVER_LIST = "ROLLOVER_LIST";
	public final static String USER_OP_SEARCH_MAINTAIN_LIST = "MAINTAIN_LIST";
	public final static String USER_OP_ROLLOVER_HISTORY_LIST = "ROLLOVER_HISTORY_LIST";
	public final static String USER_OP_ROLLOVER_CONFIRM = "ROLLOVER_CONFIRM";
	public final static String USER_OP_MAINTAIN_CONFIRM = "MAINTAIN_CONFIRM";
	public final static String USER_OP_UPLOAD_PAGE = "UPLOAD_PAGE";
	public final static String USER_OP_UPLOAD_FILE = "UPLOAD_FILE";
	public final static String USER_OP_SHOW_SKU_INFO = "SHOW_SKU_INFO";
	public final static String USER_OP_MAINTAIN_HISTORY_LIST = "MAINTAIN_HISTORY_LIST";
	public final static String USER_OP_BR_HISTORY_LIST = "BR_HISTORY_LIST";
	
	public final static String USER_OP_ROLLOVER_ADD_PAGE = "ROLLOVER_ADD_PAGE";
	public final static String USER_OP_MAINTAIN_ADD_PAGE = "MAINTAIN_ADD_PAGE";
	public final static String USER_OP_SKU_BORROW_PAGE = "SKU_BORROW_PAGE";
	public final static String USER_OP_SKU_RETURN_PAGE = "SKU_RETURN_PAGE";
	
	public final static String USER_OP_ROLLOVER_ADD = "ROLLOVER_ADD";
	public final static String USER_OP_MAINTAIN_ADD = "MAINTAIN_ADD";
	public final static String USER_OP_SKU_BORROW = "SKU_BORROW";
	public final static String USER_OP_SKU_RETURN = "SKU_RETURN";
	
	public final static String USER_OP_SKU_DELETE = "SKU_DELETE";
	
	// object 
	public final static String AM_ASSETS_TYPE = "ASSETS_TYPE";
	public final static String AM_SKU_INFO = "SKU_INFO";
}
