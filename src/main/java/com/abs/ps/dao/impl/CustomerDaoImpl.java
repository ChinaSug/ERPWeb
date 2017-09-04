package com.abs.ps.dao.impl;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.util.StringHelper;
import com.abs.ps.dao.CustomerDao;
import com.abs.ps.domain.Customer;
import com.abs.ps.util.Constants;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.web.dto.CustomerDto;




public class CustomerDaoImpl extends AbsDaoSupport implements CustomerDao {
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    }
   
	@Override
	public CustomerDto saveOrUpdate(CustomerDto customerdto) {
		// TODO Auto-generated method stub
		if(customerdto!=null){
			Customer customer = convertToObj(customerdto);
			this.getHibernateTemplate().saveOrUpdate(customer);
			return convertToDto(customer);
		}
		return null;
	}
	
	@Override
	public CustomerDto getObjectById(Long oid) {
		// TODO Auto-generated method stub
		String hql="FROM Customer WHERE OID = ?";
		List<?> find = this.getHibernateTemplate().find(hql, oid);
		if (find  != null && find.size()==1) {
			Customer obj = (Customer) find.get(0);
			 CustomerDto dto= convertToDto(obj);
			return dto;
		}
		return null;
	}
	
	
	@Override
	public int deleteObjectById(String ids) {
		// TODO Auto-generated method stub
		int num = 0;
		if (!StringHelper.isEmpty(ids)) {
			Object[] split = ids.split(",");
			String re = ids.replaceAll("\\d+", "?");
			String sql = "DELETE FROM T_CUSTOMER WHERE OID IN (" + re + ")";
			num = jdbcTemplate.update(sql.toString(), split);
		}
		return num;
	}
	
	@Override
	public IPaging findObjectWithPaging(int pageNumber, int pageSize,
			Map<String, String> valueMap) {
				
		// TODO Auto-generated method stub
	List<Object> valueList = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer(" select c from Customer c where 1=1");
		
		if(valueMap!=null){
			if (!isEmpty(valueMap.get(Constants.SEARCH_CUSTOMER_NAME))) {
				hql.append(" and c.name like ? ");
				valueList.add("%" + valueMap.get(Constants.SEARCH_CUSTOMER_NAME) + "%");
			} 
			
		/*	if (!isEmpty(valueMap.get("status"))) {
				hql.append(" and c.status = ? ");
				valueList.add(valueMap.get("status"));
			}*/
		}
		hql.append(" order by c.createDate desc ");
		IPaging paging = this.queryByPage(pageNumber, pageSize, valueList.toArray(), hql.toString());
		if (paging != null) {
			List<Object> list = paging.getThisPageElements();
			List<CustomerDto> dtoList = convertToDtoList(list);
			paging.setThisPageElements(dtoList);
		}
		return paging;
		
	}
	
	
	private Customer convertToObj(CustomerDto dto) {
		Customer obj = null;
		if (dto != null) {
			obj = FilterUtil.convertObjectClass(dto, Customer.class);
			if (obj != null && obj.getOid()!=null && obj.getOid()<1) {
				obj.setOid(null);
			}
		}
		return obj;
	}
	private CustomerDto convertToDto(Object obj) {
		CustomerDto dto = null;
		if (obj != null) {
			dto = FilterUtil.convertObjectClass(obj, CustomerDto.class);
			//dto.setDeletable(deletable(dto.getOid()));
		}
		return dto;
	}
	private List<CustomerDto> convertToDtoList(List<Object> objList) {
		List<CustomerDto> dtoList = null;
		if (objList != null) {
			dtoList = new LinkedList<>();
			for (Object obj : objList) {
				dtoList.add(convertToDto(obj));
			}
		}
		return dtoList;
	}
	private boolean isEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return false;
		}
		return true;
	}
	@Override
	public boolean deletable(Long oid) {
		// TODO Auto-generated method stub
		String sql = " SELECT COUNT(*) FROM T_CUSTOMER U " +
				" INNER JOIN T_CONTRACT O ON O.CUSTOMER_OID = U.OID AND U.OID = ? ";
		int count = jdbcTemplate.queryForInt(sql, oid);
		if (count > 0) {
			return false;
		}
		return true;
		
	}

	@Override
	public List<Customer> findCustomers(String name) {
		// TODO Auto-generated method stub
		if (!StringHelper.isEmpty(name)) {
    		return this.getHibernateTemplate().find("from Customer d where d.name like ? order by d.name","%" + name + "%");
    	} else {
    		return this.getHibernateTemplate().find("from Customer d order by d.name");
    	}
	}
	
	
	
}
