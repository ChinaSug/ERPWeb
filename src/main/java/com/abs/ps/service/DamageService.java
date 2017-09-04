package com.abs.ps.service;

import java.util.List;

/**
 * @author 苏建生 2017-7-14
 */
public interface DamageService {
	public List<Object> getCustomerDamageSummary(String reportDate);
	public List<Object> getDepartDamageSummary(String reportDate);
	public List<Object> getDamageSumTable(String reportDate);
}
