package com.abs.ps.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.ps.dao.SupportingDataDao;
import com.abs.ps.util.Constants;
import com.abs.ps.web.dto.NameCodeDto;

public class SupportingDataDaoImpl extends AbsDaoSupport implements SupportingDataDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
	
	public List<NameCodeDto> findSupportingDataByType(String dataType) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT OID, DATA_NAME, DATA_CODE FROM T_SUPPORT_DATA WHERE DATA_TYPE=?");
		
		List<NameCodeDto> dtos = new ArrayList<NameCodeDto>();
    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), dataType);
    	while(rs.next()) {
    		NameCodeDto dto = new NameCodeDto();
    		dto.setCode(rs.getString(1));
    		dto.setName(rs.getString(2));
    		dto.setValue(rs.getString(3));
    		dtos.add(dto);
    	}
    	return dtos;
	}
	
	public Long getSupportDataOid(String dataName, String dataType) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT OID FROM T_SUPPORT_DATA ");
		sql.append(" WHERE DATA_NAME = ? AND DATA_TYPE = ? ");
		
		return jdbcTemplate.queryForLong(sql.toString(), dataName, dataType);
	}
	
	public List<Long> getOrgConfList(long orgOid) {
		
		List<Long> orgOidList = new ArrayList<Long>(); 
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DATA_NAME FROM T_SUPPORT_DATA ");
		sql.append(" WHERE DATA_TYPE = ? AND DATA_CODE = ? ");
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), Constants.SUPPORT_STATIS_ORG_CONF, orgOid);
		while (rs.next()) {
			orgOidList.add(rs.getLong("DATA_NAME"));
		}
		orgOidList.add(orgOid);
		
		return orgOidList;
	}
	
	@Override
	public List<NameCodeDto> findSupportingData(String type, String code) {
		String sql = "SELECT OID, DATA_NAME, DATA_CODE FROM T_SUPPORT_DATA WHERE DATA_TYPE = ? and DATA_CODE = ? ";
		List<NameCodeDto> dtos = new ArrayList<NameCodeDto>();
    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), type, code);
    	while(rs.next()) {
    		NameCodeDto dto = new NameCodeDto();
    		dto.setValue(rs.getString(1));
    		dto.setName(rs.getString(2));
    		dto.setCode(rs.getString(3));
    		dtos.add(dto);
    	}
    	return dtos;
	}
	
}
