package com.abs.ps.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.abs.core.dao.AbsDaoSupport;
import com.abs.core.paging.IPaging;
import com.abs.core.paging.PagingImpl;
import com.abs.core.util.StringHelper;
import com.abs.ps.dao.BaseInfoDao;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.domain.BomMain;
import com.abs.ps.domain.Department;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.ItemType;
import com.abs.ps.domain.Machine;
import com.abs.ps.domain.Mould;
import com.abs.ps.domain.ScheduleMain;
import com.abs.ps.domain.StockType;
import com.abs.ps.domain.Supplier;
import com.abs.ps.domain.Warehouse;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.web.dto.BomDetailDto;
import com.abs.ps.web.dto.DamageInfoDto;
import com.abs.ps.web.dto.ItemDto;
import com.abs.ps.web.dto.SupplierDto;
 
public class BaseInfoDaoImpl extends AbsDaoSupport implements BaseInfoDao{
	private JdbcTemplate jdbcTemplate;  
	  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 

    public List<Supplier> findSuppliers(String supplierName) {
    	if (!StringHelper.isEmpty(supplierName)) {
    		return this.getHibernateTemplate().find("from Supplier d where d.supplierName like ? order by d.supplierName","%" + supplierName + "%");
    	} else {
    		return this.getHibernateTemplate().find("from Supplier d order by d.supplierName");
    	}
    }
    
    public List<ItemInfo> findItems(String name, Long typeOid) {
    	StringBuffer hql = new StringBuffer();
    	List<Object> valueList = new LinkedList<>();
    	if (!StringHelper.isEmpty(name) && typeOid < 1) {
    		hql.append("from ItemInfo d where d.name like ? and d.typeOid=? ");
    		valueList.add("%" + name + "%");
    		valueList.add(typeOid);
    	} else {
    		if (typeOid < 1) {
    			hql.append("from ItemInfo d ");
    		} else {
    			hql.append("from ItemInfo d where d.typeOid=? ");
    			valueList.add(typeOid);
    		}
    	}
    	hql.append(" order by d.oid desc, d.name");
    	
    	return getHibernateTemplate().find(hql.toString(), valueList.toArray());
    }
   
    public List<ItemType> findItemType(String name) {
    	if (!StringHelper.isEmpty(name)) {
    		return this.getHibernateTemplate().find("from ItemType d where d.name like ? order by d.name","%" + name + "%");
    	} else {
    		return this.getHibernateTemplate().find("from ItemType d order by d.name");
    	}
    }
    
    public List<Department> findDepartments(String name) {
    	StringBuffer hql = new StringBuffer();
    	List<Object> valueList = new LinkedList<>();
    	if (!StringHelper.isEmpty(name)) {
    		hql.append(" from Department d where d.departName like ? ");
    		valueList.add("%" + name + "%");
    	} else {
    		hql.append(" from Department d ");
    	}
    	hql.append(" order by d.oid desc ");
    	return getHibernateTemplate().find(hql.toString(), valueList.toArray());
    }
    
    public List<Machine> findMachines(String name) {
    	if (!StringHelper.isEmpty(name)) {
    		return this.getHibernateTemplate().find("from Machine d where d.name like ? order by d.name","%" + name + "%");
    	} else {
    		return this.getHibernateTemplate().find("from Machine d order by d.name");
    	}
    }
    
    public List<Mould> findMoulds(String name) {
    	if (!StringHelper.isEmpty(name)) {
    		return this.getHibernateTemplate().find("from Mould d where d.name like ? order by d.name","%" + name + "%");
    	} else {
    		return this.getHibernateTemplate().find("from Mould d order by d.name");
    	}
    }
    
    public List<StockType> findStockTypes(String dimension, String name) {
    	if (!StringHelper.isEmpty(name)) {
    		return this.getHibernateTemplate().find("from StockType d where d.dimension=? and d.name like ? order by d.name",dimension, "%" + name + "%");
    	} else {
    		return this.getHibernateTemplate().find("from StockType d where d.dimension=? order by d.name", dimension);
    	}
    }
    
