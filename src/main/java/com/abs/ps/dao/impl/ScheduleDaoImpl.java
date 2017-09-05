package com.abs.ps.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.core.util.StringHelper;
import com.abs.ps.dao.ScheduleDao;
import com.abs.ps.domain.ScheduleMain;
import com.abs.ps.util.DateHelper;
import com.abs.ps.web.dto.ScheduleMainDto;

public class ScheduleDaoImpl extends AbsDaoSupport  implements  ScheduleDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
    
    public IPaging findScheduleMainWithPaging(int pageNumber, int pageSize, ScheduleMainDto criteria) {
		/*Object[] values = null; 
		String hql = "from ScheduleMain d ";
		if (!StringHelper.isEmpty(criteria.getName())) {
			 hql = "from ScheduleMain d where d.name like ? ";
			 values = new Object[1];
			 values[0] = "%" + criteria.getName() + "%";
		}if (!StringHelper.isEmpty(criteria.getCustomerName())) {
			hql = "from ScheduleMain d LEFT JOIN Customer c where c.name like ? and d.customerOid = c.oid ";
			 values = new Object[1];
			 values[0] = "%" + criteria.getCustomerName() + "%";
		}if (!StringHelper.isEmpty(criteria.getScheduleDate())) {
			hql = "from ScheduleMain d where d.scheduleDate like ? ";
			 values = new Object[1];
			 values[0] = "%" + criteria.getScheduleDate() + "%";
		}if (!StringHelper.isEmpty(criteria.getStatus())) {
			hql = "from ScheduleMain d where d.status like ? ";
			 values = new Object[1];
			 values[0] = "%" + criteria.getStatus() + "%";
		}
		hql += " order by d.createDate desc";
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	*/
    	
    	IPaging page = null;
		List<ScheduleMainDto> result = new ArrayList<ScheduleMainDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_SCHD_MAIN TS ,T_CUSTOMER C ,T_ITEM I WHERE TS.ITEM_OID = I.oid AND TS.CUSTOMER_OID = C.OID");
		
		List<Object> list = new ArrayList<Object>();
		
		if (!StringHelper.isEmpty(criteria.getCustomerName())) {
			sql.append(" AND (C.NAME LIKE ? OR TS.SCHD_NAME LIKE ? OR PROD_ID LIKE ?)");
			list.add("%"+criteria.getCustomerName()+"%");
			list.add("%"+criteria.getCustomerName()+"%");
			list.add("%"+criteria.getCustomerName()+"%");
		}
		if (!StringHelper.isEmpty(criteria.getScheduleDate())) {
			sql.append(" AND TS.SCHD_DATE = ? ");
			list.add(criteria.getScheduleDate());
		}
		if (!StringHelper.isEmpty(criteria.getStatus())) {
			sql.append(" AND TS.STATUS = ? ");
			list.add(criteria.getStatus());
		}
		sql.append(" ORDER BY TS.CREATE_DATE DESC ");
		
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);

		while(rs.next()) {
			ScheduleMainDto dto = new ScheduleMainDto();
			dto.setOid(rs.getString(1));
			dto.setCustomerOid(rs.getString("CUSTOMER_OID"));
			dto.setCustomerName(rs.getString("NAME"));
			dto.setProdId(rs.getString("PROD_ID"));
			dto.setName(rs.getString("SCHD_NAME"));
			dto.setRespPerson(rs.getString("RESP_PERSON"));
			dto.setScheduleDate(DateHelper.convert2String(rs.getDate("SCHD_DATE"), DateHelper.DATE_FORMATE));
			dto.setProdAmtDate(rs.getString("PROD_AMT_DATE"));
			dto.setMouldTgCompleteDate(rs.getString("MOULD_TG_COMPLETE_DATE"));
			dto.setMouldActCompleteDate(rs.getString("MOULD_ACT_COMPLETE_DATE"));
			dto.setTryAmt(rs.getString("TRY_AMT"));
			dto.setActAmt(rs.getString("ACT_AMT"));
			dto.setTryDate(DateHelper.convert2String(rs.getDate("TRY_DATE"), DateHelper.DATE_FORMATE));
			dto.setActDate(DateHelper.convert2String(rs.getDate("ACT_DATE"), DateHelper.DATE_FORMATE));
			dto.setStatus(rs.getString("STATUS"));
			dto.setProdAmt(rs.getString("PRODUCT_AMT"));
			/*boolean isDeletable = isDeletable(ProductControlMain.class, "bomDetailOid", Long.parseLong(dto.getOid()));
			if (!isDeletable) {
				dto.setDisabled("true");
			}*/
			result.add(dto);
			
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		
		return page;
	}

	@Override
	public List<ScheduleMain> findOutstandingSchedule() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT SM.* FROM T_SCHD_MAIN SM, T_SCHD_DETAIL SD WHERE SM.OID=SD.SCHD_OID AND SM.STATUS='0' AND date(SD.TRY_DATE) = date(NOW()) AND SD.TRY_DATE > SD.ACT_DATE ");
		
		return (List<ScheduleMain>) jdbcTemplate.query(sql.toString(), new Object[] { }, new BeanPropertyRowMapper(ScheduleMain.class));  
	}

	
}
