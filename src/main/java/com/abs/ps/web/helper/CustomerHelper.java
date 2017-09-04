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

import com.abs.core.paging.IPaging;
import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.Customer;
import com.abs.ps.domain.Mould;
import com.abs.ps.domain.Warehouse;
import com.abs.ps.service.CustomerService;
import com.abs.ps.util.ERPWebUtil;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.CustomerDto;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.json.OptionDto;

public class CustomerHelper implements IControllerHelper {
	private Logger logger=Logger.getLogger(EmployeeHelper.class.getName()); 
		private HttpServletRequest request = null;
		private HttpServletResponse response = null;
		private ServletContext context = null;
		private CustomerService customerService = null;
		private ActionLogHelper actionLogHelper = null;
		private int pageSize = 10;
		private UserDto sessionUserDto = null;
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		public CustomerHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context,int pageSize){
			this.request = request;
			this.response = response;
			this.context = context;
			this.customerService=(CustomerService)factory.getBeanByName("customerService");
			this.pageSize =pageSize;
			HttpSession session = request.getSession();
			sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
			actionLogHelper = new ActionLogHelper(request, response, context, pageSize);
		}
	@Override
	public void doDelete() throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String[] delid = request.getParameterValues("delid");
		String delIds = StringHelper.join(",", delid);
		if (delid != null) {
			customerService.deleteObjectById(delIds);
			
		}
		doQuery();
		actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_DELETE,
					QueueConstants.AM_CUSTOMER,
					"客户编号:" + delIds,
					""
				)
			);
	}

	@Override
	public void doSave() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = FilterUtil.getParamStringMap(request.getParameterMap());
		CustomerDto custDto = new CustomerDto();
		FilterUtil.setObjectField(custDto, paramMap);
		
		custDto.setCreateBy(sessionUserDto.getUserId());
		custDto.setCreateDate(FilterUtil.getCurrentDateStr());
		custDto.setDepartOid(sessionUserDto.getEntOid());
		//custDto.setDepartOid(sessionUserDto.getEntOid());
		
		if (custDto.getOid() > 0) {
			CustomerDto oldDto = customerService.getObjectById(custDto.getOid());
			if (oldDto != null) {
				custDto.setCreateBy(oldDto.getCreateBy());
				custDto.setCreateDate(oldDto.getCreateDate());
			}
			Map<String,String> map = compareDate(oldDto,custDto);
			
			if(map!=null&&map.size()>0){
				String value="";
				 String key="";
				for(String key1 :map.keySet()){
					key+=key1+"、";
					value+=map.get(key1)+"、";
				}
				actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_MODIFY,
							QueueConstants.AM_CUSTOMER,
							key,value
						)
					);
			}
		}else{
			actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_ADD,
						QueueConstants.AM_CUSTOMER,
						"客户名称:" + custDto.getName(),
						""
					)
				);
		}
		
		customerService.saveOrUpdate(custDto);
		doQuery();
	}

	@Override
	public void doQuery() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pageNum = request.getParameter("pageNumber");
		int pageNumber = 1;
		try {
			pageNumber = Integer.parseInt(pageNum);
		} catch (Exception e) {
			pageNumber = 1;
		}
		
		Map<String, String> valueMap = FilterUtil.convertStrMap(request.getParameterMap(), true);
		//valueMap.put("departOid", String.valueOf(sessionUserDto.getEntOid())); // 默认返回当前用户所属单位的客户信息 
		
		IPaging paging = customerService.findObjectWithPaging(pageNumber, pageSize, valueMap);
		if(paging!=null){
			StringBuffer pagingUrl = new StringBuffer(request.getContextPath()+"/mgHandler.html?op_action="+QueueConstants.USER_OP_CUSTOMER_LIST);
			pagingUrl.append(ERPWebUtil.setPagingUrlParam(valueMap));
			paging.setURL(pagingUrl.toString());
		}
		request.setAttribute("SEARCH_MAP", valueMap);
		request.setAttribute(QueueConstants.QUEUE_PAGING, paging);
		PageDispatcher.forward(request, response, "jsp/customer_list.jsp");
	}

	public void doModify() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oidS = request.getParameter("oid");
		long oid = -1;
		try {
			oid = Long.parseLong(oidS);
		} catch (NumberFormatException e) {
			doQuery();
			return;
		}
		CustomerDto custdto = customerService.getObjectById(oid);
		
		request.setAttribute("CUSTOMER_DTO", custdto);
		PageDispatcher.forward(request, response, "jsp/customer_maintain.jsp");
	}
	
	public void doAdd() throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PageDispatcher.forward(request, response, "jsp/customer_maintain.jsp");
	}
	
	
	
	public void getCustomers() throws ServletException, IOException{
		String name = request.getParameter("name");
		List<Customer> lists = customerService.findCustomers(name);
		List<OptionDto> optionDtos = new ArrayList<OptionDto>();
//		optionDtos.add(new OptionDto());
		for (Customer obj : lists) {
			OptionDto dto = new OptionDto();
			dto.setValue(obj.getOid().toString());
			dto.setText(obj.getName());
			optionDtos.add(dto);
		}
		
		String resutlStr = JsonUtils.toJSONString(optionDtos);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	/**
	 * 比较修改前后客户的值
	 * @param oldDto
	 * @param newDto
	 * @return map<String,String>
	 */
	public Map<String,String> compareDate(CustomerDto oldDto,CustomerDto newDto){
		Map<String,String> map = new HashMap<>();
		if(oldDto.getName().equals(newDto.getName())==false){
			map.put(oldDto.getName(),newDto.getName());
		}
		if(oldDto.getContactPerson().equals(newDto.getContactPerson())==false){
			map.put(oldDto.getContactPerson(),newDto.getContactPerson());
		}
		if(oldDto.getMobileNum().equals(newDto.getMobileNum())==false){
			map.put(oldDto.getMobileNum(),newDto.getMobileNum());
		}
		if(oldDto.getEmail().equals(newDto.getEmail())==false){
			map.put(oldDto.getEmail(),newDto.getEmail());
		}
		if(oldDto.getStatus().equals(newDto.getStatus())==false){
			if(oldDto.getStatus().equals("1")){
			map.put("状态：正常","失效");
			}else{
				map.put("状态：失效","正常");
			}
		}
		return map;
		
	}
}
