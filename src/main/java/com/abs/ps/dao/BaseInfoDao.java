package com.abs.ps.dao;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.Department;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.ItemType;
import com.abs.ps.domain.Machine;
import com.abs.ps.domain.Mould;
import com.abs.ps.domain.StockType;
import com.abs.ps.domain.Supplier;
import com.abs.ps.domain.Warehouse;
import com.abs.ps.web.dto.BomDetailDto;
import com.abs.ps.web.dto.DamageInfoDto;
import com.abs.ps.web.dto.ItemDto;
import com.abs.ps.web.dto.SupplierDto;

public interface BaseInfoDao extends IDao{
	public Supplier getSupplierById(Long id);
	public Supplier getSupplierBysupplierId(String supplierId);
	public ItemInfo getItemByCode(String Code);
	public List<Supplier> findSuppliers(String supplierName);
	public IPaging findSupplierWithPaging(int pageNumber, int pageSize, SupplierDto supplierDto);
	public IPaging findItemsByPaging(int pageNumber, int pageSize, ItemDto criteria);
	public List<ItemType> findItemType(String name) ;
	
	public List<Machine> findMachines(String name);
	public List<Mould> findMoulds(String name);
	public List<StockType> findStockTypes(String dimension, String name);
	public List<Warehouse> findWarehouses(String name);
	public List<ItemInfo> findItems(String name, Long typeOid);
	public List<ItemInfo> findItemsPrice(String name,Long oid);
	
	public List<Department> findDepartments(String name);
	
	public IPaging findWarehouseWithPaging(int pageNumber, int pageSize, String name);
	public IPaging findMachineWithPaging(int pageNumber, int pageSize, String name);
	public IPaging findMouldWithPaging(int pageNumber, int pageSize, String name);
	public IPaging findItemTypeWithPaging(int pageNumber, int pageSize, String name);
	public IPaging findStockTypeWithPaging(int pageNumber, int pageSize, String name);
	public IPaging findDamageInfosByPaging(int pageNumber, int pageSize, DamageInfoDto criteria);
	
	/**
	 * 通过指定clazz类型，field要查询的属性，value对应field要的值来查找对象
	 * 
	 * @param clazz
	 * @param field
	 * @param value
	 * @return
	 */
	public <T> T getObject(Class<T> clazz, String[] field, Object[] value);
	
	/**
	 * 获取没有关联生产控制单的BOM副表列表，且BOM主表状态为1的
	 * 
	 * @return
	 */
	public List<BomDetailDto> getBomDetailByNoProduct();
	
	public List<BomDetailDto> getAllBomDetail();
}
