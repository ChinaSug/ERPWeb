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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.ps.domain.Department;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.ItemType;
import com.abs.ps.domain.Machine;
import com.abs.ps.domain.Mould;
import com.abs.ps.domain.StockType;
import com.abs.ps.domain.Warehouse;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.QueueConstants;
import com.abs.ps.web.dto.BomDetailDto;
import com.abs.ps.web.dto.UserDto;
import com.abs.ps.web.dto.json.OptionDto;

public class BaseInfoHelper {
	private Logger logger=Logger.getLogger(SupplierHelper.class.getName()); 
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	private UserDto sessionUserDto = null;
	private BaseInfoService baseInfoService;
	
	public BaseInfoHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
		HttpSession session = request.getSession();
		sessionUserDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.baseInfoService = (BaseInfoService) factory.getBeanByName("baseInfoService");
	}
	
	public void getItemTypes() throws ServletException, IOException{
		String typeName = request.getParameter("typeName");
		List<ItemType> itemTypes = baseInfoService.findItemType(typeName);
		List<OptionDto> optionDtos = new ArrayList<OptionDto>();
		for (ItemType itemType : itemTypes) {
			OptionDto dto = new OptionDto();
			dto.setValue(itemType.getOid().toString());
			dto.setText(itemType.getName());
			optionDtos.add(dto);
		}
		
		String resutlStr = JsonUtils.toJSONString(optionDtos);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
		
	}
	
	public void getDepartments() throws ServletException, IOException{
		String name = request.getParameter("name");
		List<Department> list = baseInfoService.findDepartments(name);
		List<OptionDto> optionDtos = new ArrayList<OptionDto>();
		for (Department department : list) {
			OptionDto dto = new OptionDto();
			dto.setValue(department.getOid().toString());
			dto.setText(department.getDepartName());
			optionDtos.add(dto);
		}
		
		String resutlStr = JsonUtils.toJSONString(optionDtos);
		PrintWriter out = response.getWriter();
		out.print(resutlStr);
		
		out.flush();
		out.close();
		
	}
	
	public void getMachines() throws ServletException, IOException{
		String name = request.getParameter("name");
		List<Machine> lists = baseInfoService.findMachines(name);
		List<OptionDto> optionDtos = new ArrayList<OptionDto>();
		for (Machine obj : lists) {
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
	
	public void getMoulds() throws ServletException, IOException{
		String name = request.getParameter("name");
		List<Mould> lists = baseInfoService.findMoulds(name);
		List<OptionDto> optionDtos = new ArrayList<OptionDto>();
		for (Mould obj : lists) {
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
	 * 获取库存类型下拉选择框
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getStockTypes() throws ServletException, IOException{
		String name = request.getParameter("name");
		String dimension = request.getParameter("dim");
		List<StockType> lists = baseInfoService.findStockTypes(dimension, name);
		List<OptionDto> optionDtos = new ArrayList<OptionDto>();
		for (StockType obj : lists) {
			if ("CP_PRO".equals(obj.getCode()) || "CP_LOS".equals(obj.getCode())) {
				continue;
			}
			
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
	 * 获取仓库下拉选择框
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getWarehouses() throws ServletException, IOException{
		String name = request.getParameter("name");
		List<Warehouse> lists = baseInfoService.findWarehouses(name);
		List<OptionDto> optionDtos = new ArrayList<OptionDto>();
		for (Warehouse obj : lists) {
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
	
	// 获取BOM详细表没有对应关联生产控制单的BOM编号
	public void getBomNumNotProduct() throws IOException {
		List<BomDetailDto> bomDetailList = baseInfoService.getBomDetailByNoProduct();
		JSONArray jsonArr = new JSONArray();
		for (BomDetailDto dto : bomDetailList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("bomNum", dto.getBomNum());
			jsonObj.put("prodId", dto.getProdId());
			jsonArr.add(jsonObj);
		}
		
		PrintWriter out = response.getWriter();
		out.print(jsonArr.toString());
		
		out.flush();
		out.close();
	}
	
	/**
	 * 获取所有有效的BOM子表，用于下拉选择框
	 * 
	 * @throws IOException
	 */
	public void getAllValidBomDetail() throws IOException {
		List<BomDetailDto> bomDetailList = baseInfoService.getAllBomDetail();
		JSONArray jsonArr = new JSONArray();
		Map<Long, ItemInfo> itemMap = new HashMap<>();
		
		for (BomDetailDto dto : bomDetailList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("bomNum", dto.getBomNum());
			jsonObj.put("prodId", dto.getProdId());
			jsonObj.put("bomDetailOid", dto.getOid());
			jsonObj.put("bomName", dto.getName());
			
			long itemOid = FilterUtil.filterString2Long(dto.getItemOid());
			long inkOid = FilterUtil.filterString2Long(dto.getPinkOid());
			long alloyOid = FilterUtil.filterString2Long(dto.getAlloyOid());
			
			if (itemOid > 0) {
				ItemInfo item = itemMap.get(itemOid);
				if (item == null) {
					item = (ItemInfo) baseInfoService.getEntityByOid(ItemInfo.class, itemOid);
				}
				
				if (item != null) {
					itemMap.put(item.getOid(), item);
					jsonObj.put("itemOid", item.getOid());
					jsonObj.put("itemName", item.getName());
					jsonObj.put("itemCode", item.getCode());
				}
			}
			
			if (inkOid > 0) {
				ItemInfo inkItem = itemMap.get(inkOid);
				if (inkItem == null) {
					inkItem = (ItemInfo) baseInfoService.getEntityByOid(ItemInfo.class, inkOid);
				}
				
				if (inkItem != null) {
					itemMap.put(inkItem.getOid(), inkItem);
					jsonObj.put("inkOid", inkItem.getOid());
					jsonObj.put("inkName", inkItem.getName());
					jsonObj.put("inkCode", inkItem.getCode());
				}
			}
			
			if (alloyOid > 0) {
				ItemInfo alloyItem = itemMap.get(alloyOid);
				if (alloyItem == null) {
					alloyItem = (ItemInfo) baseInfoService.getEntityByOid(ItemInfo.class, alloyOid);
				}
				
				if (alloyItem != null) {
					itemMap.put(alloyItem.getOid(), alloyItem);
					jsonObj.put("alloyOid", alloyItem.getOid());
					jsonObj.put("alloyName", alloyItem.getName());
					jsonObj.put("alloyCode", alloyItem.getCode());
				}
			}
			
			if (sessionUserDto.isAdmin()) {
				jsonObj.put("itemUnitPrice", dto.getUnitPrice());
				jsonObj.put("matPrice", dto.getMaterialUpc());
				jsonObj.put("piePrice", dto.getPieUpc());
				jsonObj.put("damagePrice", dto.getDamageUpc());
			}
			jsonArr.add(jsonObj);
		}
		
		PrintWriter out = response.getWriter();
		out.print(jsonArr.toString());
		
		out.flush();
		out.close();
	}
	
	/**
	 * 获取库存类型为产品时的产品下拉选择框
	 * @throws IOException
	 */
	public void getStockBomDetailSG() throws IOException {
		List<BomDetailDto> bomDetailList = baseInfoService.getAllBomDetail();
		JSONArray jsonArr = new JSONArray();
		
		for (BomDetailDto dto : bomDetailList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("itemCode", dto.getProdId());
			jsonObj.put("text", dto.getName());
			jsonObj.put("unitPrice", dto.getUnitPrice());
			jsonObj.put("bomDetailOid", dto.getOid());
			jsonArr.add(jsonObj);
		}
		
		PrintWriter out = response.getWriter();
		out.print(jsonArr.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 获取当前用户信息
	 * @throws IOException
	 */
	public void getCurUser() throws IOException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("userId", sessionUserDto.getUserId());
		jsonObj.put("userName", sessionUserDto.getUserName());
		jsonObj.put("isAdmin", sessionUserDto.isAdmin());
		
		PrintWriter out = response.getWriter();
		out.print(jsonObj.toString());
		out.flush();
		out.close();
	}
	
	
	
}
