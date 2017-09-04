package com.abs.ps.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.ps.dao.ProductControlDao;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.domain.BomMain;
import com.abs.ps.domain.Department;
import com.abs.ps.domain.Machine;
import com.abs.ps.domain.ProductControlDetail;
import com.abs.ps.domain.ProductControlMain;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ProductControlDetailDto;
import com.abs.ps.web.dto.ProductControlMainDto;

public class ProductControlDaoImpl extends AbsDaoSupport implements ProductControlDao {
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
        
    public IPaging findProductControlMainWithPaging(int pageNumber, int pageSize, ProductControlMainDto criteria) {
		StringBuffer hql = new StringBuffer();
		List<Object> valueList = new ArrayList<>();
		
		hql.append(" select pcm, bm, bd, m, d ")
		.append(" from ProductControlMain pcm, BomMain bm, BomDetail bd, Machine m, Department d ")
		.append(" where pcm.bomDetailOid = bd.oid and bm.oid = bd.bomOid and pcm.machineOid = m.oid and pcm.departOid = d.oid ");
		
		if (criteria != null) {
			if (!StringHelper.isEmpty(criteria.getProdId())) {
				hql.append(" and bd.prodId like ? ");
				valueList.add("%" + criteria.getProdId() + "%");
			}
			if (!StringHelper.isEmpty(criteria.getPublishDate())) {
				hql.append(" and date_format(pcm.publishDate ,'%Y-%m-%d') = ? ");
				valueList.add(criteria.getPublishDate());
			}
			if (!StringHelper.isEmpty(criteria.getStatus())) {
				hql.append(" and pcm.status = ? ");
				valueList.add(criteria.getStatus());
			}
		}
		hql.append(" order by pcm.oid desc ");
		
		int totalCount = this.getTotalCount(hql.toString(), valueList.toArray());
		List<?> result = this.query(hql.toString(), valueList.toArray(), pageNumber, pageSize);
		
		IPaging paging = new PagingImpl(result, totalCount, pageNumber, pageSize);
		List<ProductControlMainDto> mainDtoList = null;
		if (paging != null) {
			List<?> list = paging.getThisPageElements();
			if (list != null) {
				mainDtoList = new ArrayList<>(list.size());
				for (Object obj : list) {
					if (obj instanceof Object[]) {
						Object[] objs = (Object[]) obj;
						ProductControlMain pcm = (ProductControlMain) objs[0];
						BomMain bm = (BomMain) objs[1];
						BomDetail bd = (BomDetail) objs[2];
						Machine m = (Machine) objs[3];
						Department d = (Department) objs[4];
						
						ProductControlMainDto dto = FilterUtil.convertObjectClass(pcm, ProductControlMainDto.class);
						dto.setBomNum(bm.getBomNum());
						dto.setProdId(bd.getProdId());
						dto.setMachineName(m.getName());
						dto.setDepartName(d.getDepartName());
						
						mainDtoList.add(dto);
					}
				}
				paging.setThisPageElements(mainDtoList);
			}
		}
		
		return paging;
	}
    
    @Override
    public List<Object> getWeekPlanTable(int year, int month, int week) {
    	List<Object> list = new LinkedList<>();
    	if (month < 1 || month > 12) {
    		return list;
    	}
    	if (week < 1 || week > 5) {
    		return list;
    	}
    	
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month - 1);
    	
    	int dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int endDate = week * 7;
		int startDate = endDate - 6;
		if (startDate > dayOfMonth) {
			return list;
		}
		endDate = endDate > dayOfMonth ? dayOfMonth : endDate;
		
