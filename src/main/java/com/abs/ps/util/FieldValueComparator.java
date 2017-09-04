package com.abs.ps.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.abs.ps.domain.BomMain;
/**
 * Comparing two object's value to identify which field value be changed, which use to mark down the operation in action log. 
 * @author Wilson Huang
 * @since June.16,2017
 */
public final class FieldValueComparator {
	private FieldValueComparator(){}
	
	/**
	 * 
	 * @param sourceObj 
	 * @param targetObj
	 * @return
	 */
	public static Map<String,String> compareObject(Object sourceObj, Object targetObj) {
		Map<String,String> map = new HashMap<String,String>();

		String sourceCalssName = sourceObj.getClass().getSimpleName();
		String targetCalssName = targetObj.getClass().getSimpleName();
		
		if (!sourceCalssName.equals(targetCalssName)) {
			return null;
		}
		try {
			Field[] fields = sourceObj.getClass().getDeclaredFields();
			for (Field field : fields) {			
				String fieldName = field.getName();
				Object srcValue = getFiledValue(field, sourceObj);
				Object trgValue = getFiledValue(field, targetObj);
				
				valueCompare(fieldName, srcValue, trgValue, map);
			
			}
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	
	private static boolean valueCompare(String fieldName, Object srcVal, Object trgVal, Map<String,String> map) {
		if (srcVal == null && trgVal == null) {
			return true;
		}
		String sourceValue = "";
		String targetValue = "";
		if (srcVal instanceof String) {
			if (srcVal != null && srcVal.equals(trgVal)) {
				return true;
			}
			sourceValue = (String) srcVal;
			targetValue = (String) trgVal;
		} else if (srcVal instanceof Integer) {
			Integer srcValInt = new Integer("0");
			if (srcVal != null) {
				srcValInt = (Integer) srcVal;
				sourceValue = String.valueOf(srcValInt);
			}
			Integer trgValInt = new Integer("0");
			if (trgVal != null) {
				trgValInt = (Integer) trgVal;
				targetValue = String.valueOf(trgValInt);
			}

			if (srcVal != null && trgValInt != null && srcValInt.intValue() == trgValInt.intValue()) {
				return true;
			}

		} else if (srcVal instanceof Double) {	
			Double srcValObj = new Double("0");
			if (srcVal !=null) {
				srcValObj = (Double) srcVal;
				sourceValue = String.valueOf(srcValObj);
			}
			Double trgValObj = new Double("0");
			if (trgVal != null) {
				trgValObj = (Double) trgVal;
				targetValue = String.valueOf(trgValObj);
			}

			if (srcValObj.doubleValue() == trgValObj.doubleValue()) {
				return true;
			}
		} else if (srcVal instanceof Boolean) {	
			Boolean srcValObj = new Boolean(false);
			if (srcVal != null) {
				srcValObj = (Boolean) srcVal;
			}
			Boolean trgValObj = new Boolean(false);
			if (trgVal != null) {
				trgValObj = (Boolean) trgVal;
			}

			if (srcValObj.booleanValue() == trgValObj.booleanValue()) {
				return true;
			}
		} else if (srcVal instanceof Date) {	
			Date srcValObj = new Date();
			if (srcVal != null) {
				srcValObj = (Date) srcVal;
			}
			Date trgValObj = new Date();
			if (trgVal != null) {
				trgValObj = (Date) trgVal;
			}
			if (DateHelper.compareDate(srcValObj, trgValObj, DateHelper.DATE_FORMATE) == 0) {
				return true;
			}
			
			sourceValue = DateHelper.convert2String(srcValObj, DateHelper.DATE_FORMATE);
			targetValue  = DateHelper.convert2String(trgValObj, DateHelper.DATE_FORMATE);
		} 
		map.put(fieldName, sourceValue + "=" + targetValue);
		return false;
	}
	
	private static Object getFiledValue(Field field, Object obj) throws Exception {
		if (field != null) {
			field.setAccessible(true);
			String fieldName = field.getName();
			String fieldType = field.getGenericType().toString();

			fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
			
			try {
				Method method = obj.getClass().getMethod("get" + fieldName);
				
				if (fieldType.equals("class java.lang.String")) {				 
					 return (String) method.invoke(obj);
				} else if (fieldType.equals("class java.lang.Integer")) {
					return (Integer) method.invoke(obj);
				} else if (fieldType.equals("class java.lang.Double")) {
					return (Double) method.invoke(obj);
				} else if (fieldType.equals("class java.lang.Boolean")) {
					return (Boolean) method.invoke(obj);
				} else if (fieldType.equals("class java.util.Date")) {
					return (Date) method.invoke(obj);
				}
			} catch(Exception ex) {
//				ex.printStackTrace();
			}
			
			
		}
		return null;
	}
	
	
	public static String[] split(Map<String,String> map){
		
		String fromValue = "";
		String toValue = ""; 
		String[] values = new String[2];
		for(String key :map.keySet()){
			int a = map.get(key).indexOf("=");
			fromValue +=key+"["+map.get(key).substring(a+1)+"]"+"\n";
			toValue+=key+"["+map.get(key).substring(0,a)+"]"+"\n";
			values[0]=fromValue;
			values[1]=toValue;
		}
		return values;
		
	}
	
	public static void main(String[] argu) {
//		NameCodeDto dto1 = new NameCodeDto();
//		dto1.setCode("111");
//		dto1.setName("Wilson");
//		NameCodeDto dto2 = new NameCodeDto();
//		dto2.setCode("222");
//		dto2.setName("huangwi2");
		
		BomMain bom1 = new BomMain();
		bom1.setCreateDate(DateHelper.convert2Date("2017-05-09", DateHelper.DATE_FORMATE));
		BomMain bom2 = new BomMain();
		bom2.setCreateDate(DateHelper.convert2Date("2017-06-09", DateHelper.DATE_FORMATE));
		Map<String,String> map = FieldValueComparator.compareObject(bom1, bom2);
		System.out.println("map = " + map);
		
		
	}
}
