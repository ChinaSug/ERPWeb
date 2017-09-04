package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.util.StringHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.DateHelper;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.ProductControlMain;
import com.abs.ps.domain.ScheduleDetail;
import com.abs.ps.domain.ScheduleMain;
import com.abs.ps.service.ScheduleService;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ScheduleDetailDto;
import com.abs.ps.web.dto.ScheduleMainDto;
import com.abs.ps.web.dto.UserDto;

public class ScheduleHelper implements IControllerHelper{
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private ScheduleService scheduleService;
	private ActionLogHelper actionLogHelper = null;
	
	public ScheduleHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.scheduleService = (ScheduleService) factory.getBeanByName("scheduleService");
		actionLogHelper = new ActionLogHelper(request, response, context, 10);
	}
	
	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					ScheduleMain scheduleMain =  (ScheduleMain)scheduleService.getEntityByOid(ScheduleMain.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
							actionLogHelper.generateActionLog(
								ActionLogHelper.ACTION_TYPE_DELETE,
								QueueConstants.AM_SCHD_MAIN,
								"工程管理名称:" + scheduleMain.getName(),
								""
							)
						);
					scheduleService.delete(ScheduleMain.class, Long.parseLong(delId));
					scheduleService.delete(ScheduleDetail.class, "scheduleOid", Long.parseLong(delId));
				}
			} else {
				ScheduleMain scheduleMain =  (ScheduleMain)scheduleService.getEntityByOid(ScheduleMain.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_SCHD_MAIN,
							"工程管理名称:" + scheduleMain.getName(),
							""
						)
					);
				scheduleService.delete(ScheduleMain.class, Long.parseLong(deleteIds));
				scheduleService.delete(ScheduleDetail.class, "scheduleOid", Long.parseLong(deleteIds));
			}
		}
		doQuery();
		
		
	}	
	
	public void deleteDetail() throws ServletException, IOException {
		String scheduleOid = request.getParameter("sched_oid");	
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					scheduleService.delete(ScheduleDetail.class, Long.parseLong(delId));
				}
			} else {
				scheduleService.delete(ScheduleDetail.class, Long.parseLong(deleteIds));
			}
		}
		findScheduleDetailDtos(scheduleOid);
		actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_DELETE,
					QueueConstants.AM_SCHD_DETAIL,
					"工程管理子编号:" + deleteIds,
					""
				)
			);
	}	

	@Override
	public void doSave() throws ServletException, IOException {		
		String oid = request.getParameter("oid");
		String customerOid = request.getParameter("customerOid");
		String prodId = request.getParameter("prodId");
		String scheduleDate = request.getParameter("scheduleDate");
		String respPerson = request.getParameter("respPerson");
		String name = request.getParameter("name");
		String prodAmt = request.getParameter("prodAmt");
		String itemOid = request.getParameter("itemOid");
		String prodNum = request.getParameter("prodNum");
		String productAmt = request.getParameter("productAmt");
		String projectId = request.getParameter("projectId");
		String projectDate = request.getParameter("projectDate");
		String prodPicUrl = request.getParameter("prodPicUrl");
		String fetchStandardNum = request.getParameter("fetchStandardNum");
		String fetchActualNum = request.getParameter("fetchActualNum");
		String shotStandardWgt = request.getParameter("shotStandardWgt");
		String shotActualWgt = request.getParameter("shotActualWgt");
		String prodStandardWgt = request.getParameter("prodStandardWgt");
		String prodActualWgt = request.getParameter("prodActualWgt");
		String pwStandardWgt = request.getParameter("pwStandardWgt");
		String pwActualWgt = request.getParameter("pwActualWgt");
		String mouldTgCompleteDate = request.getParameter("mouldTgCompleteDate");
		String mouldActCompleteDate = request.getParameter("mouldActCompleteDate");
		String tryAmt = request.getParameter("tryAmt");
		String actAmt = request.getParameter("actAmt");
		String prodQc = request.getParameter("prodQc");
		String prodStandardJob = request.getParameter("prodStandardJob");
		String prodFqc = request.getParameter("prodFqc");
		String prodPkg = request.getParameter("prodPkg");
		String prodShape = request.getParameter("prodShape");
		String prodReport = request.getParameter("prodReport");
		String prodSignDate = request.getParameter("prodSignDate");
		String prodVerify = request.getParameter("prodVerify");
		String prodGo = request.getParameter("prodGo");
		String unitConsume = request.getParameter("unitConsume");

		ScheduleMain scheduleMain = null;
		if (StringHelper.isEmpty(oid)) {
			scheduleMain = new ScheduleMain();
			if (sessionUserDto != null) {
				scheduleMain.setCreateBy(this.sessionUserDto.getUserId());
			}
			scheduleMain.setCreateDate(new Date());
			scheduleMain.setStatus("0");
		} else {
			scheduleMain = (ScheduleMain) scheduleService.getEntityByOid(ScheduleMain.class, Long.parseLong(oid));
			
			if (!StringHelper.isEmpty(prodGo)) {
				scheduleMain.setStatus("1");
			}
		}
		
		scheduleMain.setActAmt(actAmt);
		if (!StringHelper.isEmpty(customerOid)) {
			scheduleMain.setCustomerOid(Long.parseLong(customerOid));
		}
		scheduleMain.setFetchActualNum(fetchActualNum);
		scheduleMain.setFetchStandardNum(fetchStandardNum);
		if (!StringHelper.isEmpty(itemOid)) {
			scheduleMain.setItemOid(Long.parseLong(itemOid));
		}
		if (!StringHelper.isEmpty(mouldActCompleteDate)) {
			scheduleMain.setMouldActCompleteDate(DateHelper.convert2Date(mouldActCompleteDate,DateHelper.DATE_FORMATE));
		}
		if (!StringHelper.isEmpty(mouldTgCompleteDate)) {
			scheduleMain.setMouldTgCompleteDate(DateHelper.convert2Date(mouldTgCompleteDate,DateHelper.DATE_FORMATE));
		}
		scheduleMain.setName(name);
		scheduleMain.setProdActualWgt(prodActualWgt);
		scheduleMain.setProdAmt(prodAmt);
		scheduleMain.setProdFqc(prodFqc);
		scheduleMain.setProdGo(prodGo);
		scheduleMain.setProdId(prodId);
		scheduleMain.setProdNum(prodNum);
		scheduleMain.setProdPicUrl(prodPicUrl);
		scheduleMain.setProdPkg(prodPkg);
		scheduleMain.setProdQc(prodQc);
		scheduleMain.setProdReport(prodReport);
		scheduleMain.setProdShape(prodShape);
		if (!StringHelper.isEmpty(prodSignDate)) {
			scheduleMain.setProdSignDate(DateHelper.convert2Date(prodSignDate,DateHelper.DATE_FORMATE));
		}
		scheduleMain.setProdStandardJob(prodStandardJob);
		scheduleMain.setProdStandardWgt(prodStandardWgt);
		scheduleMain.setProductAmt(productAmt);
		scheduleMain.setProductAmt(productAmt);
		scheduleMain.setProdVerify(prodVerify);
		if (!StringHelper.isEmpty(projectDate)) {
			scheduleMain.setProjectDate(DateHelper.convert2Date(projectDate,DateHelper.DATE_FORMATE));
		}
		scheduleMain.setProjectId(projectId);
		scheduleMain.setPwActualWgt(pwActualWgt);
		scheduleMain.setPwStandardWgt(pwStandardWgt);
		scheduleMain.setRespPerson(respPerson);
		if (!StringHelper.isEmpty(scheduleDate)) {
			scheduleMain.setScheduleDate(DateHelper.convert2Date(scheduleDate,DateHelper.DATE_FORMATE));
		}
		scheduleMain.setShotActualWgt(shotActualWgt);
		scheduleMain.setShotStandardWgt(shotStandardWgt);
		scheduleMain.setTryAmt(tryAmt);
		scheduleMain.setUnitConsume(unitConsume);

		if (sessionUserDto != null) {
			scheduleMain.setLastModifyBy(this.sessionUserDto.getUserId());
		}
		
		if(StringHelper.isEmpty(oid)){
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_ADD,
					QueueConstants.AM_SCHD_MAIN,
					"名称:" + name,
					""
				)
			);
		}else{
			ScheduleMain oldscheduleMain = (ScheduleMain) scheduleService.getEntityByOid(ScheduleMain.class, Long.parseLong(oid));
			Map<String,String> map = FieldValueComparator.compareObject(scheduleMain,oldscheduleMain);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_SCHD_MAIN,
					values[0],
					values[1]
				)
			);
		}
		scheduleService.saveObject(scheduleMain);
	
		doQuery();
	}

	
	public void saveDetail() throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String scheduleOid = request.getParameter("sched_oid");		
		String tryNum = request.getParameter("tryNum");
		String tryDate = request.getParameter("tryDate");
		String actualDate = request.getParameter("actualDate");
		String result = request.getParameter("result");
		String tryCallback = request.getParameter("tryCallback");

		ScheduleDetail detail = null; 
		if (StringHelper.isEmpty(oid)) {
			detail = new ScheduleDetail();
			detail.setCreateDate(new Date());
			if (!StringHelper.isEmpty(scheduleOid)) {
				detail.setScheduleOid(Long.parseLong(scheduleOid));
			}
		} else {
			detail = (ScheduleDetail) scheduleService.getEntityByOid(ScheduleDetail.class, Long.parseLong(oid));
		}
		detail.setTryNum(tryNum);
		if (!StringHelper.isEmpty(tryDate)) {
			detail.setTryDate(DateHelper.convert2Date(tryDate, DateHelper.DATE_FORMATE));
		}
		if (!StringHelper.isEmpty(actualDate)) {
			detail.setActualDate(DateHelper.convert2Date(actualDate, DateHelper.DATE_FORMATE));
		}
		detail.setResult(result);
		detail.setTryCallback(tryCallback);
		
		if(StringHelper.isEmpty(oid)){
			actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_ADD,
						QueueConstants.AM_SCHD_DETAIL,
						"编号:" + tryNum,
						""
					)
				);
			
		}else{
			ScheduleDetail oldScheduleDetail = (ScheduleDetail) scheduleService.getEntityByOid(ScheduleDetail.class, Long.parseLong(oid));
			Map<String,String> map = FieldValueComparator.compareObject(detail,oldScheduleDetail);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_MODIFY,
						QueueConstants.AM_SCHD_DETAIL,
						values[0],
						values[1]
					)
				);
		}
		scheduleService.saveObject(detail);
		
		//findScheduleDetailDtos(scheduleOid);
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
		
		//String searchName = request.getParameter("search_name");
		String search_customer = request.getParameter("search_customer");
		String search_schedDate = request.getParameter("search_schedDate");
		String search_status = request.getParameter("search_status");
		
		
		ScheduleMainDto criteria = new ScheduleMainDto();
		criteria.setCustomerName(search_customer);
		criteria.setScheduleDate(search_schedDate);
		criteria.setStatus(search_status);
		
		ListResult<ScheduleMainDto> result = scheduleService.findScheduleMainWithPaging(pageNumber, pageSize, criteria);
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(result));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
		
	}
	
	public void findScheduleDetailDtos(String schedOid) throws ServletException, IOException{
		String scheduleOid = request.getParameter("sched_oid");		
		if (!StringHelper.isEmpty(schedOid) ) {
			scheduleOid = schedOid;
		}
		List<ScheduleDetailDto> details = scheduleService.findScheduleDetailDtos(scheduleOid);
		String resutlStr = JsonUtils.toJSONString(details);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	
	public void getScheduleByOid() throws ServletException, IOException{
		String scheduleOid = request.getParameter("sched_oid");
		ScheduleMainDto scheduleMainDto = scheduleService.getScheduleByOid(scheduleOid);
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(scheduleMainDto));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}

	@Override
	public void doModify() throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doAdd() throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] argu) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"coreContext.xml", "beanContext.xml"});
		ScheduleService scheduleService = (ScheduleService) context.getBean("scheduleService");
		
//		ScheduleMain obj = new ScheduleMain();
//		obj.setCreateBy("huangwi2");
//		obj.setProdNum("111111");
//		obj.setProjectId("22222");
		ScheduleMainDto criteria = new ScheduleMainDto();
		ListResult<ScheduleMainDto> result = scheduleService.findScheduleMainWithPaging(1, 10, criteria);
		System.out.println("done result = " + result);
	}
	
}
