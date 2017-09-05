package com.abs.ps.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.core.util.StringHelper;
import com.abs.ps.dao.BaseInfoDao;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.domain.Customer;
import com.abs.ps.domain.DamageInfo;
import com.abs.ps.domain.Department;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.ItemType;
import com.abs.ps.domain.Machine;
import com.abs.ps.domain.Mould;
import com.abs.ps.domain.ProductControlMain;
import com.abs.ps.domain.StockInfo;
import com.abs.ps.domain.StockType;
import com.abs.ps.domain.Supplier;
import com.abs.ps.domain.Warehouse;
import com.abs.ps.service.AbstractService;
import com.abs.ps.service.BaseInfoService;
import com.abs.ps.util.FilterUtil;
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

public class BaseInfoServiceImpl extends AbstractService implements BaseInfoService{
	private BaseInfoDao baseInfoDao;
	
	public void setBaseInfoDao(BaseInfoDao baseInfoDao) {
		this.baseInfoDao = baseInfoDao;
		super.setIDao(baseInfoDao);
	}
	@Override
	public Supplier getSupplierById(Long id) {
		return baseInfoDao.getSupplierById(id);
	}
	@Override
	public List<Supplier> findSuppliers(String supplierName) {
		return baseInfoDao.findSuppliers(supplierName);
	}

	public IPaging findSupplierWithPaging(int pageNumber, int pageSize, SupplierDto supplierDto) {
		return baseInfoDao.findSupplierWithPaging(pageNumber, pageSize, supplierDto);
	}
	public IPaging findItemsByPaging(int pageNumber, int pageSize, ItemDto criteria) {
		return baseInfoDao.findItemsByPaging(pageNumber, pageSize, criteria);
	}
	
	public List<ItemType> findItemType(String name) {
		return baseInfoDao.findItemType(name);
	}
	
	public List<Machine> findMachines(String name) {
		return baseInfoDao.findMachines(name);
	}
	public List<Mould> findMoulds(String name) {
		return baseInfoDao.findMoulds(name);
	}
	public List<StockType> findStockTypes(String dimension, String name) {
		return baseInfoDao.findStockTypes(dimension, name);
	}
	public List<Warehouse> findWarehouses(String name) {
		return baseInfoDao.findWarehouses(name);
	}
	
	public List<ItemInfo> findItems(String name, Long typeOid) {
		return baseInfoDao.findItems(name, typeOid);
	}
	
