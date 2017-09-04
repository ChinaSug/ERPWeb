package com.abs.ps.util;

import static java.util.Locale.ENGLISH;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * @author 苏建生 2017-8-21
 */
public final class FilterUtil {

	public static final String INT_REGEX = "^[+-]?[0-9]+$";
	public static final String LONG_REGEX = "^[+-]?[0-9]+$";
	public static final String DOUBLE_REGEX = "^[+-]?[0-9]+(\\.[0-9]*)?$";
	public static final String SCIENTIFIC_NOTATION = "^[+-]?\\d+(\\.\\d+)?(E-?\\d+)?$";
	public static final String DATE_REGEX = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
	public static final String DATETIME_REGEX = "^\\d{4}-\\d{1,2}-\\d{1,2}\\s*\\d{2}(\\:(\\d){2}){2}(\\.0)*$";
	// 匹配带千位分隔符“,”的数字
	public static final String FINANCE_NUM_REGEX = "^-?[1-9]\\d*(,\\d{3})*(\\.\\d+)?$";
	public static final String OFFICE_EXCEL_2003_POSTFIX = ".xls";
	public static final String OFFICE_EXCEL_2007_POSTFIX = ".xlsx";
	
	private static final Pattern P_INT_REGEX = Pattern.compile(INT_REGEX);
	private static final Pattern P_LONG_REGEX = Pattern.compile(LONG_REGEX);
	private static final Pattern P_DOUBLE_REGEX = Pattern.compile(DOUBLE_REGEX);
	private static final Pattern P_SCIENTIFIC_NOTATION = Pattern.compile(SCIENTIFIC_NOTATION);
	private static final Pattern P_DATE_REGEX = Pattern.compile(DATE_REGEX);
	private static final Pattern P_DATETIME_REGEX = Pattern.compile(DATETIME_REGEX);
	private static final Pattern P_FINANCE_NUM_REGEX = Pattern.compile(FINANCE_NUM_REGEX);

	private FilterUtil() {
	};

	/**
	 * 过滤不符合boolean条件的字符，若字符等于1或true或Y时返回真
	 * 
	 * @param s
	 * @return
	 */
	public static boolean filterString2Boolean(String s) {
		if (!isEmpty(s)) {
			return "1".equals(s) || "true".equals(s) || "Y".equals(s);
		}
		return false;
	}

	/**
	 * 过滤不符合double条件的字符，默认值返回0
	 * 
	 * @param s
	 * @return
	 */
	public static double filterString2Double(String s) {
		if (s != null) {
			Matcher isNum = P_DOUBLE_REGEX.matcher(s);
			if (isNum.matches()) {
				return Double.valueOf(s);
			}
			isNum = P_SCIENTIFIC_NOTATION.matcher(s);
			if (isNum.matches()) {
				BigDecimal bd = new BigDecimal(s);
				return Double.valueOf(bd.toPlainString());
			}
			isNum = P_FINANCE_NUM_REGEX.matcher(s);
			if (isNum.matches()) {
				double d = filterFinanceString(s);
				return d;
			}
		}
		return 0;
	}

	/**
	 * 过滤不符合int条件的字符，默认值返回0
	 * 
	 * @param s
	 * @return
	 */
	public static int filterString2Int(String s) {
		s = filterNullStr(s);
		Matcher isNum = P_INT_REGEX.matcher(s);
		if (isNum.matches()) {
			return Integer.valueOf(s);
		}
		return 0;
	}

	/**
	 * 过滤不符合long条件的字符，默认值返回0
	 * 
	 * @param s
	 * @return
	 */
	public static long filterString2Long(String s) {
		s = filterNullStr(s);
		Matcher isNum = P_LONG_REGEX.matcher(s);
		if (isNum.matches()) {
			return Long.valueOf(s);
		}
		return 0;
	}
	
