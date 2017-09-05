package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.util.StringHelper;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.Mould;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.MouldDto;
import com.abs.ps.web.dto.UserDto;

public class MouldHelper implements IControllerHelper{
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private BaseInfoService baseInfoService;
	private ActionLogHelper actionLogHelper = null;
	private int pageSize = 10;
	
	public MouldHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context,int pageSize){
		this.request = request;
		this.response = response;
		this.context = context;
		this.pageSize = pageSize;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.baseInfoService = (BaseInfoService) factory.getBeanByName("baseInfoService");
		actionLogHelper = new ActionLogHelper(request, response, context, pageSize);
	}
	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					Mould mould =  (Mould)baseInfoService.getEntityByOid(Mould.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
							actionLogHelper.generateActionLog(
								ActionLogHelper.ACTION_TYPE_DELETE,
								QueueConstants.AM_MACHINE,
								"模具名称:" + mould.getName(),
								""
							)
						);
					baseInfoService.delete(Mould.class, Long.parseLong(delId));
				}
			} else {
				
				Mould mould =  (Mould)baseInfoService.getEntityByOid(Mould.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_MACHINE,
							"模具名称:" + mould.getName(),
							""
						)
					);
				baseInfoService.delete(Mould.class, Long.parseLong(deleteIds));
			}
		}
		
		doQuery();
	}

	@Override
	public void doSave() throws ServletException, IOException {
		Map<String, Object> paramMap = FilterUtil.getParamStringMap(request.getParameterMap(), false);
		Mould obj = FilterUtil.getInstance(paramMap, Mould.class);
		long oid = obj.getOid() != null ? obj.getOid() : 0;
		if (oid <= 0) {
			obj.setOid(null);
		}
		baseInfoService.saveObject(obj);
		
		doQuery();
	}


	@Override
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
		
		ListResult<MouldDto> results = baseInfoService.findMouldWithPaging(pageNumber, pageSize, name);
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(results));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	@Override
	public void doModify() throws ServletException, IOException {
		
	}
	@Override
	public void doAdd() throws ServletException, IOException {
		
	}
	
}
