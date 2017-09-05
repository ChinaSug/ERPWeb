package com.abs.ps.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.ps.dao.StockInfoDao;
import com.abs.ps.domain.CheckPointDetail;
import com.abs.ps.domain.CheckPointMain;
import com.abs.ps.domain.StockInfo;
import com.abs.ps.domain.Warehouse;
import com.abs.ps.util.Constants;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.CheckPointDetailDto;
import com.abs.ps.web.dto.CheckPointMainDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.StockInfoDto;
import com.abs.ps.web.dto.StockSearchDto;
import com.abs.ps.web.dto.StockSumDto;

public class StockInfoDaoImpl extends AbsDaoSupport implements StockInfoDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
	
	
	public IPaging findStocksByPaging(int pageNumber, int pageSize, StockInfoDto criteria) {
		StringBuffer sql = new StringBuffer();
		List<Object> valueList = new ArrayList<>();
		List<StockInfoDto> result = new ArrayList<>();
		
		sql.append(" SELECT ")
		.append(" TST.NAME, TW.NAME, TI.ITEM_CODE, TI.ITEM_NAME, TBD.PROD_ID, TBD.NAME, TS.SUPP_NAME, TC.NAME, TIT.NAME, TI.TYPE_OID ")
		.append(" , TSI.OID, TSI.STOCK_NUM, TSI.STOCK_TYPE_OID, TSI.WAREHOUSE_OID, TSI.STOCK_PERSON," +
				" TSI.STOCK_DATE, TSI.ITEM_OID, TSI.STOCK_AMT, TSI.UNIT_PRICE, TSI.TOTAL_PRICE," +
				" TSI.CUSTOMER_OID, TSI.REMARK, TSI.STATUS, TSI.CREATE_BY, TSI.CREATE_DATE," +
				" TSI.LAST_MODIFY_BY, TSI.LAST_MODIFY_DATE, TSI.STOCK_ITEM_TYPE, TSI.BOM_DETAIL_OID ")
		.append(" FROM T_STOCK_INFO TSI ")
		.append(" LEFT JOIN T_STOCK_TYPE TST ON TSI.STOCK_TYPE_OID = TST.OID ")
		.append(" LEFT JOIN T_WAREHOUSE TW ON TSI.WAREHOUSE_OID = TW.OID ")
		.append(" LEFT JOIN T_ITEM TI ON TSI.ITEM_OID = TI.OID ")
		.append(" LEFT JOIN T_ITEM_TYPE TIT ON TI.TYPE_OID = TIT.OID ")
		.append(" LEFT JOIN T_SUPPLIER TS ON TI.SUPPLIER_OID = TS.OID ")
		.append(" LEFT JOIN T_CUSTOMER TC ON TSI.CUSTOMER_OID = TC.OID ")
		.append(" LEFT JOIN T_BOM_DETAIL TBD ON TSI.BOM_DETAIL_OID = TBD.OID ")
		.append(" WHERE 1=1 ");
		
		if (criteria != null) {
			if (!StringHelper.isEmpty(criteria.getStockInfoType())) {
				sql.append(" AND TST.DIM = ? ");
				valueList.add(criteria.getStockInfoType());
			}
			if (!StringHelper.isEmpty(criteria.getStockNum())) {
				sql.append(" AND (TSI.STOCK_NUM LIKE ? OR TI.ITEM_CODE LIKE ? OR TI.ITEM_NAME LIKE ? OR TBD.PROD_ID LIKE ?)");
				valueList.add("%"+criteria.getStockNum()+"%");
				valueList.add("%"+criteria.getStockNum()+"%");
				valueList.add("%"+criteria.getStockNum()+"%");
				valueList.add("%"+criteria.getStockNum()+"%");
			}
			if (!StringHelper.isEmpty(criteria.getWarehouseOid())) {
				sql.append(" AND TSI.WAREHOUSE_OID = ? ");
				valueList.add(Long.parseLong(criteria.getWarehouseOid()));
			}
			if (!StringHelper.isEmpty(criteria.getStockTypeOid())) {
				sql.append(" AND TST.OID = ? ");
				valueList.add(criteria.getStockTypeOid());
			}
			if (!StringHelper.isEmpty(criteria.getStockDate())) {
				sql.append(" AND TSI.STOCK_DATE = ? ");
				valueList.add(criteria.getStockDate());
			}
		}
		sql.append(" ORDER BY TSI.OID DESC ");
		
		int total = this.getTotalCount(sql.toString(), valueList.toArray(), jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), valueList.toArray(), pageNumber, pageSize, jdbcTemplate);

		while(rs.next()) {
			StockInfoDto dto = FilterUtil.parseOneSqlRowSetToObject(rs, StockInfoDto.class);
			dto.setStockTypeName(rs.getString(1));
			dto.setWarehouseName(rs.getString(2));
			// 若库存物品类型为产品1，则设置itemCode和itemName为产品的，相反则设置物料
			if ("1".equals(dto.getStockItemType())) { 
				dto.setItemCode(rs.getString(5));
				dto.setItemName(rs.getString(6));
				dto.setStockItemTypeName("产品");
			} else if ("2".equals(dto.getStockItemType())) {
				dto.setItemCode(rs.getString(3));
				dto.setItemName(rs.getString(4));
				dto.setStockItemTypeName(rs.getString(9));
			}
			dto.setSupplierName(rs.getString(7));
			dto.setCustomerName(rs.getString(8));
			dto.setItemTypeOid(rs.getString(10));
			
			result.add(dto);
		}
		
		PagingImpl page = new PagingImpl(result, total, pageNumber, pageSize);
		return page;
	}
	
	public IPaging findCheckPointByPaging(int pageNumber, int pageSize, CheckPointMainDto criteria) {
		IPaging page = null;
		List<CheckPointMainDto> result = new ArrayList<CheckPointMainDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TCM.OID,TCM.CHECK_NUM,TCM.PEROID_TYPE,TCM.PEROID_YEAR,TCM.PEROID_TIME,TCM.CHECK_PERSON,TCM.WAREHOUSE_OID,TW.NAME,TCM.STATUS,TCM.CREATE_BY,TCM.CREATE_DATE,TCM.REMARK");
		sql.append(" FROM T_ST_CHECK_MAIN TCM, T_WAREHOUSE TW");
		sql.append(" WHERE TCM.WAREHOUSE_OID=TW.OID ");
		
		List<Object> list = new ArrayList<Object>();
		
		if (!StringHelper.isEmpty(criteria.getCheckNum())) {
			sql.append(" AND TCM.CHECK_NUM=? ");
			list.add(criteria.getCheckNum());
		}
		
		if (!StringHelper.isEmpty(criteria.getPeroidYear())) {
			sql.append(" AND TCM.PEROID_YEAR=? ");
			list.add(criteria.getPeroidYear());
		}
		
		if (!StringHelper.isEmpty(criteria.getPeroidTime())) {
			sql.append(" AND TCM.PEROID_TIME=? ");
			list.add(criteria.getPeroidTime());
		}
		if (!StringHelper.isEmpty(criteria.getCheckPerson())) {
			sql.append(" AND TCM.CHECK_PERSON LIKE ? ");
			list.add("%"+criteria.getCheckPerson()+"%");
		}
		if (!StringHelper.isEmpty(criteria.getWarehouseOid())) {
			sql.append(" AND TCM.WAREHOUSE_OID=? ");
			list.add(Long.parseLong(criteria.getWarehouseOid()));
		}
		if (!StringHelper.isEmpty(criteria.getStatus())) {
			sql.append(" AND TCM.STATUS=? ");
			list.add(Long.parseLong(criteria.getStatus()));
		}
		sql.append(" ORDER BY TCM.CREATE_DATE DESC ");
		
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);
		while(rs.next()) {
			CheckPointMainDto dto = new CheckPointMainDto();
			dto.setOid(rs.getString(1));
			dto.setCheckNum(rs.getString(2));
			dto.setPeroidType(rs.getString(3));
			dto.setPeroidYear(rs.getString(4));
			dto.setPeroidTime(rs.getString(5));
			dto.setCheckPerson(rs.getString(6));
			dto.setWarehouseOid(rs.getString(7));
			dto.setWarehouseName(rs.getString(8));
			dto.setStatus(rs.getString(9));
			dto.setCreateBy(rs.getString(10));
			dto.setCreateDate(DateHelper.convert2String(rs.getDate(11), DateHelper.DATETIME_FORMATE));
			dto.setRemark(rs.getString(12));

			result.add(dto);
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		
		return page;
		
	}
	
	public boolean isDuplicateCheckPoint(CheckPointMainDto dto) {
		String hql = "from CheckPointMain d where d.peroidTime = ? and d.warehouseOid = ?";
		
		List<?> results = getHibernateTemplate().find(hql, dto.getPeroidTime(), Long.parseLong(dto.getWarehouseOid()));
    	if (results != null && results.size() > 0) {
    		return true;
    	}
    	return false;
	}
	
	
	public List<StockSumDto> findStockInfos(StockSumDto criteria) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TSI.ITEM_OID,SUM(IFNULL(TSI.STOCK_AMT,0)) FROM T_STOCK_INFO TSI, T_STOCK_TYPE TST WHERE TSI.STOCK_TYPE_OID=TST.OID ");
		
		List<Object> list = new ArrayList<Object>();
		
		if (!StringHelper.isEmpty(criteria.getDimension())) {
			sql.append(" AND TST.DIM=? ");
			list.add(criteria.getDimension());
		}
		
		if (!StringHelper.isEmpty(criteria.getItemOid())) {
			sql.append(" AND TSI.ITEM_OID=? ");
			list.add(criteria.getItemOid());
		}
		
		if (!StringHelper.isEmpty(criteria.getWhsOid())) {
			sql.append(" AND TSI.WAREHOUSE_OID=? ");
			list.add(criteria.getWhsOid());
		}
		
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		
		sql.append(" AND TSI.STOCK_ITEM_TYPE = 2 "); // 表示获取物料的库存信息
		sql.append(" GROUP BY TSI.ITEM_OID ");

		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), params);
		List<StockSumDto> dtos = new ArrayList<StockSumDto>();
		while(rs.next()) {
			StockSumDto dto = new StockSumDto();
			dto.setItemOid(rs.getString(1));
			dto.setSumAmt(rs.getDouble(2));
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public List<CheckPointDetailDto> findStockCheckPointDetailInfos(Long cpMainOid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CD.OID, CD.CP_MAIN_OID,TI.ITEM_CODE,TI.ITEM_NAME,TIT.NAME,TI.MODEL,TI.COLOR,TI.SPEC,TI.UNIT,TI.SAFE_AMT,CD.CUR_STOCK_AMT,CD.ACT_STOCK_AMT,CD.REMARK,TI.OID ");
		sql.append(" FROM T_ST_CHECK_DETAIL CD, T_ITEM TI,T_ITEM_TYPE TIT ");
		sql.append(" WHERE CD.ITEM_OID=TI.OID AND TI.TYPE_OID=TIT.OID AND CD.CP_MAIN_OID = ? ");
		sql.append(" AND CD.STOCK_ITEM_TYPE = ? ");
		sql.append(" ORDER BY TIT.NAME,TI.ITEM_NAME ");
		
		Object[] params = new Object[2];
		params[0] = cpMainOid;
		params[1] = Constants.STOCK_ITEM_TYPE_MATERIAL;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), params);
		List<CheckPointDetailDto> dtos = new ArrayList<CheckPointDetailDto>();
		while(rs.next()) {
			CheckPointDetailDto dto = new CheckPointDetailDto();
			dto.setOid(rs.getString(1));
			dto.setCpMainOid(rs.getString(2));
			dto.setItemCode(rs.getString(3));
			dto.setItemName(rs.getString(4));
			dto.setItemTypeName(rs.getString(5));
			dto.setModel(rs.getString(6));
			dto.setColor(rs.getString(7));
			dto.setSpec(rs.getString(8));
			dto.setUnit(rs.getString(9));
			dto.setSafeAmt(rs.getString(10));
			dto.setCurrentStockAmt(rs.getString(11));
			dto.setActualStockAmt(rs.getString(12));
			dto.setRemark(rs.getString(13));
			dto.setItemOid(rs.getString(14));
			dtos.add(dto);
		}
		return dtos;
	}
	
	
	public IPaging searchCheckPointByPaging(int pageNumber, int pageSize, StockSearchDto criteria) {
		IPaging page = null;
		List<StockSearchDto> result = new ArrayList<StockSearchDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SV.ITEM_OID,SV.ITEM_CODE,SV.ITEM_NAME,SV.TYPE_NAME,SV.MODEL,SV.COLOR,SV.SPEC,SV.UNIT,SV.SAFE_AMT,SV.SUPP_NAME,SV.WHS_OID,"
				+ "SV.WHS_NAME,SV.AMT FROM STOCK_VIEW SV WHERE 1=1 ");

		List<Object> list = new ArrayList<Object>();
		
		if (criteria != null) {
			if (!StringHelper.isEmpty(criteria.getWarehouseOid())) {
				sql.append(" AND SV.WHS_OID = ? ");
				list.add(criteria.getWarehouseOid());
			}
			
			if (!StringHelper.isEmpty(criteria.getItemName())) {
				sql.append(" AND (SV.ITEM_CODE LIKE ? OR SV.ITEM_NAME LIKE ?  OR SV.TYPE_NAME LIKE ? OR SV.SUPP_NAME LIKE ?) ");
				list.add("%" + criteria.getItemName() + "%");
				list.add("%" + criteria.getItemName() + "%");
				list.add("%" + criteria.getItemName() + "%");
				list.add("%" + criteria.getItemName() + "%");
			}
			if (!StringHelper.isEmpty(criteria.getItemCode())) {
				sql.append(" AND SV.SV.ITEM_CODE=? ");
				list.add(criteria.getItemCode());
			}
		}
		sql.append(" ORDER BY SV.ITEM_NAME ");
		
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);
		while(rs.next()) {
			StockSearchDto dto = new StockSearchDto();
			dto.setItemOid(rs.getString(1));
			dto.setItemCode(rs.getString(2));
			dto.setItemName(rs.getString(3));
			dto.setTypeName(rs.getString(4));
			dto.setModel(rs.getString(5));
			dto.setColor(rs.getString(6));
			dto.setSpec(rs.getString(7));
			dto.setUnit(rs.getString(8));
			dto.setSafeAmt(rs.getString(9));
			dto.setSupplierName(rs.getString(10));
			dto.setWarehouseOid(rs.getString(11));
			dto.setWarehouseName(rs.getString(12));
			dto.setStockAmt(rs.getString(13));

			result.add(dto);
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		return page;
	}
	
	public List<CheckPointDetail> findCPDetails(Long cpMainOid) {
		String hql = "from CheckPointDetail d where d.cpMainOid=?";
    	return this.getHibernateTemplate().find(hql, cpMainOid);
    }
	
	public boolean isUnderCheckPointByWHS(Long whsOid) {		
    	 List<CheckPointMain> cpMainList = this.getHibernateTemplate().find("from CheckPointMain d where d.status='0' and d.warehouseOid=?", whsOid);
    	 if (cpMainList != null && cpMainList.size() > 0) {
    		return true; 
    	 }
    	 return false; 
    }
	
	@Override
	public ListResult<JSONObject> findStockProdView(int pageNum, int pageSize, Map<String, String> searchMap) {
		List<JSONObject> list = new ArrayList<>();
		List<Object> valueList = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT SPV.BOM_DETAIL_OID, SPV.PROD_ID, SPV.PROD_NAME, SPV.AMT, SPV.WH_NAME, SPV.BOM_NUM, SPV.SAFE_AMT");
		sql.append(" FROM STOCK_PROD_VIEW SPV ");
		sql.append(" WHERE 1=1 ");
		
		if (searchMap != null) {
			valueList = new ArrayList<>();
			
			String whOid = searchMap.get("warehouse_oid");
			if (!StringHelper.isEmpty(whOid)) {
				sql.append(" AND (SPV.PIV_WH = ? OR SPV.POV_WH = ?) ");
				valueList.add(whOid);
				valueList.add(whOid);
			}
			
			String prodName = searchMap.get("search_prodName");
			if (!StringHelper.isEmpty(prodName)) {
				sql.append(" AND SPV.PROD_NAME LIKE ? ");
				valueList.add("%" + prodName + "%");
			}
		}
		sql.append(" ORDER BY SPV.WH_NAME ");
		
		int totalCount = getTotalCount(sql.toString(), valueList.toArray(), jdbcTemplate);
		SqlRowSet rs = queryByPage4SQL(sql.toString(), valueList.toArray(), pageNum, pageSize, jdbcTemplate);
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("bomDetailOid", rs.getLong(1));
			jsonObj.put("prodId", rs.getString(2));
			jsonObj.put("prodName", rs.getString(3));
			jsonObj.put("stockAmt", rs.getString(4));
			jsonObj.put("warehouseName", rs.getString(5));
			jsonObj.put("bomNum", rs.getString(6));
			jsonObj.put("safeAmt", rs.getString(7));
			
			list.add(jsonObj);
		}
		
		ListResult<JSONObject> result = new ListResult<>();
		result.setResults(String.valueOf(totalCount));
		result.setRows(list);
		result.setHasError("");
		
		return result;
	}
	
	@Override
	public void deleteCheckPointDetail(String cpMainIds) {
		if (!StringHelper.isEmpty(cpMainIds)) {
			Object[] split = cpMainIds.split(",");
			String re = cpMainIds.replaceAll("\\d+", "?");
			String sql = "DELETE FROM T_ST_CHECK_DETAIL WHERE CP_MAIN_OID IN (" + re + ")";
			jdbcTemplate.update(sql.toString(), split);
		}
	}
	
	@Override
	public List<CheckPointDetailDto> getStockProdView(String whOid) {
		String sql = " SELECT SPV.BOM_DETAIL_OID, SPV.PROD_ID, SPV.PROD_NAME, SPV.AMT, SPV.WH_NAME, SPV.BOM_NUM "
				+ " FROM STOCK_PROD_VIEW SPV "
				+ " WHERE SPV.PIV_WH = ? OR SPV.POV_WH = ? ";
		
		List<CheckPointDetailDto> list = new ArrayList<>();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, whOid, whOid);
		while (rs.next()) {
			CheckPointDetailDto dto = new CheckPointDetailDto();
			dto.setBomDetailOid(rs.getString("BOM_DETAIL_OID"));
			dto.setProdId(rs.getString("PROD_ID"));
			dto.setProdName(rs.getString("PROD_NAME"));
			dto.setBomNum(rs.getString("BOM_NUM"));
			dto.setCurrentStockAmt(rs.getString("AMT"));
			dto.setStockItemType(Constants.STOCK_ITEM_TYPE_PROD);
			
			list.add(dto);
		}
		return list;
	}
	
	@Override
	public List<CheckPointDetailDto> getCheckPointProd(String cpMainOid) {
		String sql = " SELECT TCD.OID, TCD.CUR_STOCK_AMT, TCD.ACT_STOCK_AMT, TCD.REMARK, SPV.BOM_DETAIL_OID, SPV.PROD_ID, SPV.PROD_NAME, SPV.AMT, SPV.WH_NAME, SPV.BOM_NUM, SPV.SAFE_AMT "
					+ " FROM STOCK_PROD_VIEW SPV "
					+ " INNER JOIN T_ST_CHECK_MAIN TCM ON TCM.OID = ? AND (TCM.WAREHOUSE_OID = SPV.PIV_WH OR TCM.WAREHOUSE_OID = SPV.POV_WH) "
					+ " INNER JOIN T_ST_CHECK_DETAIL TCD ON TCD.CP_MAIN_OID = TCM.OID AND TCD.BOM_DETAIL_OID = SPV.BOM_DETAIL_OID ";
		
		List<CheckPointDetailDto> list = new ArrayList<>();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, cpMainOid);
		while (rs.next()) {
			CheckPointDetailDto dto = new CheckPointDetailDto();
			dto.setOid(rs.getString(1));
			dto.setActualStockAmt(rs.getString("ACT_STOCK_AMT"));
			dto.setCurrentStockAmt(rs.getString("CUR_STOCK_AMT"));
			dto.setBomDetailOid(rs.getString("BOM_DETAIL_OID"));
			dto.setProdId(rs.getString("PROD_ID"));
			dto.setProdName(rs.getString("PROD_NAME"));
			dto.setBomNum(rs.getString("BOM_NUM"));
			dto.setRemark(rs.getString("REMARK"));
			dto.setStockItemType(Constants.STOCK_ITEM_TYPE_PROD);
			dto.setSafeAmt(rs.getString("SAFE_AMT"));
			
			list.add(dto);
		}
		return list;
	}
	
	@Override
	public boolean isCheckStockWarehouse(String warehouseOid) {
		String hql = "from CheckPointMain where status = 0 and warehouseOid = ?";
		List<?> find = getHibernateTemplate().find(hql, FilterUtil.filterString2Long(warehouseOid));
		if (find != null && find.size() > 0) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public List<StockInfoDto> getCheckingStock(String stockIds) {
		if (stockIds == null) {
			return new ArrayList<>();
		}
		
		String hql = " select si, wh from StockInfo si, CheckPointMain cm, Warehouse wh "
				+ " where si.warehouseOid = cm.warehouseOid and si.warehouseOid = wh.oid "
				+ " and cm.status = 0"
				+ " and si.oid in (" + stockIds.replaceAll("\\d+", "?") + ")";
		
		String[] split = stockIds.split(",");
		Object[] oidsArr = new Long[split.length];
		for (int i = 0; i < split.length; i++) {
			oidsArr[i] = Long.parseLong(split[i].trim());
		}
		
		List<StockInfoDto> list = new ArrayList<>();
		List<?> find = getHibernateTemplate().find(hql, oidsArr);
		if (find != null) {
			for (Object obj : find) {
				Object[] objs = (Object[]) obj;
				StockInfo si = (StockInfo) objs[0];
				Warehouse wh = (Warehouse) objs[1];
				
				StockInfoDto sid = FilterUtil.convertObjectClass(si, StockInfoDto.class);
				sid.setWarehouseName(wh.getName());
				list.add(sid);
			}
		}
		
		return list;
	}
	
	@Override
	public List<StockSearchDto> getStockView(Map<String, String> paramMap) {
		StringBuffer sql = new StringBuffer(); 
		sql.append(" SELECT ST.ITEM_OID, ST.ITEM_CODE, ST.ITEM_NAME, ST.TYPE_NAME, ST.MODEL, ST.COLOR, ST.SPEC, ST.UNIT, ST.SAFE_AMT, ST.SUPP_NAME, ST.WHS_OID, ST.WHS_NAME, ST.AMT")
		.append(" FROM STOCK_VIEW ST WHERE 1=1 ");
		
		List<Object> values = new ArrayList<>();
		if (paramMap != null) {
			if (!StringHelper.isEmpty(paramMap.get("LOW_STOCK"))) {
				sql.append(" AND ST.SAFE_AMT > ST.AMT ");
			}
		}
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), values.toArray());
		List<StockSearchDto> dtoList = new ArrayList<>();
		StockSearchDto d = null;
		while (rs.next()) {
			d = new StockSearchDto();
			d.setItemOid(rs.getString("ITEM_OID"));
			d.setItemCode(rs.getString("ITEM_CODE"));
			d.setItemName(rs.getString("ITEM_NAME"));
			d.setTypeName(rs.getString("TYPE_NAME"));
			d.setModel(rs.getString("MODEL"));
			d.setColor(rs.getString("COLOR"));
			d.setSpec(rs.getString("SPEC"));
			d.setUnit(rs.getString("UNIT"));
			d.setSafeAmt(rs.getString("SAFE_AMT"));
			d.setSupplierName(rs.getString("SUPP_NAME"));
			d.setWarehouseOid(rs.getString("WHS_OID"));
			d.setWarehouseName(rs.getString("WHS_NAME"));
			d.setStockAmt(rs.getString("AMT"));
			
			dtoList.add(d);
		}
		
		return dtoList;
	}
	
}
