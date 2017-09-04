package com.abs.ps.service;

import java.util.Map;

import com.abs.ps.domain.OrderManager;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.OrderManagerDto;

public interface OrderManagerService extends IService{
	//订单管理的分页列表
	public ListResult<OrderManagerDto> findOrderManagerWithPaging(int pageNumber, int pageSize, Map<String, String> valueMap);
	/**
	 * 增删查改
	 * @param entity
	 * @return
	 */
	public OrderManager saveOrUpdate(OrderManager entity);
	public OrderManager getObjectById(Long oid);
	public void deleteObjectById(String oid);
}
