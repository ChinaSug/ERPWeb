package com.abs.ps.dao;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.Employee;
import com.abs.ps.web.dto.NameCodeDto;

public interface EmployeeDao {
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, String orgOid);
	public Employee getObjectById(Long id);
	public void save(Employee org);
	public void deleteObjectById(String ids);
	public void saveObjects(List<Employee> serviceCenters);
	public List<NameCodeDto> findAllEmployeeByOrgOid(Long orgOid);
	public boolean deletable(Long oid);
	public List<NameCodeDto> findAllEmployeeByDepartOid(Long departOid);
}
