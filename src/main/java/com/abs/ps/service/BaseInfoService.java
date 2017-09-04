package com.abs.ps.service;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.BaseInfoDao;
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
import com.abs.ps.web.dto.ItemTypeDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.MachineDto;
import com.abs.ps.web.dto.MouldDto;
import com.abs.ps.web.dto.StockTypeDto;
import com.abs.ps.web.dto.SupplierDto;
import com.abs.ps.web.dto.WarehouseDto;

public interface BaseInfoService extends IService{
	public Supplier getSupplierById(Long id);
	public List<Supplier> findSuppliers(String supplierName);
	public Supplier getSupplierBysupplierId(String supplierId);
	public ItemInfo getItembyCode(String Code);
	public IPaging findSupplierWithPaging(int pageNumber, int pageSize, SupplierDto supplierDto);
	public IPaging findItemsByPaging(int pageNumber, int pageSize, ItemDto criteria);
	public List<ItemType> findItemType(String name);
	public List<Machine> findMachines(String name);
	public List<Mould> findMoulds(String name);
	public List<StockType> findStockTypes(String dimension, String name);
	public List<Warehouse> findWarehouses(String name);
	public List<ItemInfo> findItems(String name, Long typeOid);
	public List<ItemInfo> findItemsPrice(String name,Long oid);
	public List<Department> findDepartments(String name);
	
	public ListResult<WarehouseDto> findWarehouseWithPaging(int pageNumber, int pageSize, String name);
	public ListResult<MachineDto> findMachineWithPaging(int pageNumber, int pageSize, String name);
	public ListResult<MouldDto> findMouldWithPaging(int pageNumber, int pageSize, String name);
	public ListResult<ItemTypeDto> findItemTypeWithPaging(int pageNumber, int pageSize, String name);
	public ListResult<StockTypeDto> findStockTypeWithPaging(int pageNumber, int pageSize, String name);
	public ListResult<DamageInfoDto> findDamageInfosByPaging(int pageNumber, int pageSize, DamageInfoDto criteria);
	
	/**
	 * 通过指定clazz类型，field要查询的属性，value对应field要的值来查找对象
	 * 
	 * @param clazz
	 * @param field
	 * @param value
	 * @return
	 */
	public <T> T getObject(Class<T> clazz, String[] field, Object[] value);
	
	public List<BomDetailDto> getBomDetailByNoProduct();
	
	public List<BomDetailDto> getAllBomDetail();
}
