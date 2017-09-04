package com.abs.ps.service;

import java.util.List;

import com.abs.ps.dao.IDao;

public abstract class AbstractService {
	protected IDao iDao;
	
	public void setIDao(IDao iDao) {
		this.iDao = iDao;
	}

	public void saveObject(Object obj) {
		iDao.saveObject(obj);
	}

	public void delete(Class clazz, String fieldName, Long oid) {
		iDao.delete(clazz, fieldName, oid);
		
	}

	public void delete(Class clazz, Long oid) {
		iDao.delete(clazz, oid);
	}
	
	public Object getEntityByOid(Class clazz, Long id) {
		return iDao.getEntityByOid(clazz, id);
	}
	
	public List getEntityListByOid(Class clazz, String fieldName, Object obj) {
		return iDao.getEntityListByOid(clazz, fieldName, obj);
	}
}
