package com.abs.ps.dao;

import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import com.abs.core.paging.IPaging;
import com.abs.ps.DaoSupportTest;
import com.abs.ps.web.dto.CheckPointDetailDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.StockInfoDto;

public class StockInfoDaoImpl extends DaoSupportTest {

	private StockInfoDao stockInfoDao = null;
	
	@Before
	public void before() {
		stockInfoDao = (StockInfoDao) context.getBean("stockInfoDao");
	}
	
	@Test
	public void testFindStocksByPaging() {
		
		IPaging paging = stockInfoDao.findStocksByPaging(1, 20, null);
		printJsonStr(paging);
		
	}
	
	@Test
	public void testGetStockProdView() {
		
		ListResult<JSONObject> list = stockInfoDao.findStockProdView(1, 10, null);
		printJsonStr(list);
		
	}
	
	@Test
	public void testDeleteCheckPointDetail() {
		
		// stockInfoDao.deleteCheckPointDetail("6,7");
		
	}
	
	@Test
	public void testgetProdCheckPointDetail() {
		
		List<CheckPointDetailDto> list = stockInfoDao.getStockProdView("1");
		printJsonStr(list);
		
	}
	
	@Test
	public void testGetCheckingStock() {
		
		List<StockInfoDto> list = stockInfoDao.getCheckingStock("1237, 2");
		printJsonStr(list);
		
	}
	
}
