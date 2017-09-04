package com.abs.ps.service.impl;

import java.util.List;

import com.abs.ps.dao.ScheduleDao;
import com.abs.ps.domain.ScheduleMain;
import com.abs.ps.service.BackendService;

public class BackendServiceImpl implements BackendService{
	private ScheduleDao scheduleDao;
	
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}
	
	public List<ScheduleMain> findOutstandingSchedule() {
		return scheduleDao.findOutstandingSchedule();
	}
	
	public void save(Object obj) {
		scheduleDao.saveObject(obj);
	}
}
