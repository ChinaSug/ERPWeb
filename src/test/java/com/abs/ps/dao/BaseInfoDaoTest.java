package com.abs.ps.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.abs.core.paging.IPaging;
import com.abs.ps.DaoSupportTest;
import com.abs.ps.domain.Department;
import com.abs.ps.web.dto.BomDetailDto;

public class BaseInfoDaoTest extends DaoSupportTest {

	private BaseInfoDao baseInfoDao;
	
	@Before
	public void before() {
		baseInfoDao = (BaseInfoDao) context.getBean("baseInfoDao");
	}
	
	@Test
	public void testGetObject() {
		String[] fieldArr = {"status", "oid"};
		Object[] valueArr = {"1", 2L};
		
		Department object = baseInfoDao.getObject(Department.class, fieldArr, valueArr);
		
		printJsonStr(object);
	}
	
	@Test
	public void testGetBomDetailByNoProduct() {
		
		List<BomDetailDto> list = baseInfoDao.getBomDetailByNoProduct();
		printJsonStr(list);
		System.out.println(list.size());
		
	}
	
	@Test
	public void testGetAllBomDetail() {
		List<BomDetailDto> list = baseInfoDao.getAllBomDetail();
		printJsonStr(list);
		System.out.println(list.size());
	}
	
	@Test
	public void testFindDamageInfosByPaging() {
		IPaging paging = baseInfoDao.findDamageInfosByPaging(1, 10, null);
		printJsonStr(paging);
	}
	
	
	
}
