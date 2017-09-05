package com.abs.ps.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.abs.ps.domain.CheckPointDetail;
import com.abs.ps.domain.CheckPointMain;
import com.abs.ps.web.dto.CheckPointDetailDto;
import com.abs.ps.web.dto.CheckPointMainDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.StockInfoDto;
import com.abs.ps.web.dto.StockSearchDto;

public interface StockInfoService extends IService{
	public ListResult<StockInfoDto> findStocksByPaging(int pageNumber, int pageSize, StockInfoDto criteria);
	public boolean isDuplicateCheckPoint(CheckPointMainDto dto);
	public ListResult<CheckPointMainDto> findCheckPointByPaging(int pageNumber, int pageSize, CheckPointMainDto criteria);
	public void generateCheckPoint(CheckPointMainDto dto);
	public List<CheckPointDetailDto> findStockCheckPointDetailInfos(Long cpMainOid);
	public ListResult<StockSearchDto> searchCheckPointByPaging(int pageNumber, int pageSize, StockSearchDto criteria);
	
	/**
	 * 保存库存子表信息，通过cpMainOid，
	 * 
	 * @param cpMainOid
	 * @param cpMap
	 * @param userId
	 * @param isConfirmCP
	 */
	public void saveCPDetail(String cpMainOid, Map<String, CheckPointDetailDto> cpMap, String userId, boolean isConfirmCP);
	public boolean isUnderCheckPointByWHS(Long whsOid);
	
	public ListResult<JSONObject> findStockProdView(int pageNum, int pageSize, Map<String, String> searchMap);
	public void deleteCheckPointDetail(String cpMainIds);
	public List<CheckPointDetailDto> getProdCheckPointDetail(String whOid);
	public List<CheckPointDetailDto> getCheckPointProd(String cpMainOid);
	
	/**
	 * 保存或确定库存盘点信息
	 * @param cpMain 主库存盘点表，用于更新状态和最后修改人时间
	 * @param itemCPList 物料库存盘点子表
	 * @param prodCPList 产品库存盘点子表
	 * @param isConfirmCP 是否确认盘点信息
	 */
	public void setCPDetail(CheckPointMain cpMain, String itemsJsonStr, String prodsJsonStr, boolean isConfirmCP);
	
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
	
	public List<StockSearchDto> getStockView(Map<String, String> paramMap);
}
 