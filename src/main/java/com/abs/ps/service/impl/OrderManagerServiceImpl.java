package com.abs.ps.service.impl;

import java.util.Map;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.OrderManagerDao;
import com.abs.ps.domain.OrderManager;
import com.abs.ps.service.AbstractService;
import com.abs.ps.service.OrderManagerService;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.OrderManagerDto;


public class OrderManagerServiceImpl extends AbstractService implements OrderManagerService{
	private OrderManagerDao orderManagerDao;
	
	public void setOrderManagerDao(OrderManagerDao orderManagerDao) {
		this.orderManagerDao = orderManagerDao;
		super.setIDao(orderManagerDao);
	}

	@Override
	public ListResult<OrderManagerDto> findOrderManagerWithPaging(int pageNumber, int pageSize,
			Map<String, String> valueMap) {
		// TODO Auto-generated method stub
		IPaging paging = orderManagerDao.findOrderManagerWithPaging(pageNumber, pageSize, valueMap);
		ListResult<OrderManagerDto> result = new ListResult<OrderManagerDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {	
			result.setRows(paging.getThisPageElements());
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}

	@Override
	public OrderManager saveOrUpdate(OrderManager entity) {
		// TODO Auto-generated method stub
		return orderManagerDao.saveOrUpdate(entity);
	}

	@Override
	public OrderManager getObjectById(Long oid) {
		// TODO Auto-generated method stub
		return orderManagerDao.getObjectById(oid);
	}

	@Override
	public void deleteObjectById(String oid) {
		// TODO Auto-generated method stub
		orderManagerDao.deleteObjectById(oid);
		
	}

	
}
