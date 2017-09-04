package com.abs.ps.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.util.StringHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.ps.dao.OrderManagerDao;
import com.abs.ps.domain.OrderManager;
import com.abs.ps.web.dto.OrderManagerDto;


public class OrderManagerDaoImpl extends AbsDaoSupport implements OrderManagerDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 

	@Override
	public IPaging findOrderManagerWithPaging(int pageNumber, int pageSize, Map<String, String> valueMap) {
		// TODO Auto-generated method stub
		IPaging page = null;
		List<OrderManagerDto> result = new ArrayList<OrderManagerDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT OM.*,C.NAME FROM T_ORDER_MANAGER OM,T_CUSTOMER C WHERE OM.CUSTOMER_OID=C.OID");
		List<Object> list = new ArrayList<Object>();
		if (valueMap != null) {
			//开始时间与结束时间
			// System.out.println(hasContent(valueMap, "startDate"));
			if (hasContent(valueMap, "startDate")) {
				sql.append(" and OM.ACCEPT_DATE >='"+valueMap.get("startDate")+"'");
			}
			if (hasContent(valueMap, "endDate")) {
				sql.append(" and OM.ACCEPT_DATE <='"+valueMap.get("endDate")+"'");
			}
		}
		
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);

		while(rs.next()) {
			OrderManagerDto dto = new OrderManagerDto();
			dto.setOid(rs.getString(1));
			dto.setCustomerOid(rs.getString(2));
			dto.setAcceptDate((rs.getString(3)));
			dto.setOrderNum(rs.getString(4));
			dto.setProjectNum(rs.getString(5));
			dto.setProductNum(rs.getString(6));
			dto.setSpecificationName(rs.getString(7));
			dto.setOrderCount(rs.getString(8));
			dto.setCompleteCount(rs.getString(9));
			dto.setUnCompleteCount(rs.getString(10));
			dto.setDelivery(rs.getString(11));
			dto.setStartDate(rs.getString(12));
			dto.setFinishDate(rs.getString(13));
			dto.setStatus(rs.getString(14));
			dto.setRemark(rs.getString(15));
			dto.setCustomerName(rs.getString(16));
			result.add(dto);
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		
		return page;
	}

	@Override
	public OrderManager saveOrUpdate(OrderManager entity) {
		// TODO Auto-generated method stub
		if (entity != null) {
			this.getHibernateTemplate().saveOrUpdate(entity);
			return entity;
		}
		return null;
	}

	@Override
	public OrderManager getObjectById(Long oid) {
		// TODO Auto-generated method stub
		String hql = "from OrderManager om where om.oid=?";
		List<OrderManager> list = this.getHibernateTemplate().find(hql, oid);
		if (list  != null) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteObjectById(String oid) {
		// TODO Auto-generated method stub
		String sql=" DELETE FROM T_ORDER_MANAGER  WHERE OID ="+oid;
		try {
			this.deleteBySql(sql);
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean hasContent(Map<String, String> map, String key) {
		if (map != null && !StringHelper.isEmpty(map.get(key))) {
			return true;
		}
		return false;
	}

	
}
