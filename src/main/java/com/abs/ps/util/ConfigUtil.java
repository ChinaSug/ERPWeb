package com.abs.ps.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abs.ps.dao.OrgDao;
import com.abs.ps.dao.SupportingDataDao;

public class ConfigUtil {

	public static final String CONF_FILE = "/ServerConfig.properties";
	
	private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"beanContext.xml","absContext.xml"}); 
	
	private ConfigUtil() {};
	
	/**
	 * 获取属于格力工业城企业的组织机构代码数组
	 * 
	 * @return
	 */
	public static String[] getBelongGreeIndustryCity() {
		String property = getProperties("gree_industry_city");
		if (property != null) {
			return property.split(",");
		}
		return null;
	}
	
	/**
	 * 获取企业统计指标中主园区工业产值的企业的组织机构代码数组
	 * 
	 * @return
	 */
	public static String[] getMainOrgIndustryEntCode() {
		String property = getProperties("NanPing_statis_main_industry");
		if (property != null) {
			return property.split(",");
		}
		return null;
	}
	
	public static String getProperties(String propName) {
		
		Properties p = new Properties();
		InputStream in = null;
		
		try {
			in = ConfigUtil.class.getResourceAsStream(CONF_FILE);
			if (in == null) {
				throw new NullPointerException("格力工业城配置文件 " + CONF_FILE + " 没有找到");
			}
			
			p.load(in);
			String property = p.getProperty(propName);
			if (property != null) {
				return StringHelper.getNewString(property);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static String getOrgNameByNanPing() {
		return getProperties("NanPing_org_name");
	}
	
	/**
	 * 初始化工业行业Oid常量
	 * 
	 * @return
	 */
	public static String initIndustryOid() {
		SupportingDataDao suppDao = (SupportingDataDao) context.getBean("supportingDataDao");
		return String.valueOf(suppDao.getSupportDataOid("工业", Constants.SUPPORT_ENT_TYPE));
	}
	
	/**
	 * 初始化南屏园区Oid常量
	 * 
	 * @return
	 */
	public static String initNanPingOrgOid() {
		OrgDao orgDao = (OrgDao) context.getBean("orgDao");
		return String.valueOf(orgDao.getOrgOidByName(getOrgNameByNanPing()));
	}
	
	public static void main(String[] args) {
	}
	
}
