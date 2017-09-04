package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.util.StringHelper;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.Customer;
import com.abs.ps.domain.OrderManager;
import com.abs.ps.service.CustomerService;
import com.abs.ps.service.OrderManagerService;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.OrderManagerDto;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.json.OptionDto;

public class OrderManagerHelper implements IControllerHelper{
	private Logger logger = Logger.getLogger(OrderManagerHelper.class.getName());
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private ActionLogHelper actionLogHelper = null;
	private OrderManagerService orderManagerService;
	private CustomerService customerService;
	
	public OrderManagerHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		actionLogHelper = new ActionLogHelper(request, response, context,10);
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.orderManagerService = (OrderManagerService) factory.getBeanByName("orderManagerService");
		this.customerService = (CustomerService) factory.getBeanByName("customerService");
	}
	
	@Override
	public void doDelete() throws ServletException, IOException {
		logger.debug("-------------------doDelete------------------");
		
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					orderManagerService.delete(OrderManager.class, Long.parseLong(delId));
				}
			} else {
				orderManagerService.delete(OrderManager.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
	}
	
	
	@Override
	public void doSave() throws ServletException, IOException {
		logger.debug("-------------------doSave------------------");
		/*String oid = request.getParameter("oid");
		String acceptDate= request.getParameter("acceptDate");
		String customerOid = request.getParameter("customerOid");
		String orderNum = request.getParameter("orderNum");
		String projectNum = request.getParameter("projectNum");
		String productNum = request.getParameter("productNum");
		String specificationName = request.getParameter("specificationName");
		String orderCount = request.getParameter("orderCount");
		String completeCount = request.getParameter("completeCount");
		String unCompleteCount = request.getParameter("unCompleteCount");
		String delivery = request.getParameter("delivery");
		String startDate = request.getParameter("startDate");
		String finishDate = request.getParameter("finishDate");
		String status = request.getParameter("status");
		String remark = request.getParameter("remark");

		OrderManager obj=null;
		if(StringHelper.isEmpty(oid)){
			obj = new OrderManager();
		}else{
			obj = (OrderManager)orderManagerService.getEntityByOid(OrderManager.class,Long.parseLong(oid));
		}
		if(!StringHelper.isEmpty(acceptDate)){
			obj.setAcceptDate(DateHelper.convert2Date(acceptDate,"yyyy-MM-dd"));
		}
		if(!StringHelper.isEmpty(customerOid)){
			obj.setCustomerOid(Long.parseLong(customerOid));
		}
		if(!StringHelper.isEmpty(orderNum)){
			obj.setOrderNum(orderNum);
		}
		if(!StringHelper.isEmpty(projectNum)){
			obj.setProjectNum(projectNum);
		}
		if(!StringHelper.isEmpty(productNum)){
			obj.setProductNum(productNum);
		}
		if(!StringHelper.isEmpty(specificationName)){
			obj.setSpecificationName(specificationName);
		}
		if(!StringHelper.isEmpty(orderCount)){
			obj.setOrderCount(orderCount);
		}
		if(!StringHelper.isEmpty(completeCount)){
			obj.setCompleteCount(completeCount);
		}
		if(!StringHelper.isEmpty(unCompleteCount)){
			obj.setUnCompleteCount(unCompleteCount);
		}
		if(!StringHelper.isEmpty(delivery)){
			obj.setDelivery(DateHelper.convert2Date(delivery,"yyyy-MM-dd"));
		}
		if(!StringHelper.isEmpty(startDate)){
			obj.setStartDate(DateHelper.convert2Date(startDate,"yyyy-MM-dd"));
		}
		if(!StringHelper.isEmpty(finishDate)){
			obj.setFinishDate(DateHelper.convert2Date(finishDate,"yyyy-MM-dd"));
		}
		if(!StringHelper.isEmpty(status)){
			obj.setStatus(status);
		}
		if(!StringHelper.isEmpty(remark)){
			obj.setRemark(remark);
		}*/
		
		Map<String, Object> paramMap = FilterUtil.getParamStringMap(request.getParameterMap());
		OrderManager obj = FilterUtil.getInstance(paramMap, OrderManager.class);
		long oid = obj.getOid() != null ? obj.getOid() : 0;
		if (oid <= 0) {
			obj.setOid(null);
		}
		
		orderManagerService.saveObject(obj);
		doQuery();
	}
	
	@Override
	public void doModify() throws ServletException, IOException {

	}

	@Override
	public void doAdd() throws ServletException, IOException {

	}

	@Override
	public void doQuery() throws ServletException, IOException {
		logger.debug("-------------------doQuery------------------");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String pageNumStr = request.getParameter("pageIndex");
		String currentDate=FilterUtil.getCurrentDateStr();
		int pageNumber = 1;
		if (!StringHelper.isEmpty(pageNumStr)) {
			pageNumber = Integer.parseInt(pageNumStr);
		}
		String pageSizeStr = request.getParameter("limit");
		int pageSize = 10;
		if (!StringHelper.isEmpty(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		Map<String, String> valueMap=new HashMap<>();
		valueMap.put("startDate", startDate);
		valueMap.put("endDate", endDate);
		valueMap.put("currentDate",currentDate);
//		System.out.println("--------------startDate"+valueMap.get("startDate"));
//		System.out.println("--------------endDate"+valueMap.get("endDate"));
		ListResult<OrderManagerDto> results = orderManagerService.findOrderManagerWithPaging(pageNumber, pageSize, valueMap);
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(results));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		out.flush();
		out.close();
	}
	
}
