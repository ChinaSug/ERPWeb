package com.abs.ps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.DepartmentDao;
import com.abs.ps.domain.Department;
import com.abs.ps.service.AbstractService;
import com.abs.ps.service.DepartmentService;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.DepartDto;
import com.abs.ps.web.dto.DepartmentDto;
import com.abs.ps.web.dto.ListResult;

public class DepartmentServiceImpl extends AbstractService implements DepartmentService{
	private DepartmentDao departmentDao;
	
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
		super.setIDao(departmentDao);
	}
	
	public IPaging findObjectWithPaging(int pageNumber, int pageSize, String orgOid) {
		return departmentDao.findObjectWithPaging(pageNumber, pageSize, orgOid);
	}
	public DepartmentDto getObjectById(Long id) {
		Department obj = departmentDao.getObjectById(id);
		return convert(obj);
	}
	public void save(DepartmentDto org) {
		departmentDao.save(convert(org));
	}
	public void deleteObjectById(String[] ids) {
		departmentDao.deleteObjectById(StringHelper.join(",", ids));
	}
	public void saveObjects(List<DepartmentDto> serviceCenters) {
		
	}
	
	public boolean deletable(Long orgOid) {
		return departmentDao.deletable(orgOid);
	}
	
	private DepartmentDto convert(Department object) {
		DepartmentDto objDto = new DepartmentDto();
		if (object != null) {
			objDto.setId(object.getOid());
			objDto.setDepartName(object.getDepartName());
			objDto.setOrgOid(String.valueOf(object.getOrgOid()));
			objDto.setStatus(object.getStatus());			
		}
		return objDto;
	}
	
	private Department convert(DepartmentDto objDto) {
		Department obj = null;
		if (objDto != null) {
			if (objDto.getId() > 0) {
				obj = departmentDao.getObjectById(objDto.getId());
			} else {
				obj = new Department();
				obj.setCreateBy(objDto.getCreateBy());
				obj.setCreateDate(new Date());
			}
			obj.setDepartName(objDto.getDepartName());
			obj.setOrgOid(Long.parseLong(objDto.getOrgOid()));
			obj.setStatus(objDto.getStatus());
			obj.setLastModifyBy(objDto.getLastModifyBy());
			obj.setLastModifyDate(new Date());
		}
		return obj;
	}
	
	public List<DepartmentDto> getAllDepartment() {
		List<Department> departs = departmentDao.getAllDepartment();
		if (departs != null) {
			return convert2Dto(departs);
		}
		return null;
		
	}
	
	private List<DepartmentDto> convert2Dto(List<Department> departs) {
		List<DepartmentDto> departDtos = new ArrayList<DepartmentDto>();
		if (departs != null) {
			for (Department depart : departs) {
				DepartmentDto dto = new DepartmentDto();
				dto.setId(depart.getOid());
				dto.setDepartName(depart.getDepartName());
				departDtos.add(dto);
			}
		}
		return departDtos;
	}
	
	public List<DepartmentDto> getDepartmentByOrg(Long orgOid) {
		List<Department> departs = departmentDao.getDepartmentByOrg(orgOid);
		if (departs != null) {
			return convert2Dto(departs);
		}
		return null;
	}

	
	@Override
	public ListResult<DepartDto> findObjectWithPaging(int pageNumber,
			int pageSize, DepartDto depart) {
		// TODO Auto-generated method stub
		IPaging paging = departmentDao.findObjectWithPaging(pageNumber, pageSize, depart);
		ListResult<DepartDto> result = new ListResult<DepartDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {	
			result.setRows(paging.getThisPageElements());
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	
}
