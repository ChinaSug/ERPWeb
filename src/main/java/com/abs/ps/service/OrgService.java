package com.abs.ps.service;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.Organization;
import com.abs.ps.web.dto.NameCodeDto;
import com.abs.ps.web.dto.OrgDto;
import com.abs.ps.web.dto.OrganizationDto;



public interface OrgService {
	public IPaging findOrgWithPaging(int pageNumber, int pageSize, String orgOid);
	public Organization save(OrgDto obj);
	public OrganizationDto getOrganizationById(Long id) ;
	public List<OrganizationDto> getAllOrganization() ;
	public void deleteObjectById(String[] ids); 
	public void saveObjects(List<OrganizationDto> serviceCenters);
	public boolean deletable(Long orgOid);
	public boolean isOrgCodeAvailabe(String orgCode);
	public boolean isOrgExist(String name);
	public OrgDto getOrgById(Long id);
	public void deleteOrgtById(String ids);
	public Organization saveWithoutPeriod(OrganizationDto dto);
	public List<String> retrieveAppointDate(Long orgOid) ;
	
	public void saveOrgDefineEntDataType(Long orgOid, String[] entDataTypes);
	public List<NameCodeDto> getOrgDefineEntDataType(Long orgOid);
}
