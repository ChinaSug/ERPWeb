package com.abs.ps.service;

import java.util.List;

import com.abs.ps.web.dto.NameCodeDto;

public interface SupportingDataService {
	public List<NameCodeDto> findControllStateList();
	public List<NameCodeDto> findEnterpriceTypeList();
	public List<NameCodeDto> findRequirementList();
	public List<NameCodeDto> findQuotedList();
	public List<NameCodeDto> findIndustrySubClassList();
	public List<NameCodeDto> findEntDataTypeList();
	public List<NameCodeDto> findEntStatisIndex();
	public List<Long> getOrgConfList(long orgOid);
	
	/**
	 * 获取房屋用途列表
	 * 
	 * @param orgOid 所属单位Oid
	 * @return
	 */
	public List<NameCodeDto> findHousePurposeList(long orgOid);
	
	/**
	 * 获取房屋产权归属列表
	 * 
	 * @param orgOid 所属单位Oid
	 * @return
	 */
	public List<NameCodeDto> findHouseOwnershipList(long orgOid);
	
	/**
	 * 获取房屋资产来源列表
	 * 
	 * @param orgOid 所属单位Oid
	 * @return
	 */
	public List<NameCodeDto> findHouseAssetSourceList(long orgOid);
}
