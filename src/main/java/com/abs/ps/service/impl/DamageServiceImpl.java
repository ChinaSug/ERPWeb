package com.abs.ps.service.impl;

import java.util.List;

import com.abs.ps.dao.DamageDao;
import com.abs.ps.service.DamageService;

public class DamageServiceImpl implements DamageService {
	
	private DamageDao damageDao;
	
	public void setDamageDao(DamageDao damageDao) {
		this.damageDao = damageDao;
	}

	@Override
	public List<Object> getCustomerDamageSummary(String reportDate) {
		return damageDao.getCustomerDamageSummary(reportDate);
	}

	@Override
	public List<Object> getDepartDamageSummary(String reportDate) {
		return damageDao.getDepartDamageSummary(reportDate);
	}

	@Override
	public List<Object> getDamageSumTable(String reportDate) {
		return damageDao.getDamageSumTable(reportDate);
	}

}
