package com.abs.ps.dao;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.ScheduleMain;
import com.abs.ps.web.dto.ScheduleMainDto;

public interface ScheduleDao extends IDao{
	public IPaging findScheduleMainWithPaging(int pageNumber, int pageSize, ScheduleMainDto criteria);
	public List<ScheduleMain> findOutstandingSchedule();
}
