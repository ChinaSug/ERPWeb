package com.abs.ps.web.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.abs.core.util.AbsBeanFactory;
import com.abs.ps.service.StockInfoService;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.StockSearchDto;
import com.abs.ps.web.dto.UserDto;

public class WorkbenchHelper {

	private Logger logger = Logger.getLogger(WorkbenchHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto curLoginUser = null;
	
	private StockInfoService stockInfoService = null;

	public WorkbenchHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context,int pageSize){
		this.request = request;
		this.response = response;
		this.context = context;
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		stockInfoService = (StockInfoService) factory.getBeanByName("stockInfoService");
		
		HttpSession session = request.getSession();
		curLoginUser = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
	}
	
	/**
	 * 返回工作台页面信息
	 * @throws ServletException
	 * @throws IOException
	 */
	public void forwardWorkbenchInfo() throws ServletException, IOException{
		logger.debug(" Get Workbench Info... ");
		
		// 获取少于安全库存量的物料
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("LOW_STOCK", "1");
		List<StockSearchDto> lowStockList = stockInfoService.getStockView(paramMap);
		request.setAttribute("LOW_STOCK", lowStockList);
		
		PageDispatcher.forward(request, response, "jsp/workbench.jsp");
	}
	
}
