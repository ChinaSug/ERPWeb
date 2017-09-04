/**
 * 
 */
package com.abs.ps.dao;


import java.util.List;
import java.util.Map;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.Customer;
import com.abs.ps.web.dto.CustomerDto;
/**
 * @author zhengxy 
 *
 */
public interface CustomerDao {
	public CustomerDto saveOrUpdate(CustomerDto customer);
	public CustomerDto getObjectById(Long oid);
	public int deleteObjectById(String ids);
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, Map<String, String> valueMap);
	/**
	 * 当客户信息没有正常有效的合约关联情况下，可以对客户信息进行删除
	 * 
	 * @param oid
	 * @return
	 */
	public boolean deletable(Long oid);
	
	
	public List<Customer> findCustomers(String name);
}
