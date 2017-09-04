/**
 * 
 */
package com.abs.ps.service.impl;

import java.util.List;
import java.util.Map;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.CustomerDao;
import com.abs.ps.domain.Customer;
import com.abs.ps.service.CustomerService;
import com.abs.ps.web.dto.CustomerDto;

/**
 * @author Administrator
 *
 */
public class CustomerServiceImpl implements CustomerService {
	private CustomerDao customerDao = null;
	
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public CustomerDto saveOrUpdate(CustomerDto customer) {
		// TODO Auto-generated method stub
		return customerDao.saveOrUpdate(customer);
	}

	@Override
	public CustomerDto getObjectById(Long oid) {
		// TODO Auto-generated method stub
		return customerDao.getObjectById(oid);
	}

	@Override
	public int deleteObjectById(String ids) {
		// TODO Auto-generated method stub
		return customerDao.deleteObjectById(ids);
	}

	@Override
	public IPaging findObjectWithPaging(int pageNumber, int pageSize,
			Map<String, String> valueMap) {
		// TODO Auto-generated method stub
		IPaging paging= customerDao.findObjectWithPaging(pageNumber, pageSize, valueMap);
		return paging;
		
	}

	@Override
	public List<Customer> findCustomers(String name) {
		// TODO Auto-generated method stub
		return customerDao.findCustomers(name);
	}
	
	
	

}
