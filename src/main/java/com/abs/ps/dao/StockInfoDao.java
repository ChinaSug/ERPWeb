package com.abs.ps.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.CheckPointDetail;
import com.abs.ps.web.dto.CheckPointDetailDto;
import com.abs.ps.web.dto.CheckPointMainDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.StockInfoDto;
import com.abs.ps.web.dto.StockSearchDto;
import com.abs.ps.web.dto.StockSumDto;

public interface StockInfoDao extends IDao{
	public IPaging findStocksByPaging(int pageNumber, int pageSize, StockInfoDto criteria);
	
	/**
	 * 该月是否已盘点
	 * @param dto
	 * @return
	 */
	public boolean isDuplicateCheckPoint(CheckPointMainDto dto);
	public IPaging findCheckPointByPaging(int pageNumber, int pageSize, CheckPointMainDto criteria);
	public List<StockSumDto> findStockInfos(StockSumDto criteria);
	public List<CheckPointDetailDto> findStockCheckPointDetailInfos(Long cpMainOid);
	public IPaging searchCheckPointByPaging(int pageNumber, int pageSize, StockSearchDto criteria);
	public List<CheckPointDetail> findCPDetails(Long cpMainOid);
	public boolean isUnderCheckPointByWHS(Long whsOid);
	
	/**
	 * 获取产品库存查询视图
	 * @return
	 */
	public ListResult<JSONObject> findStockProdView(int pageNum, int pageSize, Map<String, String> searchMap);
	
	/**
	 * 删除关联库存盘点的子表
	 * @param cpMainIds 库存盘点主表ids 
	 */
	public void deleteCheckPointDetail(String cpMainIds);
	
	/**
	 * 获取仓库盘点中，产品在入库出库后剩下的产品数量信息
	 * @param whOid
	 * @return
	 */
	public List<CheckPointDetailDto> getStockProdView(String whOid);
	
	/**
	 * 获取盘点中的产品库存列表
	 * @param cpMainOid
	 * @return
	 */
	public List<CheckPointDetailDto> getCheckPointProd(String cpMainOid);
	
	/**
	 * 判断仓库是否在待盘点状态
	 * @param warehouseOid
	 * @return
	 */
	public boolean isCheckStockWarehouse(String warehouseOid);
	
	/**
	 * 根据库存Ids获取其中待盘点的库存
	 * @param stockIds
	 * @return
	 */
	public List<StockInfoDto> getCheckingStock(String stockIds);
	
	/**
	 * 获取库存查询视图列表
	 * @param paramMap
	 * @return
	 */
	public List<StockSearchDto> getStockView(Map<String, String> paramMap);
}
