package com.abs.ps.web.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abs.ps.util.QueueConstants;

public final class HelperFactory {
	private HelperFactory() {}
	
	public static IControllerHelper getInstance(HttpServletRequest request, HttpServletResponse response, ServletContext context, int pageSize) {
		String action = request.getParameter("op_action");
		if (isUserHelper(action)) {
			return new UserHelper(request,response,context, pageSize);
		} else if (isOrgHelper(action)) {
			return new OrganizationHelper(request,response,context, pageSize);
		} else if (isDepartHelper(action)) {
			return new DepartmentHelper(request,response,context, pageSize);
		} else if (isEMPHelper(action)) {
			return new EmployeeHelper(request,response,context, pageSize);
		} else if (isSupplierHelper(action)) {
			return new SupplierHelper(request, response, context);
		} else if (isScheduleHelper(action)) {
			return new ScheduleHelper(request, response, context);
		} else if (isItemHelper(action)) {
			return new ItemHelper(request, response, context);
		} else if (isWarehouseHelper(action)) {
			return new WarehouseHelper(request, response, context,pageSize);
		} else if (isMachineHelper(action)) {
			return new MachineHelper(request, response, context);
		} else if (isMouldHelper(action)) {
			return new MouldHelper(request, response, context, pageSize);
		} else if (isItemTypeHelper(action)) {
			return new ItemTypeHelper(request, response, context);
		} else if (isStockTypeHelper(action)) {
			return new StockTypeHelper(request, response, context);
		} else if (isStockInfoHelper(action)) {
			StockInfoHelper helper = new StockInfoHelper(request, response, context);
			if (action.startsWith("STOCK_IN")) {
				helper.setStockType("1");
			} else if (action.startsWith("STOCK_OUT")) {
				helper.setStockType("2");
			}
			return helper;
		} else if (isCheckPointHelper(action)) {
			return new StockCheckPointHelper(request, response, context);
		} else if (isDamageHelper(action)) {
			return new DamageHelper(request, response, context);
		} else if(isCustomerHelper(action)){
			return new CustomerHelper(request, response, context,pageSize);
		}else if(isBomInfoHelper(action)){
			return new  BomInfoHelper(request, response, context, pageSize);
		} else if (isProduceControlHelper(action)) {
			return new  ProductControlHelper(request, response, context);
		}
		return null;
	}
	
	private static boolean isProduceControlHelper(String action){
		if ("PC_LIST".equals(action) || "PC_SAVE".equals(action) || "PC_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isBomInfoHelper(String action){
		if ("BOMINFO_LIST".equals(action) || "BOMINFO_SAVE".equals(action) || "BOMINFO_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	private static boolean isCustomerHelper(String action){
		if(QueueConstants.USER_OP_CUSTOMER_LIST.equals(action)||QueueConstants.USER_OP_CUSTOMER_SAVE.equals(action)
				||QueueConstants.USER_OP_CUSTOMER_DELETE.equals(action) || QueueConstants.USER_OP_CUSTOMER_ADD.equals(action) || QueueConstants.USER_OP_CUSTOMER_MODIFY.equals(action)){
			return true;
		}
		return false;
	}
	
	private static boolean isDamageHelper(String action) {
		if ("DM_LIST".equals(action) || "DM_SAVE".equals(action) || "DM_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isCheckPointHelper(String action) {
		if ("CP_LIST".equals(action) || "CP_SAVE".equals(action) || "CP_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isStockInfoHelper(String action) {
		if ("STOCK_IN_LIST".equals(action) || "STOCK_IN_SAVE".equals(action) || "STOCK_IN_DEL".equals(action)
				|| "STOCK_OUT_LIST".equals(action) || "STOCK_OUT_SAVE".equals(action) || "STOCK_OUT_DEL".equals(action)
				) {
			return true;
		}
		return false;
	}
	
	private static boolean isStockTypeHelper(String action) {
		if ("ST_LIST".equals(action) || "ST_SAVE".equals(action) || "ST_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isItemTypeHelper(String action) {
		if ("IT_LIST".equals(action) || "IT_SAVE".equals(action) || "IT_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isMouldHelper(String action) {
		if ("MOULD_LIST".equals(action) || "MOULD_SAVE".equals(action) || "MOULD_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isMachineHelper(String action) {
		if ("MACHINE_LIST".equals(action) || "MACHINE_SAVE".equals(action) || "MACHINE_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isWarehouseHelper(String action) {
		if ("WHS_LIST".equals(action) || "WHS_SAVE".equals(action) || "WHS_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isItemHelper(String action) {
		if ("ITEM_LIST".equals(action) || "ITEM_SAVE".equals(action) || "ITEM_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isScheduleHelper(String action) {
		if ("SCHED_LIST".equals(action) || "SCHED_SAVE".equals(action) || "SCHED_DEL".equals(action)
				|| "SCHED_SUB_LIST".equals(action) || "SCHED_SUB_SAVE".equals(action) || "SCHED_SUB_DEL".equals(action) ) {
			return true;
		}
		return false;
	}
	
	private static boolean isSupplierHelper(String action) {
		if ("SUPPLIER_LIST".equals(action) || "SUPPLIER_SAVE".equals(action) || "SUPPLIER_DEL".equals(action)) {
			return true;
		}
		return false;
	}
	
	private static boolean isContractHelper(String action) {
		if ("CONT_SAVE".equals(action) || "CONT_LIST".equals(action) || "CONT_ADD".equals(action)
				|| "CONT_DELETE".equals(action) || "CONT_MODIFY".equals(action)) {
			return true;
		}
		return false;
	}

	private static boolean isHouseInfoHelper(String action) {
		if ("HOUSE_SAVE".equals(action) || "HOUSE_LIST".equals(action) || "HOUSE_ADD".equals(action)
				|| "HOUSE_DELETE".equals(action) || "HOUSE_MODIFY".equals(action)) {
			return true;
		}
		return false;
	}

	private static boolean isOrgHelper(String action) {
		if (QueueConstants.USER_OP_ORG_LIST.equals(action) ||
				QueueConstants.USER_OP_ORG_ADD.equals(action) ||
				QueueConstants.USER_OP_ORG_SAVE.equals(action) ||
				QueueConstants.USER_OP_ORG_MODIFY.equals(action) ||
				QueueConstants.USER_OP_ORG_DELETE.equals(action)) {
			return true;
		} 
		return false;
	}
	
	private static boolean isUserHelper(String action) {
		if (QueueConstants.USER_OP_USER_LIST.equals(action) ||
				QueueConstants.USER_OP_USER_ADD.equals(action) ||
				QueueConstants.USER_OP_USER_SAVE.equals(action) ||
				QueueConstants.USER_OP_USER_MODIFY.equals(action) ||
				QueueConstants.USER_OP_USER_DELETE.equals(action)) {
			return true;
		} 
		return false;
	}
	
	private static boolean isDepartHelper(String action) {
		if ("DEPART_LIST".equals(action) ||"DEPART_SAVE".equals(action) ||"DEPART_DELETE".equals(action)) {
			return true;
		} 
		return false;
	}
	
	private static boolean isEMPHelper(String action) {
		if (QueueConstants.USER_OP_EMP_LIST.equals(action) ||
				QueueConstants.USER_OP_EMP_ADD.equals(action) ||
				QueueConstants.USER_OP_EMP_SAVE.equals(action) ||
				QueueConstants.USER_OP_EMP_MODIFY.equals(action) ||
				QueueConstants.USER_OP_EMP_DELETE.equals(action)) {
			return true;
		} 
		return false;
	}
	
}