	/**
	 * 过滤带千位分隔符“,”的数字，若不匹配则返回0
	 * 
	 * @param s
	 * @return
	 */
	public static double filterFinanceString(String s) {
		s = filterNullStr(s);
		Matcher m = P_FINANCE_NUM_REGEX.matcher(s);
		if (m.matches()) {
			s = s.replaceAll(",", "");
			return Double.parseDouble(s);
		}
		return 0;
	}

	public static String filterNullStr(String str) {
		if (isEmpty(str)) {
			return "";
		}
		return str;
	}

	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return false;
		}
		return true;
	}

	public static double filterObj2Double(Object obj) {
		if (obj != null) {
			return filterString2Double(obj.toString());
		}
		return 0;
	}

	/**
	 * 从SelecteContext中获取abs.img.max属性 设置图片上传最大限制 默认为10M
	 * 
	 * @param con
	 * @return
	 */
	public static int IMG_MAX_SIZE = 0;
	public static long getImgMaxSize(ServletContext con) {
		if (con != null && IMG_MAX_SIZE == 0) {
			Integer maxSize = (Integer) con.getAttribute("abs.img.max");
			if (maxSize != null && maxSize > 0) {
				IMG_MAX_SIZE = maxSize * 1024 * 1024;
				return IMG_MAX_SIZE;
			}
		}
		return IMG_MAX_SIZE;
	}

	public static byte[] readInputStream(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public static String getFileSufix(String name) {
		if (!StringHelper.isEmpty(name) && name.length() > 0) {
			return name.substring(name.lastIndexOf(".")).toLowerCase();
		}
		return "";
	}

	public static boolean isPhotoSufix(String fileName) {
		String sufix = ".jpg/.jpeg/.png/.bmp";
		String fileSufix = getFileSufix(fileName).toLowerCase();
		if (!StringHelper.isEmpty(fileSufix)  
				&& sufix.contains(fileSufix)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 将原图片压缩以JPG格式保存，并覆盖原图
	 * 
	 * @param image
	 * @return
	 */
	public static String compressImage(File image) {
		return compressImage(image, true);
	}
	
	/**
	 * 将原图片压缩以JPG格式保存
	 * 
	 * @param image
	 * @param isCover 是否覆盖原图
	 * @return 返回保存路径相对地址
	 */
	public static String compressImage(File image, boolean isCover) {
		String savePath = "";
		if (isPhotoSufix(image.getAbsolutePath())) {
			String imagePath = image.getAbsolutePath();
			try {
				if (isCover) {
					savePath = imagePath.substring(0, imagePath.lastIndexOf(".")) + ".jpg";
				} else {
					savePath = imagePath.substring(0, imagePath.lastIndexOf(".")) + "-" + (new Date()).getTime() + ".jpg";
				}
				
				if (imagePath.endsWith(".jpg")) {
					Thumbnails.of(image).scale(1f).outputQuality(0.25f).toFile(savePath);
				} else {
					Thumbnails.of(image).scale(1f).outputQuality(0.25f).outputFormat("jpg").toFile(savePath);
					if (image.isFile()) {
						if (!image.delete()) {
							throw new IOException("图片删除失败，在删除图片前需要先关闭关联文件的IO流");
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			int beginIndex = 0;
			if (savePath.lastIndexOf("/IMG") > 0) {
				beginIndex = savePath.lastIndexOf("/IMG") + 1;
			} else if (savePath.lastIndexOf("\\IMG") > 0) {
				beginIndex = savePath.lastIndexOf("\\IMG") + 1;
			}
			savePath = savePath.substring(beginIndex, savePath.length());
		}
		return savePath;
	}

	public static String convertDouble2String(String dou) {
		if (dou != null) {
			Matcher isNum = P_DOUBLE_REGEX.matcher(dou.toString());
			if (isNum.matches()) {
				return dou.toString();
			}
			isNum = P_SCIENTIFIC_NOTATION.matcher(dou.toString());
			if (isNum.matches()) {
				BigDecimal bd = new BigDecimal(dou.toString());
				return bd.toPlainString();
			}
		}
		return "";
	}
	
	/**
	 * 将对象转换为指定Class对象，并给相同属性字段赋值，字段要有对应setter和getter方法的才能赋值<br>
	 * 属性为Date或String间相互转换时，转换的字符格式遵循yyyy-MM-dd HH:mm:ss<br>
	 * int可以向上转long，但long向下转int会失败<br>
	 * 若字段名相同、字段类型却不同，则不会转换
	 * 
	 * @param obj 要转换的对象
	 * @param clazz 要转换成的类型对象
	 * @return
	 */
	public static <V> V convertObjectClass(Object obj, Class<V> clazz) {
		if (clazz == null || obj == null) {
			return null;
		}
		
		V newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		Field[] fields1 = obj.getClass().getDeclaredFields();
		Field[] fields2 = clazz.getDeclaredFields();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		if (null != fields1 && null != fields2) {
			for (Field field1 : fields1) {
				try {
					// 对象中有字段对应的setter和getter才进行处理
					PropertyDescriptor pd = initPropertyDescriptor(field1, obj.getClass());
					if (pd == null) {
						continue;
					}
					
					Method getMethod = pd.getReadMethod();// 获得get方法
					Object value = getMethod.invoke(obj);// 执行get方法返回一个Object
					if (value == null) {
						continue;
					}
					
					// 判断clazz的类中是否有和obj一样字段名的属性，并以字段名为键，value为值存储
					for (Field field2 : fields2) {
						String name1 = field1.getName();
						String name2 = field2.getName();
						if (name1.equals(name2)) {
							valueMap.put(name1, value);
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			setObjectField(newInstance, valueMap);
		}
		return newInstance;
	}
	
	/**
	 * 初始化PropertyDescriptor类，通过字段的setter和getter方法进行初始化
	 * @param field
	 * 
	 * @param clazz
	 * @return
	 * @throws IntrospectionException 
	 */
	public static PropertyDescriptor initPropertyDescriptor(Field field, Class<?> clazz) {
		if (field == null || clazz == null) {
			return null;
		}
		
		PropertyDescriptor pd = null;
		String fieldName = field.getName();
		Method[] methods = clazz.getMethods();
		
		if (field.getType() == Boolean.class || field.getType() == boolean.class) {
			// 字段类型为Boolean时的几种setter和getter方法问题的处理
			String get = "get";
			String set = "set";
			String is = "is";
			
			String isGetMethod = null;
			String isSetMethod = null;
			if (fieldName.startsWith(is)) { // 字段 为isAdmin,  getter: isAdmin;   setter: setAdmin
				isGetMethod = fieldName;
				isSetMethod = fieldName.replaceAll("^is", set);
			}
			String getMethod = get + capitalize(fieldName); // 字段 为isAdmin,  getter: getIsAdmin;  setter: setIsAdmin
			String setMethod = set + capitalize(fieldName);
			
			String readMethodName = null;
			String writeMethodName = null;
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.equals(isGetMethod)) {
					readMethodName = isGetMethod;
				} else if (methodName.equals(isSetMethod)) {
					writeMethodName = isSetMethod;
				} else if (methodName.equals(getMethod)) {
					readMethodName = getMethod;
				} else if (methodName.equals(setMethod)) {
					writeMethodName = setMethod;
				}
			}
			
			if (readMethodName != null && writeMethodName != null) {
				try {
					pd = new PropertyDescriptor(fieldName, clazz, readMethodName, writeMethodName);
				} catch (IntrospectionException e) {
					// 没有找到对应的setter或getter方法
					// e.printStackTrace();
				}
			}
			
		} else {
			try {
				pd = new PropertyDescriptor(fieldName, clazz);
			} catch (IntrospectionException e) {
				// 没有找到对应的setter或getter方法
				// e.printStackTrace();
			}
		}
			
		return pd;
	}
	
	/**
	 * 设置对象instance的字段值，若没有找到对应的getter和setter方法将不会赋值
	 * 
	 * @param instance 设置的对象
	 * @param valueMap 以字段名为key，要存的值为value
	 */
	public static void setObjectField(Object instance, Map<String, Object> valueMap) {
		Class<?> clazz = instance.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field fd : fields) {
			String fieldName = fd.getName();
			Object value = valueMap.get(fieldName); // 
			if (value != null) {
				PropertyDescriptor pd;
				try {
//					pd = new PropertyDescriptor(fieldName, clazz);
					pd = initPropertyDescriptor(fd, clazz);
					if (pd == null) {
						continue;
					}
					
					Type type = fd.getType(); // 要赋值的字段类型
					String valueStr = value.toString();
					
					//System.out.println(fieldName + " | " + type + " | " + value + " | " + value.getClass());
					if (type == String.class) {
						if (value.getClass() == Date.class) {
							value = DateHelper.convert2String((Date) value, DateHelper.DATETIME_FORMATE);
						} else if (value.getClass() == Timestamp.class) {
							if (isDatetimeFormat(valueStr)) {
								if (valueStr.endsWith(".0")) {
									value = valueStr.substring(0, valueStr.lastIndexOf(".0"));
								}
							} else {
								value = valueStr;
							}
						} else {
							value = valueStr;
						}
					} else if (type == Boolean.class || type == boolean.class) {
						value = filterString2Boolean(valueStr);
					} else if (type == Integer.class || type == int.class) {
						value = filterString2Int(valueStr);
					} else if (type == Long.class || type == long.class) {
						value = filterString2Long(valueStr);
					} else if (type == Double.class || type == double.class) {
						value = filterObj2Double(valueStr);
					} else if (type == Date.class) {
						if (value instanceof String) {
							if (isDateFormat(valueStr)) {
								value = DateHelper.convert2Date(valueStr, DateHelper.DATE_FORMATE);
							} else if (isDatetimeFormat(valueStr)) {
								if (valueStr.endsWith(".0")) {
									valueStr = valueStr.substring(0, valueStr.lastIndexOf(".0"));
								}
								value = DateHelper.convert2Date(valueStr, DateHelper.DATETIME_FORMATE);
							}
						}
					} else if (type == Timestamp.class) {
						// String的类型默认为： yyyy-mm-dd hh:mm:ss
						if (value instanceof String) {
							if (isDateFormat(valueStr)) {
								value = Timestamp.valueOf(valueStr);
							} else if (isDatetimeFormat(valueStr)) {
								if (valueStr.endsWith(".0")) {
									valueStr = valueStr.substring(0, valueStr.lastIndexOf(".0"));
								}
								value = Timestamp.valueOf(valueStr);
							}
						}
					}
					
					Method writeMethod = pd.getWriteMethod();
					writeMethod.invoke(instance, value); // 调用对象的set方法将value赋值给字段
				} catch (IllegalArgumentException e) {
					// argument type mismatch
					// e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 返回clazz的实例，并将valueMap中key值与clazz实例字段相同的，通过setter方法赋值
	 * 
	 * @param clazz
	 * @param valueMap
	 * @return
	 */
	public static <V> V getInstance(Map<String, Object> valueMap, Class<V> clazz) {
		V instance = null;
		try {
			instance = clazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field fd : fields) {
			String fieldName = fd.getName();
			Object value = valueMap.get(fieldName); // 
			if (value != null) {
				PropertyDescriptor pd;
				try {
					pd = initPropertyDescriptor(fd, clazz);
					if (pd == null) {
						continue;
					}
					
					Type type = fd.getType(); // 要赋值的字段类型
					String valueStr = value.toString();
					
					if (type == String.class) {
						if (value.getClass() == Date.class) {
							value = DateHelper.convert2String((Date) value, DateHelper.DATETIME_FORMATE);
						} else if (value.getClass() == Timestamp.class) {
							if (isDatetimeFormat(valueStr)) {
								if (valueStr.endsWith(".0")) {
									value = valueStr.substring(0, valueStr.lastIndexOf(".0"));
								}
							} else {
								value = valueStr;
							}
						} else {
							value = valueStr;
						}
					} else if (type == Boolean.class || type == boolean.class) {
						value = filterString2Boolean(valueStr);
					} else if (type == Integer.class || type == int.class) {
						value = filterString2Int(valueStr);
					} else if (type == Long.class || type == long.class) {
						value = filterString2Long(valueStr);
					} else if (type == Double.class || type == double.class) {
						value = filterObj2Double(valueStr);
					} else if (type == Date.class) {
						if (value instanceof String) {
							if (isDateFormat(valueStr)) {
								value = DateHelper.convert2Date(valueStr, DateHelper.DATE_FORMATE);
							} else if (isDatetimeFormat(valueStr)) {
								if (valueStr.endsWith(".0")) {
									valueStr = valueStr.substring(0, valueStr.lastIndexOf(".0"));
								}
								value = DateHelper.convert2Date(valueStr, DateHelper.DATETIME_FORMATE);
							}
						}
					}
					
					Method writeMethod = pd.getWriteMethod();
					writeMethod.invoke(instance, value); // 调用对象的set方法将value赋值给字段
				} catch (IllegalArgumentException e) {
					// argument type mismatch
					// e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return instance;
	}
	
	public static boolean isDateFormat(String str) {
		if (str != null) {
			Matcher m = P_DATE_REGEX.matcher(str);
			if (m.matches()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDatetimeFormat(String str) {
		if (str != null) {
			Matcher m = P_DATETIME_REGEX.matcher(str);
			if (m.matches()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }
	
	/**
	 * 将对象转成Json格式，filterStr用于设置忽略的字段，避免死循环引用
	 * 
	 * @param obj
	 * @return
	 */
	public static String filterStringToJson(Object obj, final String... filterStr) {
		if (obj != null) {
			JsonConfig cfg = new JsonConfig();
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					if (filterStr != null) {
						for (int i = 0, len = filterStr.length; i < len; i++) {
							if (name.equals(filterStr[i])) {
								return true;
							}
						}
					}
					return false;
				}
			});
			// 对日期字段的处理
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			    @Override
			    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			        Date[] dates = (Date[])value;
			        Long[] result = new Long[dates.length];
			        for (int index = 0; index < dates.length; index++) {
			            result[index] = dates[index].getTime();
			        }
			        return result;
			    }

			    @Override
			    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
			        Date date = (Date)value;
			        if (date != null) {
			        	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        	return formatter.format(date.getTime());
			        }
			        return null;
			    }
			});
			JSONArray array = JSONArray.fromObject(obj, cfg);
			return array.toString();
		}
		return "";
	}
	
	public static String getCurrentDateStr() {
		Date date = new Date();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		try {
			if (date != null) {
				 SimpleDateFormat formatter = new SimpleDateFormat(pattern);
				 return formatter.format(date);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将字符串数组map转换以","逗号分隔的字符map
	 * 
	 * @param map
	 * @param trans 是否将字符以ISO-8859-1转成UTF-8
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, String> convertStrMap(Map<String, String[]> map, boolean trans) {
		Map<String, String> strMap = new HashMap<>();
		if (map != null) {
			for (Map.Entry<String, String[]> entry : map.entrySet()) {
				try {
					strMap.put(entry.getKey(), joinStr(",", entry.getValue(), trans));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return strMap;
	}
	
	/**
	 * 将数组转为以","分隔字符串，默认数组格式ISO-8859-1，转为utf8字符
	 * 
	 * @param join
	 * @param strAry
	 * @param trans
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String joinStr(String join, String[] strAry, boolean trans) throws UnsupportedEncodingException{
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < strAry.length; i++){
             if(i == (strAry.length - 1)){
            	 if (trans) {
            		 sb.append(getNewString(strAry[i]));
            	 } else {
            		 sb.append(strAry[i]);
            	 }
             }else{
            	 if (trans) {
            		 sb.append(getNewString(strAry[i])).append(join);
            	 } else {
            		 sb.append(strAry[i]).append(join);
            	 }
             }
        }
        return new String(sb);
    }
	
	public static String getNewString(String str) throws UnsupportedEncodingException {
		if (isEmpty(str)) {
			return "";
		}
        return new String(str.getBytes("ISO-8859-1"), "UTF-8");
    }
	
	/**
	 * String[]参数以","逗号分隔为String类型Map<br>
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, Object> getParamStringMap(Map<String, String[]> map) {
		return getParamStringMap(map, true);
	}
	
	/**
	 * String[]参数以","逗号分隔为String类型Map<br>
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, Object> getParamStringMap(Map<String, String[]> map, boolean trans) {
		Map<String, Object> paramMap = new HashMap<>();
		String[] value = null;
		StringBuffer sb = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			value = entry.getValue();
			if (value != null) {
				sb = new StringBuffer();
				for (int i = 0, len = value.length; i < len; i++) {
					try {
						if (trans) {
							sb.append(getNewString(value[i]));
						} else {
							sb.append(value[i]);
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (i < len - 1) {
						sb.append(",");
					}
				}
				paramMap.put(entry.getKey(), sb.toString());
			}
		}
		return paramMap;
	}
	
	/**
	 * 设置对象转成JSON数组而只保留哪些字段
	 * 
	 * @param obj
	 * @param includeStr
	 * @return
	 */
	public static String includeStringToJsonArray(Object obj, final String... includeStr) {
		if (obj != null) {
			JsonConfig cfg = new JsonConfig();
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					boolean isFilter = true;
					if (includeStr != null) {
						for (int i = 0, len = includeStr.length; i < len; i++) {
							if (name.equals(includeStr[i])) {
								isFilter = false;
							}
						}
					}
					return isFilter;
				}
			});
			// 对日期字段的处理
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			    @Override
			    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			        Date[] dates = (Date[])value;
			        Long[] result = new Long[dates.length];
			        for (int index = 0; index < dates.length; index++) {
			            result[index] = dates[index].getTime();
			        }
			        return result;
			    }

			    @Override
			    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
			        Date date = (Date)value;
			        if (date != null) {
			        	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        	return formatter.format(date.getTime());
			        }
			        return null;
			    }
			});
			JSONArray json = JSONArray.fromObject(obj, cfg);
			return json.toString();
		}
		return "";
	}
	
	/**
	 * 设置对象转成JSON对象而只保留哪些字段
	 * 
	 * @param obj
	 * @param includeStr
	 * @return
	 */
	public static String includeStringToJsonObject(Object obj, final String... includeStr) {
		if (obj != null) {
			JsonConfig cfg = new JsonConfig();
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					boolean isFilter = true;
					if (includeStr != null) {
						for (int i = 0, len = includeStr.length; i < len; i++) {
							if (name.equals(includeStr[i])) {
								isFilter = false;
							}
						}
					}
					return isFilter;
				}
			});
			// 对日期字段的处理
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			    @Override
			    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			        Date[] dates = (Date[])value;
			        Long[] result = new Long[dates.length];
			        for (int index = 0; index < dates.length; index++) {
			            result[index] = dates[index].getTime();
			        }
			        return result;
			    }

			    @Override
			    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
			        Date date = (Date)value;
			        if (date != null) {
			        	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        	return formatter.format(date.getTime());
			        }
			        return null;
			    }
			});
			JSONObject json = JSONObject.fromObject(obj, cfg);
			return json.toString();
		}
		return "";
	}
	
	/**
	 * 如果新文件地址与旧文件路径不同，则删掉旧的文件
	 * 
	 * @param newFilePath
	 * @param oldFilePath
	 */
	public static void deleteOldFile(String newFilePath, String oldFilePath) {
		if (oldFilePath == null) {
			return;
		}
		
		if (!oldFilePath.equals(newFilePath)) {
			File oldFile = new File(oldFilePath);
			if (oldFile.exists() && oldFile.isFile()) {
				oldFile.delete();
			}
		}
	}
	
	/**
	 * 将带日期时间格式字符切割为只有日期形式
	 * 
	 * @param date
	 * @return
	 */
	public static String splitDatetimeToDate(String date) {
		if (isDatetimeFormat(date) && date.length() > 10) {
			return date.substring(0, 10);
		} else if (isDateFormat(date)) {
			return date;
		}
		return "";
	}
	
	
	/**
	 * 将valueStr转成clazz类型
	 * 
	 * @param clazz
	 * @param valueStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertType(Class<T> clazz, String valueStr) {
		Class<T> type = clazz;
		Object value = null;
		
		if (type == String.class) {
			value = valueStr;
		} else if (type == Boolean.class || type == boolean.class) {
			value = filterString2Boolean(valueStr);
		} else if (type == Integer.class || type == int.class) {
			value = filterString2Int(valueStr);
		} else if (type == Long.class || type == long.class) {
			value = filterString2Long(valueStr);
		} else if (type == Double.class || type == double.class) {
			value = filterObj2Double(valueStr);
		} else if (type == Date.class) {
			if (valueStr.endsWith(".0")) {
				valueStr = valueStr.substring(0, valueStr.lastIndexOf(".0"));
			}
			if (isDateFormat(valueStr)) {
				value = DateHelper.convert2Date(valueStr, DateHelper.DATE_FORMATE);
			} else if (isDatetimeFormat(valueStr)) {
				value = DateHelper.convert2Date(valueStr, DateHelper.DATETIME_FORMATE);
			}
		} else if (type == Timestamp.class) {
			// String的类型默认为： yyyy-mm-dd hh:mm:ss
			if (value instanceof String) {
				if (isDateFormat(valueStr)) {
					value = Timestamp.valueOf(valueStr);
				} else if (isDatetimeFormat(valueStr)) {
					if (valueStr.endsWith(".0")) {
						valueStr = valueStr.substring(0, valueStr.lastIndexOf(".0"));
					}
					value = Timestamp.valueOf(valueStr);
				}
			}
		}
		return (T) value;
	}
	
	/**
	 * 通过字段名获取对象的值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		if (obj == null || isEmpty(fieldName)) {
			return null;
		}
		
		Object value = null;
		Class<?> clazz = obj.getClass();
		Field field;
		try {
			field = clazz.getDeclaredField(fieldName);
			
			PropertyDescriptor pd = FilterUtil.initPropertyDescriptor(field, clazz);
			if (pd != null) {
				Method readMethod = pd.getReadMethod();
				value = readMethod.invoke(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 获取obj对象中属性为fieldName的valueStr转换后的值
	 * 如：User中fieldName为age为int类型，则valueStr会转成Integer类型对象值返回
	 * 
	 * @param obj
	 * @param fieldName
	 * @param valueStr
	 * @return
	 */
	public static Object convertFieldValue(Class<?> clazz, String fieldName, String valueStr) {
		Object value = null;
		try {
			Field field = clazz.getDeclaredField(fieldName);
			Class<?> type = field.getType();
			
			value = convertType(type, valueStr);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 根据Java字段转换查找SqlRowSet中是否存在该字段(如：createDate转成CREATE_DATE)
	 * 再获取SqlRowSet的值并转为对象
	 * 
	 * @param rs
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseSqlRowSetToObject(SqlRowSet rs, Class<T> clazz) {
		List<T> list = new LinkedList<>();
		if (rs == null) {
			return list;
		}
		
		try {
			T testInst = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
			return list;
		}
		
		Pattern p = Pattern.compile("[A-Z]");
		Map<String, Object> rsMap = new HashMap<>();
		Map<String, Field> fieldMap = new HashMap<>();
		
		// 遍历获取对象字段
		Field[] fields = clazz.getDeclaredFields();
		for (Field fd : fields) {
			String fieldName = fd.getName();
			Set<String> set = new HashSet<>();
			
			// 获取字段SQL格式字段名(如：createDate => CREATE_DATE)
			Matcher m = p.matcher(fieldName);
			while (m.find()) {
				if (!set.contains(m.group())) {
					set.add(m.group());
					fieldName = fieldName.replace(m.group(), "_" + m.group());
				}
			}
			// rsMap的键为SQL字段名，值为该字段要转成的类型
			rsMap.put(fieldName.toUpperCase(), fd.getType());
			// fieldMap的键为SQL字段名，值为字段
			fieldMap.put(fieldName.toUpperCase(), fd);
		}
		
		while (rs.next()) {
			T instance = null;
			try {
				instance = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
			}
			
			// 找到rs中与fieldMap相同SQL字段名的值，并通过
			for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
				String upperSqlField = entry.getKey();
				try {
					Object value = rs.getObject(upperSqlField, rsMap);
					if (value != null) {
						Field fed = entry.getValue();
						// 获取字段
						PropertyDescriptor pd = initPropertyDescriptor(fed, clazz);
						if (pd == null) {
							continue;
						}
						
						if (fed.getType() != value.getClass()) {
							value = convertType(fed.getType(), value.toString());
						}
						
						Method writeMethod = pd.getWriteMethod();
						writeMethod.invoke(instance, value);
					}
				} catch (InvalidResultSetAccessException e) {
					// 列名无效，跳过
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				} catch (IllegalArgumentException e) {
					// argument type mismatch 参数类型不对
					System.out.println(entry.getKey() + " : " + e.getMessage() + " " + e.getClass());
					e.printStackTrace();
				}
			}
			list.add(instance);
		}
		
		return list;
	}
	
	/**
	 * 将
	 * 根据Java字段转换查找SqlRowSet中是否存在该字段(如：createDate转成CREATE_DATE)，
	 * 再将单个SqlRowSet转换为指定clazz对象
	 * 
	 * @param rs
	 * @param clazz
	 * @return
	 */
	public static <T> T parseOneSqlRowSetToObject(SqlRowSet rs, Class<T> clazz) {
		if (rs == null) {
			return null;
		}
		
		T instance= null;
		try {
			instance = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
			return null;
		}
		
		Pattern p = Pattern.compile("[A-Z]");
		Map<String, Object> rsMap = new HashMap<>();
		
		// 遍历获取对象字段
		Field[] fields = clazz.getDeclaredFields();
		for (Field fd : fields) {
			String fieldName = fd.getName();
			Set<String> set = new HashSet<>();
			
			// 获取字段SQL格式字段名(如：createDate => CREATE_DATE)
			Matcher m = p.matcher(fieldName);
			while (m.find()) {
				if (!set.contains(m.group())) {
					set.add(m.group());
					fieldName = fieldName.replace(m.group(), "_" + m.group());
				}
			}
			// rsMap的键为SQL字段名，值为该字段要转成的类型
			rsMap.put(fieldName.toUpperCase(), fd.getType());
			
			PropertyDescriptor pd = initPropertyDescriptor(fd, clazz);
			if (pd == null) {
				continue;
			}
			
			try {
				Object value = rs.getObject(fieldName.toUpperCase(), rsMap);
				if (value != null && fd.getType() != value.getClass()) {
					value = convertType(fd.getType(), value.toString());
				}
				Method writeMethod = pd.getWriteMethod();
				writeMethod.invoke(instance, value);
			} catch (InvalidResultSetAccessException e) {
				// 列名无效，跳过
			}  catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
				System.out.println(fieldName.toUpperCase() + " : " + e.getMessage() + " " + e.getClass());
				e.printStackTrace();
			}
			
		}
		
		return instance;
	}
	
}
