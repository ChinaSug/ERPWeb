package com.abs.ps.dao;

import java.util.List;

/**
 * 报废汇总
 * @author 苏建生 2017-7-11
 */
public interface DamageDao {
	/**
	 * 获取客户报废汇总信息
	 * @param reportDate
	 * @return
	 */
	public List<Object> getCustomerDamageSummary(String reportDate);
	
	/**
	 * 获取部门报废汇总信息
	 * @param reportDate
	 * @return
	 */
	public List<Object> getDepartDamageSummary(String reportDate);
	
	/**
	 * 获取汇总表格信息
	 * @param reportDate
	 * @return
	 */
	public List<Object> getDamageSumTable(String reportDate);
}
