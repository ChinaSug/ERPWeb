package com.abs.ps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abs.ps.util.JsonUtils;

public class DaoSupportTest {
	
	private long startTime = 0;
	private long endTime = 0;
	
	public ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"beanContext.xml", "coreContext.xml"});
	
	@Before
	public void setup1() {
		startTime = System.currentTimeMillis();
	}
	
	@After
	public void tearDown1() {
		endTime = System.currentTimeMillis();
		
		System.out.println("耗时：" + (endTime-startTime) + "ms");
	}
	
	public static long s = 0;
	public static long e = 0;
	public static void start() {
		s = System.currentTimeMillis();
	}
	
	public static void end() {
		e = System.currentTimeMillis();
		
		System.out.println("耗时：" + (e-s) + "ms");
	}
	
	
	public static String printJsonStr(Object obj, final String... filterStr) {
		JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (filterStr != null) {
					for (String s : filterStr) {
						if (s.equals(name)) {
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
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessor() {
			@Override
			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				java.sql.Date date = (java.sql.Date)value;
				if (date != null) {
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					return formatter.format(date.getTime());
				}
				return null;
			}
			
			@Override
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				java.sql.Date date = (java.sql.Date)value;
				if (date != null) {
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					return formatter.format(date.getTime());
				}
				return null;
			}
			
		});
		JSONArray array = JSONArray.fromObject(obj, cfg);
		System.out.println(array);
		return array.toString();
	}
	
	public static String randomWard(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length; i++) {
            str.append(chars.charAt((int) (Math.random() * 26)));
        }
		return str.toString();
	}
	
	public static int getRandomNum(int size) {
		return (int) (Math.random() * size);
	}
	
}
