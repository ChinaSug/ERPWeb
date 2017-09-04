package com.abs.ps.dao.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.ps.dao.DamageDao;

/**
 * @author 苏建生 2017-7-12
 */
public class DamageDaoImpl extends AbsDaoSupport implements DamageDao {
	
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 

	@Override
	public List<Object> getCustomerDamageSummary(String reportDate) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT D.TOTAL_FEE, C.NAME FROM DAM_CUST_VIEW D ")
		.append(" LEFT JOIN T_CUSTOMER C ON D.CUST_OID = C.OID ")
		.append(" WHERE D.REPORT_DATE = ? ");
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), reportDate);
		
		List<Object> list = new LinkedList<>();
		while (rs.next()) {
			Map<String, Object> jsonMap = new HashMap<>(4);
			jsonMap.put("failureCost", rs.getDouble("TOTAL_FEE"));
			jsonMap.put("cusName", rs.getString("NAME"));
			
			list.add(jsonMap);
		}
		return list;
	}
	
	@Override
	public List<Object> getDepartDamageSummary(String reportDate) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT V.TOTAL_FEE, D.DEPART_NAME FROM DAM_DEPART_VIEW V ")
		.append(" LEFT JOIN T_DEPART D ON D.OID = V.DEPART_OID ")
		.append(" WHERE V.REPORT_DATE = ? ");
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), reportDate);
		
		List<Object> list = new LinkedList<>();
		while (rs.next()) {
			Map<String, Object> jsonMap = new HashMap<>(4);
			jsonMap.put("failureCost", rs.getDouble("TOTAL_FEE"));
			jsonMap.put("departName", rs.getString("DEPART_NAME"));
			
			list.add(jsonMap);
		}
		return list;
	}
	
	@Override
	public List<Object> getDamageSumTable(String reportDate) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT TD.DAM_NUM, TC.NAME, TI.ITEM_CODE, TI.ITEM_NAME, TD.BAD_AMT, TD.PIE_AMT, TD.COST, TD.ITEM_FEE, TD.PIE_FEE, TD.REPORT_DATE ")
		.append(" , TD.DAMAGE_TYPE, TBD.PROD_ID, TBD.NAME ")
		.append(" FROM T_DAMAGE TD ")
		.append(" LEFT JOIN T_ITEM TI ON TD.ITEM_OID = TI.OID ")
		.append(" LEFT JOIN T_ITEM_TYPE TIT ON TI.TYPE_OID = TIT.OID ")
		.append(" LEFT JOIN T_BOM_DETAIL TBD ON TD.BOM_DETAIL_OID = TBD.OID ")
		.append(" LEFT JOIN T_CUSTOMER TC ON TD.CUST_OID = TC.OID ")
		.append(" WHERE TD.REPORT_DATE LIKE ? ")
		.append(" ORDER BY TC.OID DESC, TD.REPORT_DATE ASC ");
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), reportDate + "%");
		
		List<Object> list = new LinkedList<>();
		while (rs.next()) {
			Map<String, Object> jsonMap = new HashMap<>(16);
			jsonMap.put("damNum", rs.getString("DAM_NUM"));
			jsonMap.put("customerName", rs.getString("NAME"));
			String damageType = rs.getString("DAMAGE_TYPE");
			if ("1".equals(damageType)) {
				jsonMap.put("itemCode", rs.getString("PROD_ID"));
				jsonMap.put("itemName", rs.getString("NAME"));
			} else if ("2".equals(damageType)) {
				jsonMap.put("itemCode", rs.getString("ITEM_CODE"));
				jsonMap.put("itemName", rs.getString("ITEM_NAME"));
			}
			jsonMap.put("badAmt", rs.getString("BAD_AMT"));
			jsonMap.put("pieAmt", rs.getString("PIE_AMT"));
			jsonMap.put("cost", rs.getString("COST"));
			jsonMap.put("itemFee", rs.getString("ITEM_FEE"));
			jsonMap.put("pieFee", rs.getString("PIE_FEE"));
			jsonMap.put("repostDate", rs.getString("REPORT_DATE"));
			
			list.add(jsonMap);
		}
		return list;
	}

}
