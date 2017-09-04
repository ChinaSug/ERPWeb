package com.abs.ps.dao;

import java.util.Map;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.OrderManager;

public interface OrderManagerDao extends IDao{
	//订单管理的分页列表
	public IPaging findOrderManagerWithPaging(int pageNumber, int pageSize, Map<String, String> valueMap);
	/**
	 * 增删查改
	 * @param entity
	 * @return
	 */
	public OrderManager saveOrUpdate(OrderManager entity);
	public OrderManager getObjectById(Long oid);
	public void deleteObjectById(String oid);
}