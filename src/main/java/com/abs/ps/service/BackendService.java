package com.abs.ps.service;

import java.util.List;

import com.abs.ps.domain.ScheduleMain;

public interface BackendService {
	public List<ScheduleMain> findOutstandingSchedule();
	public void save(Object obj);
}