	public List<Department> findDepartments(String name) {
		return baseInfoDao.findDepartments(name);
	}

	
	public ListResult<WarehouseDto> findWarehouseWithPaging(int pageNumber, int pageSize, String name) {
		IPaging paging = baseInfoDao.findWarehouseWithPaging(pageNumber, pageSize, name);		
		ListResult<WarehouseDto> result = new ListResult<WarehouseDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			List<WarehouseDto> dtoList = new ArrayList<WarehouseDto>();			
			
			for (int i = 0, j = paging.getThisPageElements().size(); i < j; i++) {
				Warehouse obj = (Warehouse) paging.getThisPageElements().get(i);
				WarehouseDto dto = new WarehouseDto();
				dto.setOid(obj.getOid().toString());
				dto.setName(obj.getName());
				boolean isDeletable = baseInfoDao.isDeletable(StockInfo.class, "warehouseOid", obj.getOid());
				if (!isDeletable) {
					dto.setDisabled("true");
				}
				dtoList.add(dto);
			}
			
			result.setRows(dtoList);
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	public ListResult<MachineDto> findMachineWithPaging(int pageNumber, int pageSize, String name) {
		IPaging paging = baseInfoDao.findMachineWithPaging(pageNumber, pageSize, name);
		ListResult<MachineDto> result = new ListResult<MachineDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			List<MachineDto> dtoList = new ArrayList<MachineDto>();			
			
			for (int i = 0, j = paging.getThisPageElements().size(); i < j; i++) {
				Machine obj = (Machine) paging.getThisPageElements().get(i);
				MachineDto dto = FilterUtil.convertObjectClass(obj, MachineDto.class);
				boolean isDeletable = baseInfoDao.isDeletable(ProductControlMain.class, "machineOid", obj.getOid());
				if (!isDeletable) {
					dto.setDisabled("true");
				}
				dtoList.add(dto);
			}
			
			result.setRows(dtoList);
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	
	
	public ListResult<MouldDto> findMouldWithPaging(int pageNumber, int pageSize, String name) {
		IPaging paging = baseInfoDao.findMouldWithPaging(pageNumber, pageSize, name);
		ListResult<MouldDto> result = new ListResult<MouldDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			List<MouldDto> dtoList = new ArrayList<MouldDto>();			
			
			for (int i = 0, j = paging.getThisPageElements().size(); i < j; i++) {
				Mould obj = (Mould) paging.getThisPageElements().get(i);
				MouldDto dto = FilterUtil.convertObjectClass(obj, MouldDto.class);
				boolean isDeletable = baseInfoDao.isDeletable(BomDetail.class, "mouldOid", obj.getOid());
				if (isDeletable) {
					isDeletable = baseInfoDao.isDeletable(ProductControlMain.class, "mouldOid", obj.getOid());
				}
				
				String customerOid = dto.getCustomerOid();
				if (!StringHelper.isEmpty(customerOid)) {
					Customer customer = (Customer) baseInfoDao.getEntityByOid(Customer.class, Long.parseLong(customerOid));
					if (customer != null) {
						dto.setCustomerName(customer.getName());
					}
				}
				
				if (!isDeletable) {
					dto.setDisabled("true");
				}
				dtoList.add(dto);
			}
			
			result.setRows(dtoList);
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	public ListResult<ItemTypeDto> findItemTypeWithPaging(int pageNumber, int pageSize, String name) {
		IPaging paging = baseInfoDao.findItemTypeWithPaging(pageNumber, pageSize, name);
		ListResult<ItemTypeDto> result = new ListResult<ItemTypeDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			List<ItemTypeDto> dtoList = new ArrayList<ItemTypeDto>();			
			
			for (int i = 0, j = paging.getThisPageElements().size(); i < j; i++) {
				ItemType obj = (ItemType) paging.getThisPageElements().get(i);
				if (!StringHelper.isEmpty(obj.getCode())) {
					continue;
				}
				ItemTypeDto dto = new ItemTypeDto();
				dto.setOid(obj.getOid().toString());
				dto.setName(obj.getName());
				boolean isDeletable = baseInfoDao.isDeletable(ItemInfo.class, "typeOid", obj.getOid());
				if (!isDeletable) {
					dto.setDisabled("true");
				}
	
				dtoList.add(dto);
			}
			
			result.setRows(dtoList);
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	public ListResult<StockTypeDto> findStockTypeWithPaging(int pageNumber, int pageSize, String name) {
		IPaging paging = baseInfoDao.findStockTypeWithPaging(pageNumber, pageSize, name);
		ListResult<StockTypeDto> result = new ListResult<StockTypeDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			List<StockTypeDto> dtoList = new ArrayList<StockTypeDto>();			
			
			for (int i = 0, j = paging.getThisPageElements().size(); i < j; i++) {
				StockType obj = (StockType) paging.getThisPageElements().get(i);
				if (!StringHelper.isEmpty(obj.getCode()) && ("CP_PRO".equals(obj.getCode()) || "CP_LOS".equals(obj.getCode()))) {
					continue;
				}
				
				StockTypeDto dto = new StockTypeDto();
				dto.setOid(obj.getOid().toString());
				dto.setName(obj.getName());
				dto.setCode(obj.getCode());
				dto.setDimension(obj.getDimension());
				boolean isDeletable = baseInfoDao.isDeletable(StockInfo.class, "stockTypeOid", obj.getOid());
				if (!isDeletable) {
					dto.setDisabled("true");
				}
				dtoList.add(dto);
			}
			
			result.setRows(dtoList);
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	
	
	public ListResult<DamageInfoDto> findDamageInfosByPaging(int pageNumber, int pageSize, DamageInfoDto criteria) {
		IPaging paging = baseInfoDao.findDamageInfosByPaging(pageNumber, pageSize, criteria);
		ListResult<DamageInfoDto> result = new ListResult<DamageInfoDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			result.setRows(paging.getThisPageElements());
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}

	@Override
	public Supplier getSupplierBysupplierId(String supplierId) {
		return baseInfoDao.getSupplierBysupplierId(supplierId);
	}

	@Override
	public ItemInfo getItembyCode(String Code) {
		return baseInfoDao.getItemByCode(Code);
	}

	@Override
	public List<ItemInfo> findItemsPrice(String name, Long oid) {
		return baseInfoDao.findItemsPrice(name, oid);
	}

	@Override
	public <T> T getObject(Class<T> clazz, String[] field, Object[] value) {
		return baseInfoDao.getObject(clazz, field, value);
	}
	
	@Override
	public List<BomDetailDto> getBomDetailByNoProduct() {
		return baseInfoDao.getBomDetailByNoProduct();
	}

	@Override
	public List<BomDetailDto> getAllBomDetail() {
		return baseInfoDao.getAllBomDetail();
	}
	
}
