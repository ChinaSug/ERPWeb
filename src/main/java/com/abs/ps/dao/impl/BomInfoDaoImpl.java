package com.abs.ps.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.ps.dao.BomInfoDao;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.domain.ProductControlMain;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.BomMainDto;

public class BomInfoDaoImpl extends AbsDaoSupport implements BomInfoDao {
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 

	@Override
	public IPaging findBomMainByPaging(int pageNumber, int pageSize,
			BomMainDto criteria) {
		IPaging page = null;
		List<BomMainDto> result = new ArrayList<BomMainDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_BOM_MAIN B ,T_CUSTOMER C WHERE B.CUSTOMER_OID = C.OID");
		
		List<Object> list = new ArrayList<Object>();
		
		if (!StringHelper.isEmpty(criteria.getStatus())) {
			sql.append(" AND B.STATUS=? ");
			list.add(criteria.getStatus());
		}
		/*if (!StringHelper.isEmpty(criteria.getBomNum())) {
			sql.append(" AND (B.BOM_NUM LIKE ? OR B.CONFIRM_PERSON LIKE ? OR B.RESP_PERSON LIKE ? OR C.NAME LIKE ? )");
			list.add("%"+criteria.getBomNum()+"%");
			list.add("%"+criteria.getBomNum()+"%");
			list.add("%"+criteria.getBomNum()+"%");
			list.add("%"+criteria.getBomNum()+"%");
		}*/
		
		if (!StringHelper.isEmpty(criteria.getBomNum())) {
			sql.append(" AND B.BOM_NUM LIKE ? ");
			list.add("%"+criteria.getBomNum()+"%");
		}
		
		sql.append(" ORDER BY B.CREATE_DATE DESC ");
		
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);

		while(rs.next()) {
			BomMainDto dto = new BomMainDto();
			dto.setOid(rs.getString(1));
			dto.setCustomerOid(rs.getString(2));
			dto.setCustomerName(rs.getString("NAME"));
			dto.setBomNum(rs.getString(3));
			dto.setConfirmPerson(rs.getString(4));
			dto.setRespPerson(rs.getString(5));
			dto.setStatus(rs.getString(6));
			dto.setCreateBy(rs.getString(7));
			dto.setCreateDate(DateHelper.convert2String(rs.getDate(8), DateHelper.DATETIME_FORMATE));
			dto.setLastModifyBy(rs.getString(9));
			dto.setLastModifyDate(DateHelper.convert2String(rs.getDate(10), DateHelper.DATETIME_FORMATE));
			
			boolean isDeletable = isDeletable(ProductControlMain.class, "bomDetailOid", Long.parseLong(dto.getOid()));
			if (!isDeletable) {
				dto.setDisabled("true");
			}
			result.add(dto);
			
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		
		return page;
	}

	@Override
	public List<BomDetail> findBomDetailDtoBybomNum(String bomNum) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT BD.* FROM T_BOM_DETAIL BD,T_BOM_MAIN BM WHERE BD.OID NOT IN(SELECT BOM_OID FROM T_PC_MAIN)AND BD.BOM_OID = BM.OID AND BM.BOM_NUM = ?");
		return (List<BomDetail>) jdbcTemplate.query(sql.toString(), new Object[] { bomNum }, new BeanPropertyRowMapper(BomDetail.class));  
	
	}
	
	@Override
	public BomDetail getBomDeatilByOid(long oid) {
		String hql = "from BomDetail where oid = ?";
		List find = getHibernateTemplate().find(hql);
		if (find != null && find.size() > 0) {
			return (BomDetail) find.get(0);
		}
		return null;
	}

	
}
