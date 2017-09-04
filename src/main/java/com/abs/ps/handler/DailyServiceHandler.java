package com.abs.ps.handler;

import java.util.List;

import org.apache.log4j.Logger;

import com.abs.ps.domain.ScheduleMain;
import com.abs.ps.service.BackendService;

public class DailyServiceHandler {
	private Logger logger=Logger.getLogger(DailyServiceHandler.class.getName()); 
	private BackendService backendService;
	
	public void setBackendService(BackendService backendService) {
		this.backendService = backendService;
	}
	
	public void execute() throws Exception {
		logger.info("start to execute()");	
		List<ScheduleMain> smList = backendService.findOutstandingSchedule();
		if (smList != null && !smList.isEmpty()) {
			for (ScheduleMain sm : smList) {
				if (sm != null) {
					sm.setStatus("2");
					backendService.save(sm);
					
					logger.info("Update Schedule Main status to outstanding." + sm.toString());					
				}
			}
		}

	}
}
