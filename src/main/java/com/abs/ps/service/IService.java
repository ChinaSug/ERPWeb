package com.abs.ps.service;

public interface IService {
	public Object getEntityByOid(Class clazz, Long id);
	public void saveObject(Object obj);
	public void delete(Class clazz, String fieldName, Long oid);
	public void delete(Class clazz,Long oid);
}
 