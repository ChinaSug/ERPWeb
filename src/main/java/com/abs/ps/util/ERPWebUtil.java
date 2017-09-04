package com.abs.ps.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.abs.ps.web.dto.UserDto;

/**
 * ERPWeb项目工具类
 * @author 苏建生 2017-8-9
 */
public class ERPWebUtil {

	private ERPWebUtil() {}
	
	public static String HTML_VERSION = "_v=2017811114754";
	
	/**
	 * 设置分页Url参数
	 * 
	 * @param valueMap
	 * @param excludeStr 要忽略的字段
	 * @return
	 */
	public static String setPagingUrlParam(Map<String, String> valueMap, String... excludeStr) {
		StringBuffer sb = new StringBuffer();
		if (valueMap != null) {
			List<String> exList = null;
			if (excludeStr != null && excludeStr.length > 0) {
				exList = Arrays.asList(excludeStr);
			}
			
			for (Map.Entry<String, String> entry : valueMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				
				if ("op_action".equals(key) || "pageNumber".equals(key)) {
					continue;
				}
				if (StringHelper.isEmpty(value)) {
					continue;
				}
				if (exList != null && exList.contains(key)) {
					continue;
				}
				
				sb.append("&" + key + "=" + value);
			}
		}
		return sb.toString();
	}
	
}
