package com.abs.ps.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.ps.dao.EmployeeDao;
import com.abs.ps.domain.Employee;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.AMEmployeeDto;
import com.abs.ps.web.dto.NameCodeDto;

public class EmployeeDaoImpl extends AbsDaoSupport implements EmployeeDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 

	public IPaging findObjectWithPaging(int pageNumber, int pageSize, String orgOid) {
		IPaging page = null;
		List<AMEmployeeDto> result = new ArrayList<AMEmployeeDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TE.OID, TE.STATUS,TE.EMP_NAME,TE.POSITION_CODE,TE.DEPART_OID,TD.DEPART_NAME FROM T_EMPLOYEE TE, T_DEPART TD WHERE TE.DEPART_OID=TD.OID");
		Object[] params = {};
		if (!StringHelper.isEmpty(orgOid)) {
			sql.append(" AND TD.ORG_OID=? ");
			params = new Object[1];
			params[0] = orgOid;
		}
		
		sql.append(" ORDER BY TE.EMP_NAME ASC ");
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);
		while(rs.next()) {
			
			AMEmployeeDto empDto = new AMEmployeeDto();

			empDto.setId(rs.getLong(1));
			empDto.setStatus(rs.getString(2));
			empDto.setEmpName(rs.getString(3));
			empDto.setDeletable(deletable(empDto.getId()));
			empDto.setPositionCode(rs.getString(4));			
			empDto.setDepartOid(rs.getString(5));			
			empDto.setDepartName(rs.getString(6));;
			result.add(empDto);
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		
		return page;
		
	}
	
	public boolean deletable(Long oid) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT COUNT(1) FROM T_SKU_INFO WHERE EMP_OID=?");
//
//    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), oid);
//    	if(rs.next() && rs.getLong(1) > 0) {
//    		return false;    		
//    	}
    	return true;
	}
	
	
	public List<NameCodeDto> findAllEmployeeByOrgOid(Long orgOid) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT E.OID, E.EMP_NAME FROM T_EMPLOYEE E, T_DEPART D WHERE E.DEPART_OID=D.OID AND D.ORG_OID=?");
		
		List<NameCodeDto> dtos = new ArrayList<NameCodeDto>();
    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), orgOid);
    	while(rs.next()) {
    		NameCodeDto dto = new NameCodeDto();
    		dto.setCode(rs.getString(1));
    		dto.setName(rs.getString(2));
    		dtos.add(dto);
    	}
    	return dtos;
	}
	
	public List<NameCodeDto> findAllEmployeeByDepartOid(Long departOid) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT E.OID, E.EMP_NAME FROM T_EMPLOYEE E WHERE E.DEPART_OID=?");
		
		List<NameCodeDto> dtos = new ArrayList<NameCodeDto>();
    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), departOid);
    	while(rs.next()) {
    		NameCodeDto dto = new NameCodeDto();
    		dto.setCode(rs.getString(1));
    		dto.setName(rs.getString(2));
    		dtos.add(dto);
    	}
    	return dtos;
	}
	
	public Employee getObjectById(Long id) {
		List<Employee> centerList = this.getHibernateTemplate().find("from Employee d where d.oid=?",id);
		if (centerList != null && centerList.size() > 0) {
			return centerList.get(0);
		}
		return null;
	}
	public void save(Employee obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}
	public void deleteObjectById(String ids) {
		String sql = "DELETE FROM T_EMPLOYEE  WHERE OID in (" + ids + ")";
		try {
			this.deleteBySql(sql);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void saveObjects(List<Employee> objs) {
		this.getHibernateTemplate().saveOrUpdateAll(objs);
	}
	
	
}
