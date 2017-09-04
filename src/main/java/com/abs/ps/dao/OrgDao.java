package com.abs.ps.dao;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.DocumentNum;
import com.abs.ps.domain.Organization;
import com.abs.ps.web.dto.NameCodeDto;

public interface OrgDao {
	public IPaging findOrgWithPaging(int pageNumber, int pageSize, String orgOid);
	public Organization getOrganizationById(Long id);
	public List<Organization> getAllOrganization() ;
	public Organization save(Organization org);
	public void deleteObjectById(String ids);
	public void saveObjects(List<Organization> serviceCenters);
	public void saveDoc(DocumentNum obj);
	public boolean deletable(Long orgOid);
	public boolean isOrgCodeAvailabe(String orgCode);
	public boolean isOrgExist(String name);

	/**
	 * 存储园区设置的企业数据显示参数信息在T_SUPPORT_DATA表中
	 * 其中DATA_CODE存储的是园区Oid, DATA_NAME存储的是要显示的企业数据参数
	 * 
	 * @param orgOid
	 * @param entDataTypes
	 */
	public void saveOrgDefineEntDataType(Long orgOid, String[] entDataTypes);
	
	/**
	 * 获取园区设置要显示的企业数据参数信息
	 * <p>NameCodeDto中code存储的是id号，name存储的是企业参数信息，type存储的是类型信息，value存储的是园区Oid</p>
	 * 
	 * @param orgOid
	 * @return
	 */
	public List<NameCodeDto> getOrgDefineEntDataType(Long orgOid);
	
	public Long getOrgOidByName(String orgName);
}