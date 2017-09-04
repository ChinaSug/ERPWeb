package com.abs.ps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.OrgDao;
import com.abs.ps.domain.DocumentNum;
import com.abs.ps.domain.Organization;
import com.abs.ps.service.OrgService;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.StringHelper;
import com.abs.ps.util.WorkingDateUtil;
import com.abs.ps.web.dto.NameCodeDto;
import com.abs.ps.web.dto.OrgDto;
import com.abs.ps.web.dto.OrganizationDto;

public class OrgServiceImpl implements OrgService{
	private OrgDao centerDao;
	
	public void setOrgDao(OrgDao centerDao) {
		this.centerDao = centerDao;
	}
	
	public IPaging findOrgWithPaging(int pageNumber, int pageSize, String orgOid) {
		return centerDao.findOrgWithPaging(pageNumber, pageSize, orgOid);
	}
	
	public Organization save(OrgDto dto) {
		Organization center = null;
		boolean isNew = false;
		if (!StringHelper.isEmpty(dto.getOid())) {
			center = centerDao.getOrganizationById(Long.valueOf(dto.getOid()));
		} else {
			center = new Organization();		
			center.setCreateBy(dto.getCreateBy());
			center.setCreateDate(new Date());
			isNew = true;
		}
		
		center.setOrgName(dto.getOrgName());
		center.setStatus(dto.getStatus());
		center.setOrgCode(dto.getOrgCode());
		center.setLastModifyBy(dto.getCreateBy());
		center.setLastModifyDate(new Date());
		center.setLogoUrl(dto.getLogoUrl());
		center.setActivityAmt(dto.getActivityAmt());
		center.setDescr(dto.getDescr());
		center.setHotActivityAmt(dto.getHotActivityAmt());
		center.setHotEnrollAmt(dto.getHotEnrollAmt());
		center.setHotNoticeAmt(dto.getHotNoticeAmt());
		center.setNeedConfirm(dto.isNeedConfirm());
		center.setRotateImgUrl1(dto.getRotateImgUrl1());
		center.setRotateImgUrl2(dto.getRotateImgUrl2());
		center.setRotateImgUrl3(dto.getRotateImgUrl3());
		center.setRotateImgUrl4(dto.getRotateImgUrl4());
		center.setRotateImgUrl5(dto.getRotateImgUrl5());
		
//		if (!StringHelper.isEmpty(dto.getMaxAppointDay())) {
//			center.setMaxAppointDay(Integer.parseInt(dto.getMaxAppointDay()));
//		}
//		if (!StringHelper.isEmpty(dto.getIsWorkingDay())) {
//			center.setIsWorkingDay("Y".equals(dto.getIsWorkingDay())?Boolean.TRUE:Boolean.FALSE);
//		}
		
		center.setImgUrl1(dto.getImgForwardUrl1());
		center.setImgUrl2(dto.getImgForwardUrl2());
		center.setImgUrl3(dto.getImgForwardUrl3());
		center.setImgUrl4(dto.getImgForwardUrl4());
		center.setImgUrl5(dto.getImgForwardUrl5());
		center.setMaxAppointDay(FilterUtil.filterString2Int(dto.getMaxAppointDay()));
		
		center = centerDao.save(center);
		
		if (isNew) {
			DocumentNum newDoc = new DocumentNum();
			newDoc.setOrgOid(center.getOid());
			newDoc.setDocNum(100000);
			newDoc.setTypeCode(center.getOrgCode());
			centerDao.saveDoc(newDoc);
		}

		return center;
	}
	
	public Organization saveWithoutPeriod(OrganizationDto dto) {
		Organization center = null;
		boolean isNew = false;
		if (dto.getId() > 0) {
			center = centerDao.getOrganizationById(Long.valueOf(dto.getId()));
		} else {
			center = new Organization();		
			center.setCreateBy(dto.getCreateBy());
			center.setCreateDate(new Date());
			isNew = true;
		}
		
		center.setOrgName(dto.getOrgName());
		center.setStatus(dto.getStatus());
		center.setOrgCode(dto.getOrgCode());
		center.setNeedConfirm(dto.isNeedConfirm());
		center.setAssetsExpireAdvanceDateAmt(dto.getAssetsExpireAdvanceDateAmt());
		center.setReturnExpireAdvanceDateAmt(dto.getReturnExpireAdvanceDateAmt());
		center.setLastModifyBy(dto.getLastModifyBy());
		center.setLastModifyDate(new Date());
		center.setExpireDate(dto.getExpireDate());

		center.setMaxAppointDay(dto.getMaxAppointDay());

		if (!StringHelper.isEmpty(dto.getIsWorkingDay())) {
			center.setIsWorkingDay("Y".equals(dto.getIsWorkingDay())?Boolean.TRUE:Boolean.FALSE);
		}
		
		center = centerDao.save(center);
		
		if (isNew) {
			DocumentNum newDoc = new DocumentNum();
			newDoc.setOrgOid(center.getOid());
			newDoc.setDocNum(100000);
			newDoc.setTypeCode(center.getOrgCode());
			centerDao.saveDoc(newDoc);
		}
		return center;
	}
	
