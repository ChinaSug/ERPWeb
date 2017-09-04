package com.abs.ps.service.impl;

import java.util.Date;
import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.EmployeeDao;
import com.abs.ps.domain.Employee;
import com.abs.ps.service.EmployeeService;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.AMEmployeeDto;
import com.abs.ps.web.dto.NameCodeDto;

public class EmployeeServiceImpl implements EmployeeService{
	private EmployeeDao employeeDao;
	
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, String orgOid) {
		return employeeDao.findObjectWithPaging(pageNumber, pageSize, orgOid);
	}
	public AMEmployeeDto getObjectById(Long id) {
		Employee eployee = employeeDao.getObjectById(id);
		return convert(eployee);
	}
	
	public List<NameCodeDto> findAllEmployeeByOrgOid(Long orgOid) {
		return employeeDao.findAllEmployeeByOrgOid(orgOid);
	}
	
	public List<NameCodeDto> findAllEmployeeByDepartOid(Long departOid) {
		return employeeDao.findAllEmployeeByDepartOid(departOid);
	}
	
	public void save(AMEmployeeDto obj) {
		employeeDao.save(convert(obj));
	}
	public void deleteObjectById(String[] ids) {
		employeeDao.deleteObjectById(StringHelper.join(",", ids));
	}
	public void saveObjects(List<AMEmployeeDto> objs) {
		
		//employeeDao.saveObjects(objs);
	}
	
	public boolean deletable(Long oid) {
		return employeeDao.deletable(oid);
	}
	
	private AMEmployeeDto convert(Employee object) {
		AMEmployeeDto objDto = new AMEmployeeDto();
		if (object != null) {
			objDto.setId(object.getOid());
			objDto.setEmpName(object.getEmpName());
			objDto.setEmpType(object.getEmpType());
			objDto.setDepartOid(String.valueOf(object.getDepartOid()));
			objDto.setPositionCode(object.getPositionCode());
			objDto.setSupport(object.getIsSupport() != null?object.getIsSupport().booleanValue():false);
			objDto.setWorking(object.getIsWorking() != null?object.getIsWorking().booleanValue():false);
			objDto.setStatus(object.getStatus());			
		}
		return objDto;
	}
	
	private Employee convert(AMEmployeeDto objDto) {
		Employee obj = null;
		if (objDto != null) {
			if (objDto.getId() > 0) {
				obj = employeeDao.getObjectById(objDto.getId());
			} else {
				obj = new Employee();
				obj.setCreateBy(objDto.getCreateBy());
				obj.setCreateDate(new Date());
			}
			obj.setDepartOid(Long.parseLong(objDto.getDepartOid()));
			obj.setEmpName(objDto.getEmpName());
			obj.setEmpType(objDto.getEmpType());
			obj.setIsSupport(objDto.isSupport());
			obj.setIsWorking(objDto.isWorking());
			obj.setPositionCode(objDto.getPositionCode());			
			obj.setStatus(objDto.getStatus());
			obj.setLastModifyBy(objDto.getLastModifyBy());
			obj.setLastModifyDate(new Date());
		}
		return obj;
	}
}
