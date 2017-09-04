package com.abs.ps.dao.impl;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.ps.dao.AppDao;

public class AppDaoImpl extends AbsDaoSupport implements AppDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
    
	
	public void deleteAppDebugLog() {
		SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT MAX(OID) FROM T_APP_DEBUG_LOG");
		long deleteOidNum = 0;
		if(rs.next()) {
			Long maxOid = rs.getLong(1);
			deleteOidNum = maxOid - 50;
		}

		String sql = "DELETE FROM T_APP_DEBUG_LOG WHERE OID < " + deleteOidNum;
		try {			
			this.deleteBySql(sql);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void saveObject(Object obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}

	
}