	public boolean isOrgCodeAvailabe(String orgCode) {
		return centerDao.isOrgCodeAvailabe(orgCode);
	}
	public OrganizationDto getOrganizationById(Long id) {
		Organization obj = centerDao.getOrganizationById(id);
		OrganizationDto objDto = null;
		if (obj != null) {
			objDto = FilterUtil.convertObjectClass(obj, OrganizationDto.class);
			objDto.setId(obj.getOid().longValue());
		}

		return objDto;
	}
	
	public OrgDto getOrgById(Long id) {
		Organization obj = centerDao.getOrganizationById(id);
		OrgDto objDto = new OrgDto();
		if (obj != null) {
			objDto = FilterUtil.convertObjectClass(obj, OrgDto.class);
		}

		return objDto;
	}
	
	
	public List<OrganizationDto> getAllOrganization() {
		List<Organization> list = centerDao.getAllOrganization();
		List<OrganizationDto> dtoList = new ArrayList<OrganizationDto>();
		if (list != null) {
			for (Organization org : list) {
				OrganizationDto orgDto = new OrganizationDto();
				orgDto.setOrgName(org.getOrgName());
				orgDto.setOrgCode(org.getOrgCode());
				orgDto.setId(org.getOid());
				orgDto.setStatus(org.getStatus());
				orgDto.setNeedConfirm(org.getNeedConfirm()==null?false:org.getNeedConfirm());
				orgDto.setAssetsExpireAdvanceDateAmt(org.getAssetsExpireAdvanceDateAmt() == null?0:org.getAssetsExpireAdvanceDateAmt().intValue());
				orgDto.setReturnExpireAdvanceDateAmt(org.getReturnExpireAdvanceDateAmt() == null?0:org.getReturnExpireAdvanceDateAmt().intValue());
				if (org.getMaxAppointDay() != null) {
					orgDto.setMaxAppointDay(org.getMaxAppointDay());
				}
				if (org.getIsWorkingDay() != null) {
					orgDto.setIsWorkingDay(org.getIsWorkingDay().booleanValue()?"Y":"N");
				}
				
				dtoList.add(orgDto);
			}
		}
		return dtoList;
	}
	
	public void deleteObjectById(String[] ids) {
		centerDao.deleteObjectById(StringHelper.join(",", ids));		
	}
	
	public void deleteOrgtById(String ids) {
		centerDao.deleteObjectById(ids);
	}
	
	public boolean deletable(Long orgOid){
		return centerDao.deletable(orgOid);
	}
	

	
	public boolean isObjectDeletable(String centerOid) {
		return true;//centerDao.isCenterDeletable(centerOid);
	}
	
	public void saveObjects(List<OrganizationDto> serviceCenters) {
		//centerDao.saveObjects(serviceCenters);
	}
	
	public boolean isOrgExist(String name) {
		return centerDao.isOrgExist(name);
	}
	
	public List<String> retrieveAppointDate(Long orgOid) {
		Organization org = centerDao.getOrganizationById(orgOid);
		List<String> appointDateList = new ArrayList<String>();
		if (org != null) {
			boolean isWorkingDate = org.getIsWorkingDay();
			int maxAppointDay = org.getMaxAppointDay();
			Date now = new Date();
			int count = 1;
			while (true) {
				if (maxAppointDay <= appointDateList.size()) {
					break;
				}
				Date appointDate = DateHelper.caculate(now, count);
				if (isWorkingDate && WorkingDateUtil.isWorkingDay(appointDate)) {
					appointDateList.add(DateHelper.convert2String(appointDate, DateHelper.DATE_FORMATE));
				}
				if (!isWorkingDate) {
					appointDateList.add(DateHelper.convert2String(appointDate, DateHelper.DATE_FORMATE));
				}
				count++;
			}
			
		}
		return appointDateList;
	}

	@Override
	public void saveOrgDefineEntDataType(Long orgOid, String[] entDataTypes) {
		centerDao.saveOrgDefineEntDataType(orgOid, entDataTypes);
	}

	@Override
	public List<NameCodeDto> getOrgDefineEntDataType(Long orgOid) {
		return centerDao.getOrgDefineEntDataType(orgOid);
	}
	
}
