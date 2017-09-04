package com.abs.ps.dao;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.Department;
import com.abs.ps.web.dto.DepartDto;

public interface DepartmentDao extends IDao {
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, DepartDto depart);
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, String orgOid);
	public Department getObjectById(Long id);
	public void save(Department org);
	public void deleteObjectById(String ids);
	public void saveObjects(List<Department> serviceCenters);
	public List<Department> getAllDepartment() ;
	public boolean deletable(Long orgOid);
	public List<Department> getDepartmentByOrg(Long orgOid);
}