		c.set(Calendar.DATE, startDate);
		String startD = (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
		c.set(Calendar.DATE, endDate);
		String endD = (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
		
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT PD.PRODUCE_DATE, M.NAME, BD.PROD_ID, PM.PERIOD, PM.CAVE_NUM," +
    			" PD.PLAN_PROD_AMT, PD.ACT_PROD_AMT, PD.GOOD_PROD_AMT, PD.BAD_PROD_AMT, PD.EFF_RATE ")
    	.append(" FROM T_PC_DETAIL PD ")
    	.append(" LEFT JOIN T_PC_MAIN PM ON PM.OID = PD.PC_OID ")
    	.append(" LEFT JOIN T_BOM_DETAIL BD ON BD.OID = PM.BOM_OID ")
    	.append(" LEFT JOIN T_MACHINE M ON M.OID = PM.MACHINE_OID ")
    	.append(" WHERE PD.PRODUCE_DATE >= ? AND PD.PRODUCE_DATE <= ? ")
    	.append(" ORDER BY PD.PRODUCE_DATE ASC, PD.OID ASC ");
    	
    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), startD, endD);
    	while (rs.next()) {
    		Map<String, Object> map = new HashMap<>();
    		map.put("produceDate", rs.getString("PRODUCE_DATE"));
    		map.put("name", rs.getString("NAME"));
    		map.put("prodId", rs.getString("PROD_ID"));
    		map.put("period", rs.getString("PERIOD"));
    		map.put("caveNum", rs.getDouble("CAVE_NUM"));
    		map.put("planProdAmt", rs.getDouble("PLAN_PROD_AMT"));
    		map.put("actProdAmt", rs.getDouble("ACT_PROD_AMT"));
    		map.put("goodProdAmt", rs.getDouble("GOOD_PROD_AMT"));
    		map.put("badProdAmt", rs.getDouble("BAD_PROD_AMT"));
    		map.put("effRate", rs.getString("EFF_RATE"));
    		
    		list.add(map);
    	}
    	
    	return list;
    }
    
    @Override
    public boolean isExceedProdAmt(long pcmOid) {
    	String sql = "SELECT SUM(PD.ACT_PROD_AMT), PM.PROD_AMT " +
    			"FROM T_PC_DETAIL PD " +
    			"INNER JOIN T_PC_MAIN PM ON PD.PC_OID = PM.OID " +
    			"WHERE PD.PC_OID = ?";
    	
    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, pcmOid);
    	double totalActAmt = 0;
    	double prodAmt = 0;
    	while (rs.next()) {
    		totalActAmt = rs.getDouble(1);
    		prodAmt = FilterUtil.filterString2Double(rs.getString(2));
    	}
    	if (totalActAmt > prodAmt) {
    		return true;
    	}
    	return false;
    }
    
    @Override
    public ListResult<ProductControlDetailDto> findPCDetailPaging(
    		int pageNumber, int pageSize, long pcmOid) {
    	String hql = "from ProductControlDetail cd where cd.pcOid = ? order by cd.produceDate desc";
    	
    	ListResult<ProductControlDetailDto> result = new ListResult<>();
		IPaging paging = this.queryByPage(pageNumber, pageSize, new Object[]{pcmOid}, hql);
		if (paging != null) {
			List<ProductControlDetail> elements = paging.getThisPageElements();
			List<ProductControlDetailDto> dtos = new ArrayList<>();
			for (ProductControlDetail detail : elements) {
				dtos.add(FilterUtil.convertObjectClass(detail, ProductControlDetailDto.class));
			}
			
			result.setRows(dtos);
			result.setResults(String.valueOf(this.getTotalCount(hql, new Object[]{pcmOid})));
		}
    	
    	return result;
    }
    
    
	@Override
    public JSONObject getNewlyWeekPlanTable(int year, int month, int week) {
    	JSONObject machineArr = new JSONObject();
    	// --- 获取指定年月周的开始日期和结束日期
    	if (month < 1 || month > 12) {
    		return machineArr;
    	}
    	if (week < 1 || week > 5) {
    		return machineArr;
    	}
    	
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month - 1);
    	
    	int dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int endDate = week * 7;
		int startDate = endDate - 6;
		if (startDate > dayOfMonth) {
			return machineArr;
		}
		endDate = endDate > dayOfMonth ? dayOfMonth : endDate;
		
		c.set(Calendar.DATE, startDate);
		String startD = (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
		c.set(Calendar.DATE, endDate);
		String endD = (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
		// ---
		
		String sql = "SELECT * "
				+ " FROM WEEK_PLAN_VIEW WPV"
				+ " WHERE WPV.PRODUCE_DATE >= ? AND WPV.PRODUCE_DATE <= ?"
				+ " ORDER BY WPV.MAC_NAME, WPV.PROD_ID, WPV.PRODUCE_DATE";
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, startD, endD);
		
		JSONObject machine, prod, amtArr, amtObj;
		while (rs.next()) {
			String machineName = rs.getString("MAC_NAME"); // 机台名称
			String pdDate = rs.getString("PRODUCE_DATE"); // 生产日期
			String prodId = rs.getString("PROD_ID"); // 产品编号
			String hole = rs.getString("SHAPE_ACT_PD"); // 穴数
			String period = rs.getString("FETCH_ACT_NUM"); // 周期
			String prodAmt = rs.getString("PROD_AMT"); // 生产数
			String planAmt = rs.getString("PLAN_AMT"); // 当天计划生产数总量
			String actAmt = rs.getString("SUM_ACT_AMT"); // 当天实际生产数总量
			String goodsAmt = rs.getString("SUM_GOOD_AMT"); // 当天加工良品总数
			String badAmt = rs.getString("SUM_BAD_AMT"); // 当天不良品总数
			
			machine = (JSONObject) machineArr.get(machineName);
			if (machine != null) {
				prod = (JSONObject) machine.get(prodId);
				if (prod != null) {
					amtArr = (JSONObject) prod.get("PROD_TIME");
					if (amtArr == null) {
						amtArr = new JSONObject();
					}
				} else {
					prod = new JSONObject();
					amtArr = new JSONObject();
				}
			} else {
				machine = new JSONObject();
				prod = new JSONObject();
				amtArr = new JSONObject();
			}
			amtObj = new JSONObject();
			amtObj.put("planAmt", planAmt); // 计划生产数
			amtObj.put("actAmt", actAmt); // 实际生产数
			amtObj.put("goodAmt", goodsAmt); // 良品数
			amtObj.put("badAmt", badAmt); // 不良品数
			amtArr.put(pdDate, amtObj);
			
			prod.put("hole", hole); // 穴数
			prod.put("period", period); // 周期
			prod.put("prodAmt", prodAmt); // 生产数
			prod.put("PROD_TIME", amtArr); // 生产时间
			machine.put(prodId, prod);
			machineArr.put(machineName, machine);
		}
		
    	return machineArr;
    }
    
}
