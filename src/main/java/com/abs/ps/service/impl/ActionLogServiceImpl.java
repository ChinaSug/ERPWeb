package com.abs.ps.service.impl;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.ActionLogDao;
import com.abs.ps.domain.ActionLog;
import com.abs.ps.service.ActionLogService;
import com.abs.ps.web.dto.ActionLogDto;

public class ActionLogServiceImpl implements ActionLogService{
	private ActionLogDao actionLogDao;
	
	public void setActionLogDao(ActionLogDao actionLogDao) {
		this.actionLogDao = actionLogDao;
	}
	
	public void save(List<ActionLog> actionLogs) {
		actionLogDao.save(actionLogs);
	}
	
	public void save(ActionLog actionLog) {
		actionLogDao.save(actionLog);
	}
	public IPaging findByPaging(ActionLogDto criteria, int pageNumber, int pageSize) {
		return actionLogDao.findByPaging(criteria, pageNumber, pageSize);
	}
	public IPaging findAppDebugPaging(int pageNumber, int pageSize) {
		return actionLogDao.findAppDebugPaging(pageNumber, pageSize);
	}
}
