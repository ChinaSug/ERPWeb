package com.abs.ps.web.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.abs.core.util.AbsBeanFactory;
import com.abs.core.util.JsonUtils;
import com.abs.core.util.StringHelper;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.service.StockInfoService;
import com.abs.ps.util.FilterUtil;

/**
 * 用于验证数据的Helper
 * @author 苏建生 2017-8-2
 */
public class CheckHelper {
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private ServletContext context = null;
	
	private BaseInfoService baseInfoService = null;
	private StockInfoService stockInfoService = null;
	
	public CheckHelper(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		this.request = request;
		this.response = response;
		this.context = context;
		
		AbsBeanFactory factory = AbsBeanFactory.getBeanFactory(this.context);
		this.baseInfoService = (BaseInfoService) factory.getBeanByName("baseInfoService");
		this.stockInfoService = (StockInfoService) factory.getBeanByName("stockInfoService");
		
	}
	
	/**
	 * 检查数据是否存在，有则返回true，无则false
	 */
	public void doCheck() {
		String className = request.getParameter("class");
		String field = request.getParameter("field");
		String value = request.getParameter("value"); 
		// 客户服务器需要转码 
		try {
			value = StringHelper.getNewString(value);
		} catch (UnsupportedEncodingException e1) {
		}
		long oid = FilterUtil.filterString2Long(request.getParameter("oid")); // 用于判断对象是否在编辑就数据
		
		PrintWriter out = null;
		JSONObject jsonObject = new JSONObject();
		try {
			out = response.getWriter();
			className = "com.abs.ps.domain." + className;
			if (checkDataIsExist(className, field, value, oid)) {
				jsonObject.put("success", true);
			} else {
				JSONObject errorJson = new JSONObject();
				errorJson.put(field, "数据已存在，请重新输入");
				jsonObject.put("success", false);
				jsonObject.put("errors", errorJson);
			}
			out.print(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JSONObject errorJson = new JSONObject();
			errorJson.put(field, "数据异常，检查失败！");
			jsonObject.put("success", false);
			jsonObject.put("errors", errorJson);
			out.print(jsonObject.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 
	 * @param fullClassName 带路径类名
	 * @param field	字段名
	 * @param value	字段字符串值
	 * @param oid 判断对象是否已存在，存在则判断字段名是否就是当前值
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public boolean checkDataIsExist(String fullClassName, String field, String value, long oid) throws ClassNotFoundException {
		Class<?> clazz;
		clazz = Class.forName(fullClassName);
		Object newValue = FilterUtil.convertFieldValue(clazz, field, value);
		String[] fieldArr = new String[]{field};
		Object[] valueArr = new Object[]{newValue};
		Object oldObj = null;
		Object oldValue = null;
		
		// 若是编辑数据，则判断value是否与旧值相等，若相等则表示返回true
		if (oid > 0) {
			oldObj = baseInfoService.getObject(clazz, new String[]{"oid"}, new Object[]{oid});
			oldValue = FilterUtil.getFieldValue(oldObj, field);
		}
		
		Object object = baseInfoService.getObject(clazz, fieldArr, valueArr);
		if (object == null || newValue.equals(oldValue)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查仓库是否在待盘点状态，
	 * 待盘点仓库不允许进行编辑和添加操作
	 * @throws IOException 
	 */
	public void checkIsCheckStockWarehourse() throws IOException {
		String warehouseOid = request.getParameter("warehouseOid");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		if (!stockInfoService.isCheckStockWarehouse(warehouseOid)) {
			jsonObject.put("success", true);
		} else {
			jsonObject.put("success", false);
			JSONObject errorJson = new JSONObject();
			errorJson.put("warehouse", "该仓库盘点中，请重选");
			jsonObject.put("errors", errorJson);
		}
		out.print(jsonObject);
		out.flush();
		out.close();
	}
	
}
