package com.abs.ps.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.ps.dao.OrgDao;
import com.abs.ps.domain.DocumentNum;
import com.abs.ps.domain.Organization;
import com.abs.ps.util.Constants;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.NameCodeDto;

public class OrgDaoImpl extends AbsDaoSupport implements OrgDao{
	
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
    
	public IPaging findOrgWithPaging(int pageNumber, int pageSize, String orgOid) {
		Object[] values = null; 
		String hql = "from Organization o order by o.createDate desc";
		if (!StringHelper.isEmpty(orgOid)) {
			hql = "from Organization o where o.oid=? order by o.createDate desc";
			 values = new Object[1];
			 values[0] = Long.parseLong(orgOid);
		}
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	
	}
	
	public List<Organization> getAllOrganization() {
		return this.getHibernateTemplate().find("from Organization o order by o.orgName");
	}
	
	public Organization getOrganizationById(Long id) {
		List<Organization> centerList = this.getHibernateTemplate().find("from Organization c where c.oid=?",id);
		if (centerList != null && centerList.size() > 0) {
			return centerList.get(0);
		}
		return null;
	}
	
	public boolean isOrgExist(String name) {
		List<Organization> centerList = this.getHibernateTemplate().find("from Organization c where c.orgName=?",name);
		if (centerList != null && centerList.size() > 0) {
			return true;
		}
		return false;
	}
	
	public Organization save(Organization obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
		this.getHibernateTemplate().flush(); 
		return obj;
	}
	
	public void saveDoc(DocumentNum obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}
	
	public void saveObjects(List<Organization> objs) {
		this.getHibernateTemplate().saveOrUpdateAll(objs);
	}	
	
	
	public boolean isOrgCodeAvailabe(String orgCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) FROM T_ORG TD WHERE TD.ORG_CODE=?");

    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), orgCode);
    	if(rs.next() && rs.getLong(1) > 0) {
    		return false;    		
    	}
    	return true;
	}
	
	public boolean deletable(Long orgOid) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) FROM T_DEPART TD WHERE TD.ORG_OID=?");

    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), orgOid);
    	if(rs.next() && rs.getLong(1) > 0) {
    		return false;    		
    	}
    	return true;
	}
	
	public void deletePeriodByOrgOid(String id) {
		/*String sql = "DELETE FROM T_PERIOD  WHERE ORG_OID in (" + id + ")";
		try {
			this.deleteBySql(sql);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		String sql4 = "DELETE FROM T_APPOINT_TYPE  WHERE ORG_OID in (" + id + ")";
		try {
			this.deleteBySql(sql4);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}*/
	}
	
	public void deleteObjectById(String ids) {
		String sql = "DELETE FROM T_ORG  WHERE OID in (" + ids + ")";
		try {
			this.deleteBySql(sql);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		/*String sql2 = "DELETE FROM T_DOC_NUM  WHERE ORG_OID in (" + ids + ")";
		try {
			this.deleteBySql(sql2);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		String sql3 = "DELETE FROM T_PERIOD  WHERE ORG_OID in (" + ids + ")";
		try {
			this.deleteBySql(sql3);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		String sql4 = "DELETE FROM T_APPOINT_TYPE  WHERE ORG_OID in (" + ids + ")";
		try {
			this.deleteBySql(sql4);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}*/
	}
	
	@Override
	public void saveOrgDefineEntDataType(Long orgOid, String[] entDataTypes) {
		/*
		jdbcTemplate.update("DELETE FROM T_SUPPORT_DATA WHERE DATA_TYPE = ? AND DATA_CODE = ?", Constants.SUPPORT_ORG_CONF, orgOid);
		
		if (entDataTypes.length <= 0 || orgOid <= 0) {
			return;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO T_SUPPORT_DATA (DATA_NAME, DATA_CODE, DATA_TYPE) ");
		sql.append(" VALUES (?, ?, ?) ");
		
		Object[] values = null;
		for (int i = 0; i < entDataTypes.length; i++) {
			if (!StringHelper.isEmpty(entDataTypes[i])) {
				values = new Object[]{entDataTypes[i], orgOid, Constants.SUPPORT_ORG_CONF};
				jdbcTemplate.update(sql.toString(), values);
			}
		}*/
	}
	
	public List<NameCodeDto> getOrgDefineEntDataType(Long orgOid) {
		
		/*List<NameCodeDto> entDataTypeList = new ArrayList<NameCodeDto>();
		NameCodeDto dto = null;
		
		String sql = "SELECT S.OID, S.DATA_NAME, S.DATA_TYPE, S.DATA_CODE FROM T_SUPPORT_DATA AS S WHERE S.DATA_TYPE = ? AND S.DATA_CODE = ? ";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, Constants.SUPPORT_ORG_CONF, orgOid);
		while (rs.next()) {
			dto = new NameCodeDto();
			dto.setCode(rs.getString(1));
			dto.setName(rs.getString(2));
			dto.setType(rs.getString(3));
			dto.setValue(rs.getString(4));
			entDataTypeList.add(dto);
		}
		
		return entDataTypeList;*/
		return null;
	}
	
	public Long getOrgOidByName(String orgName) {
		String sql = "SELECT OID FROM T_ORG WHERE ORG_NAME =?";
		return jdbcTemplate.queryForLong(sql, orgName);
	}



}