    public List<Warehouse> findWarehouses(String name) {
    	if (!StringHelper.isEmpty(name)) {
    		return this.getHibernateTemplate().find("from Warehouse d where d.name like ? order by d.name","%" + name + "%");
    	} else {
    		return this.getHibernateTemplate().find("from Warehouse d order by d.name");
    	}
    }
    
    
    public IPaging findSupplierWithPaging(int pageNumber, int pageSize, SupplierDto supplierDto) {
		Object[] values = null; 
		String hql = "from Supplier d ";
		if (!StringHelper.isEmpty(supplierDto.getSupplierName())) {
			hql = "from Supplier d where (d.supplierName like ? or d.supplierId like ? or d.type like ? )";
			 values = new Object[3];
			 values[0] = "%" + supplierDto.getSupplierName() + "%";
			 values[1] = "%" + supplierDto.getSupplierName() + "%";
			 values[2] = "%" + supplierDto.getSupplierName() + "%";
		}
		hql += " order by d.createDate desc";
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	
	}
    
    public IPaging findWarehouseWithPaging(int pageNumber, int pageSize, String name) {
		Object[] values = null; 
		String hql = "from Warehouse d ";
		if (!StringHelper.isEmpty(name)) {
			hql = "from Warehouse d where d.name like ? ";
			 values = new Object[1];
			 values[0] = "%" +name + "%";
		}
		hql += " order by d.oid desc";
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	
	}
    
    public IPaging findMachineWithPaging(int pageNumber, int pageSize, String name) {
		Object[] values = null; 
		String hql = "from Machine d ";
		if (!StringHelper.isEmpty(name)) {
			hql = "from Machine d where d.name like ? ";
			 values = new Object[1];
			 values[0] = "%" + name + "%";
		}
		hql += " order by d.oid desc";
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	
	}
    
    public IPaging findMouldWithPaging(int pageNumber, int pageSize, String name) {
		Object[] values = null; 
		String hql = "from Mould d ";
		if (!StringHelper.isEmpty(name)) {
			hql = "from Mould d where d.name like ? ";
			 values = new Object[1];
			 values[0] = "%" + name + "%";
		}
		hql += " order by d.oid desc";
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	
	}
    
    public IPaging findItemTypeWithPaging(int pageNumber, int pageSize, String name) {
		Object[] values = null; 
		String hql = "from ItemType d ";
		if (!StringHelper.isEmpty(name)) {
			hql = "from ItemType d where d.name like ? ";
			 values = new Object[1];
			 values[0] = "%" + name + "%";
		}
		hql += " order by d.oid desc";
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	
	}
    
    public IPaging findStockTypeWithPaging(int pageNumber, int pageSize, String name) {
		Object[] values = null; 
		String hql = "from StockType d ";
		if (!StringHelper.isEmpty(name)) {
			hql = "from StockType d where d.name like ? ";
			 values = new Object[1];
			 values[0] = "%" + name + "%";
		}
		hql += " order by d.dimension";
		
		return this.queryByPage(pageNumber, pageSize, values, hql);	
	}
    
    public Supplier getSupplierById(Long id) {
		List<Supplier> centerList = this.getHibernateTemplate().find("from Supplier d where d.oid=?",id);
		if (centerList != null && centerList.size() > 0) {
			return centerList.get(0);
		}
		return null;
	}

    
    
