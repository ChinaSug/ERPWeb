package com.abs.ps.dao;

import java.util.List;

public interface IDao {
	public Object getEntityByOid(Class clazz, Long id);
	public Object getEntityByOid(Class clazz, String fieldName, Object obj);
	public List getEntityListByOid(Class clazz, String fieldName, Object obj);
	public void saveObject(Object obj);
	public Object saveObjectNReturn(Object obj);
	public void delete(Class clazz, String fieldName, Long oid);
	public void delete(Class clazz,Long oid);
	public boolean isDeletable(Class clazz, String fieldName, Long oid) ;
}
