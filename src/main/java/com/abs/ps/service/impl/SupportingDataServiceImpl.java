package com.abs.ps.service.impl;

import java.util.List;

import com.abs.ps.dao.SupportingDataDao;
import com.abs.ps.service.SupportingDataService;
import com.abs.ps.util.Constants;
import com.abs.ps.web.dto.NameCodeDto;

public class SupportingDataServiceImpl implements SupportingDataService{
	private SupportingDataDao supportingDataDao;
	
	public void setSupportingDataDao(SupportingDataDao supportingDataDao) {
		this.supportingDataDao = supportingDataDao;
	}
	
	public List<NameCodeDto> findControllStateList() {
		return supportingDataDao.findSupportingDataByType(Constants.SUPPORT_CNTR);
	}
	public List<NameCodeDto> findEnterpriceTypeList() {
		return supportingDataDao.findSupportingDataByType(Constants.SUPPORT_ENT_TYPE);
	}
	public List<NameCodeDto> findRequirementList() {
		return supportingDataDao.findSupportingDataByType(Constants.SUPPORT_REQ);
	}
			
	public List<NameCodeDto> findQuotedList() {
		return supportingDataDao.findSupportingDataByType(Constants.SUPPORT_QUOTED);
	}
	
	public List<NameCodeDto> findIndustrySubClassList() {
		return supportingDataDao.findSupportingDataByType(Constants.SUPPORT_IND_SUB);
	}
	
	public List<NameCodeDto> findEntDataTypeList(){
		return supportingDataDao.findSupportingDataByType(Constants.SUPPORT_ENT_DATA);
	}
	
	public List<NameCodeDto> findEntStatisIndex() {
		return supportingDataDao.findSupportingDataByType(Constants.SUPPORT_ENT_STATIS_INDEX);
	}

	@Override
	public List<Long> getOrgConfList(long orgOid) {
		return supportingDataDao.getOrgConfList(orgOid);
	}
	
	@Override
	public List<NameCodeDto> findHousePurposeList(long orgOid) {
		return supportingDataDao.findSupportingData(Constants.SUPPORT_PURPOSE, String.valueOf(orgOid));
	}
	
	@Override
	public List<NameCodeDto> findHouseOwnershipList(long orgOid) {
		return supportingDataDao.findSupportingData(Constants.SUPPORT_OWNERSHIP, String.valueOf(orgOid));
	}
	
	@Override
	public List<NameCodeDto> findHouseAssetSourceList(long orgOid) {
		return supportingDataDao.findSupportingData(Constants.SUPPORT_ASSET_SOURCE, String.valueOf(orgOid));
	}
}