    public IPaging findItemsByPaging(int pageNumber, int pageSize, ItemDto criteria) {
		IPaging page = null;
		List<ItemDto> result = new ArrayList<ItemDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TI.OID,TI.ITEM_CODE,TI.ITEM_NAME,TI.TYPE_OID,TIT.NAME, TI.MODEL,TI.COLOR,TI.UNIT, ");
		sql.append(" TI.UNIT_PRICE,TI.SAFE_AMT,TI.SUPPLIER_OID,");
		sql.append(" (SELECT S.SUPP_NAME FROM T_SUPPLIER S WHERE S.OID = TI.SUPPLIER_OID) AS SUPP_NAME ");
		sql.append(",TI.CUSTOMER_OID,TI.STATUS,TI.CREATE_BY,TI.CREATE_DATE,TI.LAST_MODIFY_BY,TI.LAST_MODIFY_DATE,TI.SPEC,TI.DAM_PRICE,TI.MAT_PRICE,TI.PIE_PRICE");
		sql.append(" FROM T_ITEM TI, T_ITEM_TYPE TIT WHERE TI.TYPE_OID=TIT.OID");

		List<Object> list = new ArrayList<Object>();
		
		if (!StringHelper.isEmpty(criteria.getName())) {
			sql.append(" AND (TI.ITEM_NAME LIKE ? OR TI.ITEM_CODE LIKE ? OR TI.MODEL LIKE ? OR TI.SPEC LIKE ? ) ");
			list.add("%"+criteria.getName()+"%");
			list.add("%"+criteria.getName()+"%");
			list.add("%"+criteria.getName()+"%");
			list.add("%"+criteria.getName()+"%");
		}
		if(!StringHelper.isEmpty(criteria.getTypeOid())){
			sql.append(" AND TI.TYPE_OID LIKE ?");
			list.add("%"+criteria.getTypeOid()+"%");
		}
		sql.append(" ORDER BY TI.CREATE_DATE DESC ");
		
		Object[] params = new Object[list.size()];
		for (int i =0, j = list.size(); i < j; i++) {
			params[i] = list.get(i);
		}
		
		int total = this.getTotalCount(sql.toString(), params, jdbcTemplate);
		SqlRowSet rs = this.queryByPage4SQL(sql.toString(), params, pageNumber, pageSize, jdbcTemplate);
		
		while(rs.next()) {
			ItemDto dto = new ItemDto();
			dto.setOid(rs.getString(1));
			dto.setCode(rs.getString(2));
			dto.setName(rs.getString(3));
			dto.setTypeOid(rs.getString(4));
			dto.setTypeName(rs.getString(5));
			dto.setModel(rs.getString(6));
			dto.setColor(rs.getString(7));
			dto.setUnit(rs.getString(8));
			dto.setUnitPrice(rs.getString(9));
			dto.setSafeAmt(rs.getString(10));
			dto.setSupplierOid(rs.getString(11));
			dto.setSupplierName(rs.getString(12));
			dto.setCustomerOid(rs.getString(13));
			dto.setStatus(rs.getString(14));
			dto.setCreateBy(rs.getString(15));
			dto.setCreateDate(DateHelper.convert2String(rs.getDate(16), DateHelper.DATETIME_FORMATE));
			dto.setLastModifyBy(rs.getString(17));
			dto.setLastModifyDate(DateHelper.convert2String(rs.getDate(18), DateHelper.DATETIME_FORMATE));
			dto.setSpec(rs.getString(19));
			dto.setDamPrice(rs.getString(20));
			dto.setMatPrice(rs.getString(21));
			dto.setPiePrice(rs.getString(22));
			boolean isDeletable = isDeletable(ScheduleMain.class, "itemOid", Long.parseLong(dto.getOid()));
			if (!isDeletable) {
				dto.setDisabled("true");
			}

			result.add(dto);
		}
		
		page = new PagingImpl(result, total, pageNumber, pageSize);
		
		return page;
		
	}

    
    public IPaging findDamageInfosByPaging(int pageNumber, int pageSize, DamageInfoDto criteria) {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT C.NAME, IT.NAME, BD.NAME, D.UNIT_PRICE, I.UNIT_PRICE, BD.UNIT_PRICE ") // 额外字段设置，需要保证顺序
    	.append(" , D.OID, D.DAM_NUM, D.ITEM_OID, D.CUST_OID, D.BAD_AMT, D.PIE_AMT, D.COST, D.UNIT_CONSUME, D.ITEM_UNIT_PRICE, D.ITEM_FEE, D.PIE_UNIT_PRICE, D.PIE_FEE, D.REG_SOURCE, D.DEPART_OID, D.`STATUS`, D.CREATE_BY, D.CREATE_DATE, D.LAST_MODIFY_BY, D.LAST_MODIFY_DATE, D.BOM_DETAIL_OID, D.DAMAGE_TYPE, D.REPORT_DATE ")
    	.append(" , I.ITEM_CODE, I.ITEM_NAME, I.TYPE_OID, I.MAT_PRICE, I.PIE_PRICE, I.DAM_PRICE ")
    	.append(" , BD.PROD_ID, BD.MATERIAL_UPC, BD.PIE_UPC, BD.DAMAGE_UPC ")
    	.append(" , DP.DEPART_NAME ")
    	.append(" FROM T_DAMAGE D ")
    	.append(" LEFT JOIN T_CUSTOMER C ON D.CUST_OID = C.OID ")
    	.append(" LEFT JOIN T_ITEM I ON D.ITEM_OID = I.OID ")
    	.append(" LEFT JOIN T_BOM_DETAIL BD ON D.BOM_DETAIL_OID = BD.OID ")
    	.append(" LEFT JOIN T_DEPART DP ON D.DEPART_OID = DP.OID ")
    	.append(" LEFT JOIN T_ITEM_TYPE IT ON IT.OID = I.TYPE_OID ")
    	.append(" WHERE 1=1 ");
    	
    	
    	List<Object> list = new LinkedList<>();
    	if (criteria != null) {
    		if (!StringHelper.isEmpty(criteria.getItemName())) {
    			sql.append(" AND D.DAM_NUM LIKE ? ");
    			list.add("%"+criteria.getItemName()+"%");
    		}
    		
    		if(!StringHelper.isEmpty(criteria.getReportDate())){
    			sql.append(" AND D.REPORT_DATE = ? ");
    			list.add(criteria.getReportDate());
    		}
    	}
    	
    	sql.append(" ORDER BY D.OID DESC ");
    	
    	SqlRowSet rs = queryByPage4SQL(sql.toString(), list.toArray(), pageNumber, pageSize, jdbcTemplate);
    	List<DamageInfoDto> result = new LinkedList<>();
    	double unitPrice = 0;
		double matPrice = 0; 
		double piePrice = 0;
		double damagePrice = 0;
    	while (rs.next()) {
    		DamageInfoDto d = FilterUtil.parseOneSqlRowSetToObject(rs, DamageInfoDto.class);
    		d.setNumber(rs.getString("DAM_NUM"));
    		d.setCustomerOid(rs.getString("CUST_OID"));
    		d.setCustomerName(rs.getString(1));
    		
    		String damageType = d.getDamageType();
    		if ("1".equals(damageType)) { // 报废产品
    			unitPrice = FilterUtil.filterObj2Double(rs.getDouble(6));
    			matPrice = FilterUtil.filterObj2Double(rs.getDouble("MATERIAL_UPC")); 
    			piePrice = FilterUtil.filterObj2Double(rs.getDouble("PIE_UPC"));
    			damagePrice = FilterUtil.filterObj2Double(rs.getDouble("DAMAGE_UPC"));
    			d.setItemCode(rs.getString("PROD_ID"));
    			d.setItemName(rs.getString(3));
    			d.setItemTypeName("产品");
    		} else if ("2".equals(damageType)) { // 报废物料
    			unitPrice = FilterUtil.filterObj2Double(rs.getDouble(5));
    			matPrice = FilterUtil.filterObj2Double(rs.getDouble("MAT_PRICE")); 
    			piePrice = FilterUtil.filterObj2Double(rs.getDouble("PIE_PRICE"));
    			damagePrice = FilterUtil.filterObj2Double(rs.getDouble("DAM_PRICE"));
    			d.setItemTypeName(rs.getString(2));
    		}
    		d.setUnitPrice("" + unitPrice);
    		d.setMatPrice("" + matPrice);
    		d.setPiePrice("" + piePrice);
    		d.setDamagePrice("" + damagePrice);
    		
    		String createDate = d.getCreateDate();
    		if (createDate != null) {
    			d.setCreateDate(createDate.substring(0, createDate.lastIndexOf(".0")));
    		}
    		String laseModifyDate = d.getLastModifyDate();
    		if (laseModifyDate != null) {
    			d.setLastModifyDate(laseModifyDate.substring(0, laseModifyDate.lastIndexOf(".0")));
    		}
    		result.add(d);
    	}
		
    	int total = getTotalCount(sql.toString(), list.toArray(), jdbcTemplate);
    	IPaging paging = new PagingImpl(result, total, pageNumber, pageSize);
    	return paging;
    	
	}

