package com.abs.ps.service;

import java.util.List;

import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ScheduleDetailDto;
import com.abs.ps.web.dto.ScheduleMainDto;

public interface ScheduleService extends IService{
	public ListResult<ScheduleMainDto> findScheduleMainWithPaging(int pageNumber, int pageSize, ScheduleMainDto criteria);
	public List<ScheduleDetailDto> findScheduleDetailDtos(String scheduleOid);
	public ScheduleMainDto getScheduleByOid(String schedOid);
}
