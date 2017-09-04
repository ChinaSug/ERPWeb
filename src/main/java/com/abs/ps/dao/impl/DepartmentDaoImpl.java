package com.abs.ps.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.ps.dao.DepartmentDao;
import com.abs.ps.domain.Department;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.DepartDto;
import com.abs.ps.web.dto.DepartmentDto;

public class DepartmentDaoImpl extends AbsDaoSupport implements DepartmentDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, String orgOid) {
		Object[] values = null; 
		String hql = "from Department";
		if (!StringHelper.isEmpty(orgOid)) {
			hql = "from Department o where o.orgOid=?";
			 values = new Object[1];
			 values[0] = Long.parseLong(orgOid);
		}
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	
	}
	
	public boolean deletable(Long orgOid) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) FROM T_EMPLOYEE TE WHERE TE.DEPART_OID=?");

    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), orgOid);
    	if(rs.next() && rs.getLong(1) > 0) {
    		return false;    		
    	}
    	return true;
	}
	
	public Department getObjectById(Long id) {
		List<Department> centerList = this.getHibernateTemplate().find("from Department d where d.oid=?",id);
		if (centerList != null && centerList.size() > 0) {
			return centerList.get(0);
		}
		return null;
	}
	public void save(Department obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}
	
	public void saveObjects(List<Department> objs) {
		this.getHibernateTemplate().saveOrUpdateAll(objs);
	}	
	
	public void deleteObjectById(String ids) {
		String sql = "DELETE FROM T_DEPART  WHERE OID in (" + ids + ")";
		try {
			this.deleteBySql(sql);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public List<Department> getDepartmentByOrg(Long orgOid) {
		return this.getHibernateTemplate().find("from Department o where o.status='1' and o.orgOid=?", orgOid);
	}
	
	public List<Department> getAllDepartment() {
		return this.getHibernateTemplate().find("from Department c where c.status='1'");
	}
	@Override
	public IPaging findObjectWithPaging(int pageNumber, int pageSize,
			DepartDto depart) {
		// TODO Auto-generated method stub
		IPaging page = null;
		List<DepartDto> result = new ArrayList<DepartDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT D.*, C.ORG_NAME FROM T_DEPART D, T_ORG C WHERE D.ORG_OID = C.OID");
		
		List<Object> list = new ArrayList<Object>();
		if(!StringHelper.isEmpty(depart.getDepartName())){
			sql.append(" AND D.DEPART_NAME LIKE ?");
			list.add("%"+depart.getDepartName()+"%");
		}
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		sql.append(" ORDER BY D.OID DESC ");
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);

		while(rs.next()) {
			DepartDto dto = new DepartDto();
			dto.setId(rs.getString(1));
			dto.setDepartName(rs.getString(2));
			dto.setStatus(rs.getString(3));
			dto.setOrgOid(rs.getString(4));
			dto.setCreateBy(rs.getString(5));
			dto.setCreateDate(DateHelper.convert2String(rs.getDate(6), DateHelper.DATETIME_FORMATE));
			dto.setLastModifyBy(rs.getString(7));
			dto.setLastModifyDate(DateHelper.convert2String(rs.getDate(8), DateHelper.DATETIME_FORMATE));
			dto.setOrgName(rs.getString(8));
			result.add(dto);
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		
		return page;
	}
	

}