	@Override
	public Supplier getSupplierBysupplierId(String supplierId) {
		List<Supplier> centerList = this.getHibernateTemplate().find("from Supplier d where d.supplierId=?",supplierId);
		if (centerList != null && centerList.size() > 0) {
			return centerList.get(0);
		}
		return null;
	}

	@Override
	public ItemInfo getItemByCode(String Code) {
		List<ItemInfo> centerList = this.getHibernateTemplate().find("from ItemInfo d where d.code=?",Code);
		if (centerList != null && centerList.size() > 0) {
			return centerList.get(0);
		}
		return null;
	}

	@Override
	public List<ItemInfo> findItemsPrice(String name, Long oid) {
		StringBuffer hql = new StringBuffer("from ItemInfo d ");
		List<Object> valueList = new LinkedList<>();
		
		if (!StringHelper.isEmpty(name) && oid < 1) {
			hql.append(" where d.name like ? and d.oid=?");
			valueList.add("%" + name + "%");
			valueList.add(oid);
    	} else {
    		if (oid > 0) {
    			hql.append(" where d.oid=?");
    			valueList.add(oid);
    		}
    	}
		hql.append(" order by d.oid desc, d.name");
		
		return getHibernateTemplate().find(hql.toString(), valueList.toArray());
	}

	public <T> T getObject(Class<T> clazz, String[] field, Object[] value) {
		StringBuffer hql = new StringBuffer("from " + clazz.getSimpleName());
		
		if (field != null && value != null && field.length == value.length) {
			for (int i = 0, len = field.length; i < len; i++) {
				if (i == 0) {
					hql.append(" where ");
				} else if (i > 0) {
					hql.append(" and ");
				}
				hql.append(field[i] + " = ? ");
			}
		}
		List<?> find = getHibernateTemplate().find(hql.toString(), value);
		if (find != null && find.size() > 0) {
			return (T) find.get(0);
		}
		return null;
	}
	
