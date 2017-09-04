/**
 * 
 */
package com.abs.ps.service;

import java.util.List;
import java.util.Map;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.Customer;
import com.abs.ps.web.dto.CustomerDto;

/**
 * @author zhengxy
 *
 */
public interface CustomerService {
	public CustomerDto saveOrUpdate(CustomerDto customer);
	public CustomerDto getObjectById(Long oid);
	public int deleteObjectById(String ids);
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, Map<String, String> valueMap);
	
	public List<Customer> findCustomers(String name);
}
