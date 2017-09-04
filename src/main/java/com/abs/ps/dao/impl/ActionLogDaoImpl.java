package com.abs.ps.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.ps.dao.ActionLogDao;
import com.abs.ps.domain.ActionLog;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.ActionLogDto;

public class ActionLogDaoImpl extends AbsDaoSupport implements ActionLogDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
	
	public void save(List<ActionLog> actionLogs) {
		this.getHibernateTemplate().saveOrUpdateAll(actionLogs);
	}
	
	public void save(ActionLog actionLog) {
		this.getHibernateTemplate().saveOrUpdate(actionLog);
	}
	
	public IPaging findByPaging(ActionLogDto criteria, int pageNumber, int pageSize) {
		IPaging page = null;
		List<ActionLogDto> result = new ArrayList<ActionLogDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT U.USER_ID,U.USER_NAME, AL.ACTION_TYPE, AL.FIELD_NAME, AL.IP_ADDRESS, AL.FROM_VALUE, AL.TO_VALUE, AL.CREATE_TIME FROM T_USER U , T_ACTION_LOG AL WHERE U.USER_ID=AL.USER_ID ");
		
		List<Object> list = new ArrayList<Object>();
		
		/*if (!StringHelper.isEmpty(criteria.getCenterCode())) {
			sql.append(" AND U.ORG_OID=? ");
			list.add(Long.parseLong(criteria.getCenterCode()));
		}
		*/
		if (criteria != null && !StringHelper.isEmpty(criteria.getCreateTime())) {
			sql.append(" AND TO_DAYS(AL.CREATE_TIME)=TO_DAYS(?)  ");			
			list.add(DateHelper.convert2Date(criteria.getCreateTime(), DateHelper.DATE_FORMATE));
		}
		
		if (criteria != null && !StringHelper.isEmpty(criteria.getUserId())) {
			sql.append(" AND U.USER_ID LIKE ? ");
			list.add("%"+criteria.getUserId()+"%");
		}
		if(criteria!=null&& !StringHelper.isEmpty(criteria.getActionType())){
			sql.append(" AND AL.ACTION_TYPE LIKE ? ");
			list.add("%"+criteria.getActionType()+"%");
		}
		sql.append(" ORDER BY AL.CREATE_TIME DESC ");
		
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);
		while(rs.next()) {
			ActionLogDto actionLogDto = new ActionLogDto();
			actionLogDto.setUserId(rs.getString(1));
			actionLogDto.setUserName(rs.getString(2));
			//actionLogDto.setCenterName(rs.getString(3));
			actionLogDto.setActionType(rs.getString(3));
			actionLogDto.setFieldName(rs.getString(4));
			actionLogDto.setIpAddr(rs.getString(5));
			actionLogDto.setFromValue(rs.getString(6));
			actionLogDto.setToValue(rs.getString(7));
			actionLogDto.setCreateTime(DateHelper.convert2String(rs.getDate(8), DateHelper.DATETIME_FORMATE));
			
			result.add(actionLogDto);
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		
		return page;
		
		
	}
	
	
	public IPaging findAppDebugPaging(int pageNumber, int pageSize) {
		Object[] values = null;
		String hql = "from AppDebugLog ad order by ad.createDate desc";
		return this.queryByPage(pageNumber, pageSize, values, hql);		
	}
}