	public List<BomDetailDto> getBomDetailByNoProduct() {
		List<BomDetailDto> dtoList = null;
		String hql = "select bd, bm from BomDetail bd, BomMain bm " +
				"where bd.bomOid = bm.oid and bm.status = 0 and bd.oid not in (" +
				" select pm.bomDetailOid from ProductControlMain pm" +
				") order by bd.oid desc";
		
		List<?> find = getHibernateTemplate().find(hql);
		if (find != null) {
			dtoList = new LinkedList<>();
			for (Object obj : find) {
				if (obj instanceof Object[]) {
					Object[] objArr = (Object[]) obj;
					BomDetail detail = (BomDetail) objArr[0];
					BomMain main = (BomMain) objArr[1];
					
					BomDetailDto detailDto = FilterUtil.convertObjectClass(detail, BomDetailDto.class);
					detailDto.setBomNum(main.getBomNum());
					dtoList.add(detailDto);
				}
			}
		}
		
		return dtoList;
	}
	
	@Override
	public List<BomDetailDto> getAllBomDetail() {
		List<BomDetailDto> dtoList = null;
		String hql = "select bd, bm from BomDetail bd, BomMain bm " +
				"where bd.bomOid = bm.oid and bm.status = 0 " +
				"order by bm.oid desc, bd.oid desc ";
		
		List<?> find = getHibernateTemplate().find(hql);
		if (find != null) {
			dtoList = new LinkedList<>();
			for (Object obj : find) {
				if (obj instanceof Object[]) {
					Object[] objArr = (Object[]) obj;
					BomDetail detail = (BomDetail) objArr[0];
					BomMain main = (BomMain) objArr[1];
					
					BomDetailDto detailDto = FilterUtil.convertObjectClass(detail, BomDetailDto.class);
					detailDto.setBomNum(main.getBomNum());
					dtoList.add(detailDto);
				}
			}
		}
		
		return dtoList;
	}
    
}
