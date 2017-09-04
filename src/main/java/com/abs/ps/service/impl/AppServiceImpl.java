package com.abs.ps.service.impl;

import com.abs.ps.dao.AppDao;
import com.abs.ps.service.AppService;

public class AppServiceImpl implements AppService {
	private AppDao appDao;
	
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}  
	
	public void deleteAppDebugLog() {
		appDao.deleteAppDebugLog();
	}
	
	public void saveObject(Object obj) {
		appDao.saveObject(obj);
	}
}
