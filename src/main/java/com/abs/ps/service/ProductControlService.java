package com.abs.ps.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ProductControlDetailDto;
import com.abs.ps.web.dto.ProductControlMainDto;

public interface ProductControlService extends IService{
	public ListResult<ProductControlMainDto> findProductControlMainWithPaging(int pageNumber, int pageSize, ProductControlMainDto criteria);
	public ProductControlMainDto getProductControlByPcOid(Long pcOid);
	public List<ProductControlDetailDto> findProductControlDetails(String pcOid);
	public List<Object> getWeekPlanTable(int year, int month, int week);
	public boolean isExceedProdAmt(long pcmOid);
	public ListResult<ProductControlDetailDto> findPCDetailPaging(int pageNumber, int pageSize, long pcmOid);
	public JSONObject getNewlyWeekPlanTable(int year, int month, int week);
}
