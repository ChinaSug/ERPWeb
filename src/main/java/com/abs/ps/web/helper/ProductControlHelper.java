package com.abs.ps.web.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.util.StringHelper;

import com.abs.core.util.AbsBeanFactory;
import com.abs.ps.domain.ProductControlDetail;
import com.abs.ps.domain.ProductControlMain;
import com.abs.ps.service.ProductControlService;
import com.abs.ps.util.Constants;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.FieldValueComparator;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.JsonUtils;
import com.abs.ps.util.PageDispatcher;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ProductControlDetailDto;
import com.abs.ps.web.dto.ProductControlMainDto;
import com.abs.ps.web.dto.UserDto;

public class ProductControlHelper implements IControllerHelper{
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private ProductControlService productControlService;
	private ActionLogHelper actionLogHelper = null;
	
	public ProductControlHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		actionLogHelper = new ActionLogHelper(request, response, context,10);
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.productControlService = (ProductControlService) factory.getBeanByName("productControlService");
	}
	
	@Override
	public void doDelete() throws ServletException, IOException {
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					ProductControlMain productControlMain =  (ProductControlMain)productControlService.getEntityByOid(ProductControlMain.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_PC,
							"生产控制单号:" + productControlMain.getPcNum(),
							""
						)
					);
					productControlService.delete(ProductControlDetail.class, "pcOid", Long.parseLong(delId));
					productControlService.delete(ProductControlMain.class, Long.parseLong(delId));
					
				}
			} else {
				ProductControlMain productControlMain =  (ProductControlMain)productControlService.getEntityByOid(ProductControlMain.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_DELETE,
						QueueConstants.AM_PC,
						"生产控制单号:" + productControlMain.getPcNum(),
						""
					)
				);
				productControlService.delete(ProductControlDetail.class, "pcOid", Long.parseLong(deleteIds));
				productControlService.delete(ProductControlMain.class, Long.parseLong(deleteIds));
			}
		}
		doQuery();
	}
	
	public void deleteDetail() throws ServletException, IOException {
		String pcOid = request.getParameter("pcOid");	
		String deleteIds = request.getParameter("ids");
		if (!StringHelper.isEmpty(deleteIds)) {
			if (deleteIds.indexOf(",") >= 0) {
				String[] delIdArr = deleteIds.split(",");
				for (String delId : delIdArr) {
					ProductControlDetail productControlDetail =  (ProductControlDetail)productControlService.getEntityByOid(ProductControlDetail.class, Long.parseLong(delId));
					actionLogHelper.saveActionLog(
						actionLogHelper.generateActionLog(
							ActionLogHelper.ACTION_TYPE_DELETE,
							QueueConstants.AM_SUB_PC,
							"班别:" + productControlDetail.getDutyId(),
							""
						)
					);
					
					productControlService.delete(ProductControlDetail.class, Long.parseLong(delId));
				}
			} else {
				ProductControlDetail productControlDetail =  (ProductControlDetail)productControlService.getEntityByOid(ProductControlDetail.class, Long.parseLong(deleteIds));
				actionLogHelper.saveActionLog(
					actionLogHelper.generateActionLog(
						ActionLogHelper.ACTION_TYPE_DELETE,
						QueueConstants.AM_SUB_PC,
						"班别:" + productControlDetail.getDutyId(),
						""
					)
				);
				productControlService.delete(ProductControlDetail.class, Long.parseLong(deleteIds));
			}
		}
		findProductControlDetails(pcOid);
	}	
	
	
	public void saveDetail() throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String pcOid= request.getParameter("pcOid");
		String pcType= request.getParameter("pcType");
		String produceDate= request.getParameter("produceDate");
		String dutyId= request.getParameter("dutyId");
		String planProduceAmt= request.getParameter("planProduceAmt");
		String actualProduceAmt= request.getParameter("actualProduceAmt");
		String goodProductAmt= request.getParameter("goodProductAmt");
		String badProductAmt= request.getParameter("badProductAmt");
		String efficiencyRate= request.getParameter("efficiencyRate");
		String badRate= request.getParameter("badRate");
		String remark= request.getParameter("remark");
		
		ProductControlDetail pcDetail = null;
		if (StringHelper.isEmpty(oid)) {
			pcDetail = new ProductControlDetail();
			if (sessionUserDto != null) {
				pcDetail.setCreateBy(this.sessionUserDto.getUserId());
			}
			pcDetail.setCreateDate(new Date());
			
		} else {
			pcDetail = (ProductControlDetail) productControlService.getEntityByOid(ProductControlDetail.class, Long.parseLong(oid));
		}
		if (!StringHelper.isEmpty(pcOid)) {
			pcDetail.setPcOid(Long.parseLong(pcOid));
		}
		
		pcDetail.setPcType(pcType);
		if (!StringHelper.isEmpty(produceDate)) {
			pcDetail.setProduceDate(DateHelper.convert2Date(produceDate, DateHelper.DATE_FORMATE));
		}
		pcDetail.setDutyId(dutyId);
		if (!StringHelper.isEmpty(planProduceAmt)) {
			pcDetail.setPlanProduceAmt(Double.parseDouble(planProduceAmt));
		}
		if (!StringHelper.isEmpty(actualProduceAmt)) {
			pcDetail.setActualProduceAmt(Double.parseDouble(actualProduceAmt));
		}
		if (!StringHelper.isEmpty(goodProductAmt)) {
			pcDetail.setGoodProductAmt(Double.parseDouble(goodProductAmt));
		}
		if (!StringHelper.isEmpty(badProductAmt)) {
			pcDetail.setBadProductAmt(Double.parseDouble(badProductAmt));
		}
		pcDetail.setEfficiencyRate(efficiencyRate);
		pcDetail.setBadRate(badRate);
		pcDetail.setRemark(remark);
		
		if (StringHelper.isEmpty(oid)) {
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_ADD,
					QueueConstants.AM_SUB_PC,
					"生产控制班次:" + produceDate,
					""
				)
			);
		} else {
			ProductControlDetail productControlDetail = (ProductControlDetail) productControlService.getEntityByOid(ProductControlDetail.class, Long.parseLong(oid));
			Map<String,String> map = FieldValueComparator.compareObject(pcDetail,productControlDetail);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_SUB_PC,
					values[0],
					values[1]
				)
			);
		}
		productControlService.saveObject(pcDetail);
		
		/*
		改为本次修改不判断是否超过生产值
		ProductControlMain productControlMain = (ProductControlMain) productControlService.getEntityByOid(ProductControlMain.class, Long.parseLong(pcOid));
		if (productControlService.isExceedProdAmt(productControlMain.getOid())) {
			PrintWriter out = response.getWriter();
			productControlMain.setStatus("1");
			productControlMain.setLastModifyBy(sessionUserDto.getUserId());
			productControlMain.setLastModifyDate(new Date());
			productControlService.saveObject(productControlMain);
			ListResult<Object> result = new ListResult<>();
			result.setHasError("true");
			result.setError("超过当前生产控制单生产数，控制单状态改为已完成");
			out.print(JsonUtils.filter(JsonUtils.toJSONString(result)));
		} else {*/
			findProductControlDetails(pcOid);
		// }
	}

	@Override
	public void doSave() throws ServletException, IOException {
		String oid = request.getParameter("oid");
		String pcNum = request.getParameter("pcNum");
		String departOid = request.getParameter("departOid");
		String publishPerson = request.getParameter("publishPerson");
		String publishDate = request.getParameter("publishDate");
		String confirmPerson = request.getParameter("confirmPerson");
		String machineOid = request.getParameter("machineOid");
		String mouldOid= request.getParameter("mouldOid");
		String prodNum = request.getParameter("prodNum");
		String stubBar= request.getParameter("stubBar");
		String weight= request.getParameter("weight");
		String period= request.getParameter("period");
		String caveNum= request.getParameter("fetchActualNum");
		String dailyTargetAmt= request.getParameter("dailyTargetAmt");
		String pcDate= request.getParameter("pcDate");
		String prodAmt= request.getParameter("prodAmt");
		String unitConsume= request.getParameter("unitConsume");
		String plasticType= request.getParameter("plasticType");
		String plasticWeight= request.getParameter("plasticWeight");
		String targetStartDate= request.getParameter("targetStartDate");
		String targetEndDate= request.getParameter("targetEndDate");
		String planTime= request.getParameter("planTime");
		String bomDetailOid= request.getParameter("bomDetailOid");
		String status= request.getParameter("status");
		
		String materialName = request.getParameter("materialName"); 
		String materialNum = request.getParameter("materialNum"); 
		String loss = request.getParameter("loss"); 
		String itemOid = request.getParameter("itemOid"); 
		String remark = request.getParameter("remark");
		
		ProductControlMain pcMain = null;
		if (StringHelper.isEmpty(oid) || "N".equals(oid)) {
			pcMain = new ProductControlMain();
			if (sessionUserDto != null) {
				pcMain.setCreateBy(this.sessionUserDto.getUserId());
			}
			pcMain.setCreateDate(new Date());
			
		} else {
			pcMain = (ProductControlMain) productControlService.getEntityByOid(ProductControlMain.class, Long.parseLong(oid));
		}
		pcMain.setPcNum(pcNum);
		if (!StringHelper.isEmpty(departOid)) {
			pcMain.setDepartOid(Long.parseLong(departOid));
		}
		pcMain.setPublishPerson(publishPerson);
		if (!StringHelper.isEmpty(publishDate)) {
			pcMain.setPublishDate(DateHelper.convert2Date(publishDate, DateHelper.DATE_FORMATE));
		}
		pcMain.setConfirmPerson(confirmPerson);
		if (!StringHelper.isEmpty(machineOid)) {
			pcMain.setMachineOid(Long.parseLong(machineOid));
		}
		if (!StringHelper.isEmpty(mouldOid)) {
			pcMain.setMouldOid(Long.parseLong(mouldOid));
		}
		pcMain.setProdNum(prodNum);
		pcMain.setStubBar(stubBar);
		pcMain.setWeight(weight);
		pcMain.setPeriod(period);
		pcMain.setCaveNum(caveNum);
		pcMain.setDailyTargetAmt(dailyTargetAmt);
		if (!StringHelper.isEmpty(pcDate)) {
			pcMain.setPcDate(DateHelper.convert2Date(pcDate, DateHelper.DATE_FORMATE));
		}
		pcMain.setProdAmt(prodAmt);
		pcMain.setUnitConsume(unitConsume);
		pcMain.setPlasticType(plasticType);
		pcMain.setPlasticWeight(plasticWeight);
		if (!StringHelper.isEmpty(targetStartDate)) {
			pcMain.setTargetStartDate(DateHelper.convert2Date(targetStartDate, DateHelper.DATE_FORMATE));
		}
		if (!StringHelper.isEmpty(targetEndDate)) {
			pcMain.setTargetEndDate(DateHelper.convert2Date(targetEndDate, DateHelper.DATE_FORMATE));
		}
		pcMain.setPlanTime(planTime);
		if (!StringHelper.isEmpty(bomDetailOid)) {
			pcMain.setBomDetailOid(Long.parseLong(bomDetailOid));
		}
		pcMain.setMaterialName(materialName);
		pcMain.setMaterialNum(materialNum);
		pcMain.setLoss(loss);
		if (!StringHelper.isEmpty(itemOid)) {
			pcMain.setItemOid(Long.parseLong(itemOid));
		}
		pcMain.setLastModifyBy(sessionUserDto.getUserId());
		pcMain.setLastModifyDate(new Date());
		pcMain.setRemark(remark);
		
		if(StringHelper.isEmpty(oid) || "N".equals(oid)){
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_ADD,
					QueueConstants.AM_PC,
					"生产控制单号:" + pcNum,
					""
				)
			);
			pcMain.setStatus("0");
		}else{
			ProductControlMain productControlMain = (ProductControlMain) productControlService.getEntityByOid(ProductControlMain.class, Long.parseLong(oid));
			Map<String,String> map = FieldValueComparator.compareObject(pcMain,productControlMain);
			String[] values = FieldValueComparator.split(map);
			actionLogHelper.saveActionLog(
				actionLogHelper.generateActionLog(
					ActionLogHelper.ACTION_TYPE_MODIFY,
					QueueConstants.AM_PC,
					values[0],
					values[1]
				)
			);
			
			// 若生产控制单的实际生产数超过生产数则设置状态为已完成
			if (productControlService.isExceedProdAmt(productControlMain.getOid())) {
				pcMain.setStatus("1");
			}
		}
		productControlService.saveObject(pcMain);
		
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
		
		String searchProdId = request.getParameter("search_prodId");
		String searchStatus = request.getParameter("search_status");
		String search_publishDate = request.getParameter("search_publishDate");
		
		ProductControlMainDto criteria = new ProductControlMainDto();
		criteria.setProdId(searchProdId);
		criteria.setStatus(searchStatus);
		criteria.setPublishDate(search_publishDate);
		
		ListResult<ProductControlMainDto> result = productControlService.findProductControlMainWithPaging(pageNumber, pageSize, criteria);
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(result));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
		
	}
	
	public void getProductControlByPcOid()  throws ServletException, IOException {
		String pcOidStr = request.getParameter("pcOid");
		long pcOid = 0;
		if (!StringHelper.isEmpty(pcOidStr) && !"undefined".equals(pcOidStr)) {
			pcOid = Long.parseLong(pcOidStr);
		}
		ProductControlMainDto pcMainDto = productControlService.getProductControlByPcOid(pcOid);
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(pcMainDto));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
		
	}
	
	public void findProductControlDetails(String pcOidIn) throws ServletException, IOException {
		String pageNumStr = request.getParameter("pageIndex");
		int pageNumber = 1;
		if (!StringHelper.isEmpty(pageNumStr)) {
			pageNumber = Integer.parseInt(pageNumStr);
		}
		
		String pageSizeStr = request.getParameter("limit");
		int pageSize = 5;
		if (!StringHelper.isEmpty(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		long pcmOid = FilterUtil.filterString2Long(request.getParameter("pcOid"));
		if (!StringHelper.isEmpty(pcOidIn)) {
			pcmOid = Long.parseLong(pcOidIn);
		} else if (pcmOid <= 0) {
			System.out.println("没有生产控制表单Oid");
			return;
		}
		// List<ProductControlDetailDto> pcDetails = productControlService.findProductControlDetails(pcOid);
		ListResult<ProductControlDetailDto> result = 
				productControlService.findPCDetailPaging(pageNumber, pageSize, pcmOid);
		
		String resultStr = JsonUtils.filter(JsonUtils.toJSONString(result));
		PrintWriter out = response.getWriter();
		out.print(resultStr);
		out.flush();
		out.close();
	}
	
	public void getProductControlByBomOid() throws ServletException, IOException {
		ListResult<ProductControlMainDto> result = new ListResult<ProductControlMainDto>();
		result.setHasError("true");
		result.setError(Constants.ERROR_CODE_SUCCESS);
		
		String resutlStr = JsonUtils.filter(JsonUtils.toJSONString(result));
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
	}
	
	/**
	 * 获取周计划
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getWeeklyPlan() throws ServletException, IOException {
		String yearS = request.getParameter("year");
		String monthS = request.getParameter("month");
		String weekS = request.getParameter("week");
		
		if (StringHelper.isEmpty(yearS) || StringHelper.isEmpty(monthS)
				|| StringHelper.isEmpty(weekS)) {
			PageDispatcher.forward(request, response, "page/week_plan.html");
			return;
		}
		
		int year = FilterUtil.filterString2Int(yearS);
		int month = FilterUtil.filterString2Int(monthS);
		int week = FilterUtil.filterString2Int(weekS);
		JSONObject pdJsonObj = productControlService.getNewlyWeekPlanTable(year, month, week);
		
		PrintWriter out = response.getWriter();
		out.print(pdJsonObj);
		out.flush();
		out.close();
	}
	
	/**
	 * 导出周计划Excel表
	 * @throws IOException 
	 */
	public void exportWeeklyPlanExcel() {
		int year = FilterUtil.filterString2Int(request.getParameter("year"));
		int month = FilterUtil.filterString2Int(request.getParameter("month"));
		int week = FilterUtil.filterString2Int(request.getParameter("week"));
		
		File file = generateProConExcelFile(year, month, week);
		if(!file.exists()){
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("要下载的文件不存在！");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		String fileName = file.getName();
		try (
			//读取要下载的文件，保存到文件输入流
			FileInputStream in = new FileInputStream(file);
			//创建输出流
			OutputStream out = response.getOutputStream();
		) {
			//设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len = in.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			file.delete(); // 文件传输完后删除在服务器的临时文件
		}
		
	}
	
	/**
	 * 生成生产控制单Excel文件
	 * 
	 * @param year
	 * @param month
	 * @param week
	 * @return
	 */
	private File generateProConExcelFile(int year, int month, int week) {
		String exportExcelFileName = year + "年" + month + "月第" + week +  "周计划.xls";
		File excelFile = new File(exportExcelFileName);
		
		List<Object> list = productControlService.getWeekPlanTable(year, month, week);
		String[] cellTitle = {"序号", "日期", "机台", "部番", "周期", "穴数", "计划生产量",
				"实际生产量", "加工良品", "不良数", "需加工人数", "达成率"};
		Map<Integer, String> tableMap = new HashMap<>();
		tableMap.put(1, "produceDate"); // 生产日期
		tableMap.put(2, "name");		// 机台
		tableMap.put(3, "prodId"); 		// 部番
		tableMap.put(4, "period");		// 周期
		tableMap.put(5, "caveNum");		// 穴数
		tableMap.put(6, "planProdAmt");	// 计划生产量
		tableMap.put(7, "actProdAmt");	// 实际生产量
		tableMap.put(8, "goodProdAmt");	// 加工良品
		tableMap.put(9, "badProdAmt");	// 不良数
		tableMap.put(10, "");			// 需加工人数
		tableMap.put(11, "effRate");	// 达成率
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("周计划"); // 单元名
		Font titleFont = wb.createFont(); // 标题字体样式
		titleFont.setColor(HSSFColor.DARK_BLUE.index);
		titleFont.setFontHeightInPoints((short) 11);
		titleFont.setFontName("宋体");
		titleFont.setBoldweight((short) 800);
		
		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中
		titleStyle.setFont(titleFont);
		
		Font bodyFont = wb.createFont();
		bodyFont.setFontHeightInPoints((short) 9);
		bodyFont.setFontName("新宋体");
		
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER); // 水平居中
		bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
		bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
		
		// sheet标题
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cellTitle.length-1));
		Row r0 = sheet.createRow(0);
		r0.setHeight((short) 0x170);
		Cell c1 = r0.createCell(0);
		c1.setCellValue(year + "年" + month + "月第" + week +  "周计划");
		c1.setCellStyle(titleStyle);
		
		// 列表行标题
		Row r1 = sheet.createRow(1);
		r1.setHeight((short) 0x150);
		for (int i = 0; i < cellTitle.length; i++) {
			Cell cell = r1.createCell(i);
			cell.setCellValue(cellTitle[i]);
			cell.setCellStyle(bodyStyle);
		}
		
		// 主体列表数据
		for (int i = 2; i < list.size() + 2; i++) {
			Row r2 = sheet.createRow(i);
			r2.setHeight((short) 0x170);
			Map<String, Object> map = (Map<String, Object>) list.get(i-2);
			for (int j = 0; j < cellTitle.length; j++) {
				// 在sheet第0行添加表头
				Cell cell = r2.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(i-2+1);
				} else {
					Object temp = map.get(tableMap.get(j));
					if (temp instanceof String) {
						cell.setCellValue(temp.toString());
					} else if (temp instanceof Double) {
						cell.setCellValue(Double.valueOf(temp.toString()));
					}
				}
			}
		}
		
		try (
			FileOutputStream out = new FileOutputStream(excelFile);
		) {
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return excelFile;
	}
	

}
