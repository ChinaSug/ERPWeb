package com.abs.ps.service;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.web.dto.DepartDto;
import com.abs.ps.web.dto.DepartmentDto;
import com.abs.ps.web.dto.ListResult;

public interface DepartmentService extends IService {
	public ListResult<DepartDto> findObjectWithPaging(int pageNumber, int pageSize, DepartDto depart);
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, String orgOid);
	public DepartmentDto getObjectById(Long id);
	public void save(DepartmentDto org);
	public void deleteObjectById(String[] ids);
	public void saveObjects(List<DepartmentDto> serviceCenters);
	public List<DepartmentDto> getAllDepartment();
	public boolean deletable(Long orgOid);
	public List<DepartmentDto> getDepartmentByOrg(Long orgOid);
}
