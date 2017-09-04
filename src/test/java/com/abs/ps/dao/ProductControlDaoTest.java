package com.abs.ps.dao;

import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import com.abs.core.paging.IPaging;
import com.abs.ps.DaoSupportTest;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ProductControlDetailDto;

public class ProductControlDaoTest extends DaoSupportTest {
	
	private ProductControlDao productControlDao;
	
	@Before
	public void before() {
		productControlDao = (ProductControlDao) context.getBean("productControlDao");
	}
	
	@Test
	public void testIsPassProdAmt() {
		boolean passProdAmt = productControlDao.isExceedProdAmt(59); 
		
		System.out.println(passProdAmt);
	}
	
	@Test
	public void testfindPCDetailPaging() {
		ListResult<ProductControlDetailDto> result = productControlDao.findPCDetailPaging(1, 5, 60);
		printJsonStr(result);
	}
	
	@Test
	public void testfindProductControlMainWithPaging() {
		IPaging paging = productControlDao.findProductControlMainWithPaging(1, 10, null);
		printJsonStr(paging);
	}
	
	@Test
	public void testgetNewlyWeekPlanTable() {
		int year = 2017;
		int month = 9;
		int week = 1;
		
		JSONObject jsonMap = productControlDao.getNewlyWeekPlanTable(year, month, week);
		printJsonStr(jsonMap);
	}
	

}
