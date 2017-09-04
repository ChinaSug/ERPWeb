package com.abs.ps.dao;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.ActionLog;
import com.abs.ps.web.dto.ActionLogDto;

public interface ActionLogDao {
	public void save(List<ActionLog> actionLogs);
	public void save(ActionLog actionLog);
	public IPaging findByPaging(ActionLogDto criteria, int pageNumber, int pageSize);
	public IPaging findAppDebugPaging(int pageNumber, int pageSize);
}
