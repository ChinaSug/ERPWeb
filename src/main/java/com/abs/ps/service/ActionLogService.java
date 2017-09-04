package com.abs.ps.service;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.ActionLog;
import com.abs.ps.web.dto.ActionLogDto;

public interface ActionLogService {
	public void save(List<ActionLog> actionLogs);
	public void save(ActionLog actionLog);
	public IPaging findByPaging(ActionLogDto criteria, int pageNumber, int pageSize);
	public IPaging findAppDebugPaging(int pageNumber, int pageSize);

}
