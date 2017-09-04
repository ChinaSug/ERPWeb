package com.abs.ps.service;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.web.dto.AMEmployeeDto;
import com.abs.ps.web.dto.NameCodeDto;

public interface EmployeeService {
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, String orgOid);
	public AMEmployeeDto getObjectById(Long id);
	public void save(AMEmployeeDto obj);
	public void deleteObjectById(String[] ids);
	public void saveObjects(List<AMEmployeeDto> objs);
	public List<NameCodeDto> findAllEmployeeByOrgOid(Long orgOid);
	public boolean deletable(Long oid);
	public List<NameCodeDto> findAllEmployeeByDepartOid(Long departOid);
}
