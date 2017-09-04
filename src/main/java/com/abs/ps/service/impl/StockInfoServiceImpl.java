package com.abs.ps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.util.StringHelper;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.StockInfoDao;
import com.abs.ps.domain.CheckPointDetail;
import com.abs.ps.domain.CheckPointMain;
import com.abs.ps.domain.StockInfo;
import com.abs.ps.domain.StockType;
import com.abs.ps.service.AbstractService;
import com.abs.ps.service.StockInfoService;
import com.abs.ps.util.Constants;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.web.dto.CheckPointDetailDto;
import com.abs.ps.web.dto.CheckPointMainDto;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.StockInfoDto;
import com.abs.ps.web.dto.StockSearchDto;
import com.abs.ps.web.dto.StockSumDto;

public class StockInfoServiceImpl extends AbstractService implements StockInfoService{
	private StockInfoDao stockInfoDao;
	
	public void setStockInfoDao(StockInfoDao stockInfoDao) {
		this.stockInfoDao = stockInfoDao;
		super.setIDao(stockInfoDao);
	}
	
	@Override
	public ListResult<StockInfoDto> findStocksByPaging(int pageNumber, int pageSize, StockInfoDto criteria) {
		IPaging paging = stockInfoDao.findStocksByPaging(pageNumber, pageSize, criteria);
		ListResult<StockInfoDto> result = new ListResult<StockInfoDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			result.setRows(paging.getThisPageElements());
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	
	
	public ListResult<CheckPointMainDto> findCheckPointByPaging(int pageNumber, int pageSize, CheckPointMainDto criteria) {
		IPaging paging = stockInfoDao.findCheckPointByPaging(pageNumber, pageSize, criteria);
		ListResult<CheckPointMainDto> result = new ListResult<CheckPointMainDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			result.setRows(paging.getThisPageElements());
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	
	public List<CheckPointDetailDto> findStockCheckPointDetailInfos(Long cpMainOid) {
		return stockInfoDao.findStockCheckPointDetailInfos(cpMainOid);
	}
	
	public boolean isDuplicateCheckPoint(CheckPointMainDto dto) {
		return stockInfoDao.isDuplicateCheckPoint(dto);
	}
	
	public void generateCheckPoint(CheckPointMainDto dto) {
		if (dto != null) {

			CheckPointMain cpMain = new CheckPointMain();
			cpMain.setCheckNum(dto.getCheckNum());
			cpMain.setCheckPerson(dto.getCheckPerson());
			cpMain.setCreateBy(dto.getCreateBy());
			cpMain.setCreateDate(new Date());
			cpMain.setPeroidTime(dto.getPeroidTime());
			cpMain.setPeroidType(dto.getPeroidType());
			cpMain.setPeroidYear(dto.getPeroidYear());
			cpMain.setRemark(dto.getRemark());
			cpMain.setStatus("0");
			if (!StringHelper.isEmpty(dto.getWarehouseOid())) {
				cpMain.setWarehouseOid(Long.parseLong(dto.getWarehouseOid()));
			}
			
			cpMain = (CheckPointMain) stockInfoDao.saveObjectNReturn(cpMain);
			
			// 添加该仓库的物料库存到盘点子表t_st_check_detail
			// 查找仓库入库的物料数量，并求和以物料Oid为键存到stockMap中
			StockSumDto c1 = new StockSumDto();
			c1.setDimension("1"); // 入库类型
			c1.setWhsOid(dto.getWarehouseOid());
			List<StockSumDto> stockInList = stockInfoDao.findStockInfos(c1);
			Map<String, StockSumDto> stockMap = new HashMap<>();
			for (StockSumDto inDto : stockInList) {
				StockSumDto tempSDto = stockMap.get(inDto.getItemOid());
				if (tempSDto != null) {
					tempSDto.setSumAmt(tempSDto.getSumAmt() + inDto.getSumAmt()); 
				} else {
					stockMap.put(inDto.getItemOid(), inDto);
				}
			}
			// 查找仓库出库的物料数量，并将入库物料相减
			StockSumDto c2 = new StockSumDto();
			c2.setDimension("2");
			c2.setWhsOid(dto.getWarehouseOid());
			List<StockSumDto> stockOutList = stockInfoDao.findStockInfos(c2);
			for (StockSumDto outDto : stockOutList) {
				StockSumDto tempSDto = stockMap.get(outDto.getItemOid());
				if (tempSDto != null) {
					tempSDto.setSumAmt(tempSDto.getSumAmt() - outDto.getSumAmt()); 
				} else {
					stockMap.put(outDto.getItemOid(), outDto);
				}
			}
			// 
			for (Map.Entry<String, StockSumDto> entry : stockMap.entrySet()) {
				StockSumDto stockDto = entry.getValue();
				CheckPointDetail cpDetail = new CheckPointDetail();
				cpDetail.setCpMainOid(cpMain.getOid());
				cpDetail.setCurrentStockAmt(stockDto.getSumAmt());
				cpDetail.setItemOid(Long.parseLong(stockDto.getItemOid()));
				cpDetail.setStockItemType(Constants.STOCK_ITEM_TYPE_MATERIAL); // 设置仓库存储类型为物料
				
				stockInfoDao.saveObject(cpDetail);
			}
			
			// 添加该仓库的产品库存到盘点子表t_st_check_detail
			List<CheckPointDetailDto> prodCPDetailList = stockInfoDao.getStockProdView(dto.getWarehouseOid());
			if (prodCPDetailList != null) {
				for (CheckPointDetailDto cpDto : prodCPDetailList) {
					CheckPointDetail cpDetail = new CheckPointDetail();
					cpDetail.setCpMainOid(cpMain.getOid());
					cpDetail.setBomDetailOid(Long.parseLong(cpDto.getBomDetailOid()));
					cpDetail.setCurrentStockAmt(FilterUtil.filterString2Double(cpDto.getCurrentStockAmt()));
					cpDetail.setStockItemType(Constants.STOCK_ITEM_TYPE_PROD);
					
					stockInfoDao.saveObject(cpDetail);
				}
			}
			
		}
	}
	
	public ListResult<StockSearchDto> searchCheckPointByPaging(int pageNumber, int pageSize, StockSearchDto criteria) {
		IPaging paging = stockInfoDao.searchCheckPointByPaging(pageNumber, pageSize, criteria);
		ListResult<StockSearchDto> result = new ListResult<StockSearchDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			result.setRows(paging.getThisPageElements());
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	
	public void saveCPDetail(String cpMainOid, Map<String, CheckPointDetailDto> cpMap, String userId, boolean isConfirmCP) {
		if (!StringHelper.isEmpty(cpMainOid) && cpMap != null) {
			CheckPointMain cpMain = (CheckPointMain) stockInfoDao.getEntityByOid(CheckPointMain.class, Long.parseLong(cpMainOid));
			if (cpMain != null) {
				List<CheckPointDetail> cpDetails = stockInfoDao.findCPDetails(cpMain.getOid());
				List<CheckPointDetail> outstandingCPList = new ArrayList<CheckPointDetail>();
				
				for (CheckPointDetail cpDetail : cpDetails) {
					if (Constants.STOCK_ITEM_TYPE_MATERIAL.equals(cpDetail.getStockItemType())) { // 保存物料类型库存盘点
						String itemOid = cpDetail.getItemOid().toString();
						
						CheckPointDetailDto cpDetailDto = cpMap.get(itemOid);
						if (cpDetailDto != null) {
							if (!StringHelper.isEmpty(cpDetailDto.getActualStockAmt())) {
								cpDetail.setActualStockAmt(Double.parseDouble(cpDetailDto.getActualStockAmt()));
							}
							cpDetail.setRemark(cpDetailDto.getRemark());
							
							if (cpDetail.getCurrentStockAmt() != null && cpDetail.getActualStockAmt() != null) {
								if (cpDetail.getCurrentStockAmt().compareTo(cpDetail.getActualStockAmt()) != 0) {
									outstandingCPList.add(cpDetail);
								}
							}
						}			
					} else if (Constants.STOCK_ITEM_TYPE_PROD.equals(cpDetail.getStockItemType())) {
						
					}
				}
				
				cpMain.setLastModifyBy(userId);
				cpMain.setLastModifyDate(new Date());
				if (isConfirmCP) {
					cpMain.setStatus("1");
					balanceStock(cpMain, outstandingCPList); 
				}
				stockInfoDao.saveObject(cpMain);
			}
		}	
	}
	
	/**
	 * 平衡库存盘点物料库存量
	 * @param cpMain
	 * @param outstandingCPList
	 */
	private void balanceStock(CheckPointMain cpMain, List<CheckPointDetail> outstandingCPList) {		
		if (outstandingCPList != null) {
			StockType stockInType = (StockType) stockInfoDao.getEntityByOid(StockType.class, "code", "CP_PRO");
			StockType stockOutType = (StockType) stockInfoDao.getEntityByOid(StockType.class, "code", "CP_LOS");
			
			if (stockInType != null && stockOutType != null) {
				for (CheckPointDetail cpDetail : outstandingCPList) {
					StockInfo stockInfo = new StockInfo();
					stockInfo.setCreateBy("SYSTEMDUMMY");
					stockInfo.setCreateDate(new Date());
					stockInfo.setItemOid(cpDetail.getItemOid());
					stockInfo.setStatus("0");
					stockInfo.setStockDate(new Date());
					stockInfo.setStockNum("00000000");
					stockInfo.setStockPerson("SYSTEMDUMMY");
					stockInfo.setWarehouseOid(cpMain.getWarehouseOid());
					stockInfo.setUnitPrice(new Double("0"));
					stockInfo.setTotalPrice(new Double("0"));
					stockInfo.setStockItemType(Constants.STOCK_ITEM_TYPE_MATERIAL);
					
					if (cpDetail.getCurrentStockAmt().compareTo(cpDetail.getActualStockAmt()) > 0) {						
						stockInfo.setStockTypeOid(stockOutType.getOid());
						stockInfo.setStockAmt(cpDetail.getCurrentStockAmt() - cpDetail.getActualStockAmt());						
					} else {
						stockInfo.setStockTypeOid(stockInType.getOid());
						stockInfo.setStockAmt(cpDetail.getActualStockAmt() - cpDetail.getCurrentStockAmt());
					}
					
					stockInfoDao.saveObject(stockInfo);
				}
			}
		}
	}
	
	/**
	 * 平衡库存盘点物料库存量，stockItemType用于判断存的是物料类型还是产品类型
	 * @param cpMain
	 * @param outstandingCPList
	 * @param stockItemType
	 */
	private void balanceStock(CheckPointMain cpMain, List<CheckPointDetail> outstandingCPList, String stockItemType) {		
		if (outstandingCPList != null) {
			StockType stockInType = (StockType) stockInfoDao.getEntityByOid(StockType.class, "code", "CP_PRO");
			StockType stockOutType = (StockType) stockInfoDao.getEntityByOid(StockType.class, "code", "CP_LOS");
			
			if (stockInType != null && stockOutType != null) {
				for (CheckPointDetail cpDetail : outstandingCPList) {
					StockInfo stockInfo = new StockInfo();
					stockInfo.setCreateBy("SYSTEMDUMMY");
					stockInfo.setCreateDate(new Date());
					stockInfo.setStatus("0");
					stockInfo.setStockDate(new Date());
					stockInfo.setStockNum("00000000");
					stockInfo.setStockPerson("SYSTEMDUMMY");
					stockInfo.setUnitPrice(new Double("0"));
					stockInfo.setTotalPrice(new Double("0"));
					stockInfo.setWarehouseOid(cpMain.getWarehouseOid());
					if (Constants.STOCK_ITEM_TYPE_MATERIAL.equals(stockItemType)) {
						stockInfo.setItemOid(cpDetail.getItemOid());
						stockInfo.setStockItemType(Constants.STOCK_ITEM_TYPE_MATERIAL);
					} else if (Constants.STOCK_ITEM_TYPE_PROD.equals(stockItemType)) {
						stockInfo.setBomDetailOid(cpDetail.getBomDetailOid());
						stockInfo.setStockItemType(Constants.STOCK_ITEM_TYPE_PROD);
					}
					
					if (cpDetail.getCurrentStockAmt().compareTo(cpDetail.getActualStockAmt()) > 0) {						
						stockInfo.setStockTypeOid(stockOutType.getOid());
						stockInfo.setStockAmt(cpDetail.getCurrentStockAmt() - cpDetail.getActualStockAmt());						
					} else {
						stockInfo.setStockTypeOid(stockInType.getOid());
						stockInfo.setStockAmt(cpDetail.getActualStockAmt() - cpDetail.getCurrentStockAmt());
					}
					
					stockInfoDao.saveObject(stockInfo);
				}
			}
		}
	}

	public boolean isUnderCheckPointByWHS(Long whsOid) {
		return stockInfoDao.isUnderCheckPointByWHS(whsOid);
	}
	
	@Override
	public ListResult<JSONObject> findStockProdView(int pageNum, int pageSize, Map<String, String> searchMap) {
		return stockInfoDao.findStockProdView(pageNum, pageSize, searchMap);
	}
	
	@Override
	public void deleteCheckPointDetail(String cpMainIds) {
		stockInfoDao.deleteCheckPointDetail(cpMainIds);
	}
	
	@Override
	public List<CheckPointDetailDto> getProdCheckPointDetail(String whOid) {
		return stockInfoDao.getStockProdView(whOid);
	}
	
	@Override
	public List<CheckPointDetailDto> getCheckPointProd(String cpMainOid) {
		return stockInfoDao.getCheckPointProd(cpMainOid);
	}
	
	@Override
	public void setCPDetail(CheckPointMain cpMain, String itemsJsonStr,
				String prodsJsonStr, boolean isConfirmCP) {
		if (cpMain == null) {
			return;
		}
		
		// 将json字符转成对应对象组
		JSONArray itemJsonArr = JSONArray.fromObject(itemsJsonStr);
		JSONArray prodJsonArr = JSONArray.fromObject(prodsJsonStr);
		CheckPointDetail itemCPD = null;
		CheckPointDetail prodCPD = null;
		Map<Long, CheckPointDetail> itemCPDMap = new HashMap<>();
		Map<Long, CheckPointDetail> prodCPDMap = new HashMap<>();
		JSONObject jsonObj = null;
		
		for (int i = 0 ; i < itemJsonArr.size(); i++) {  
			jsonObj = itemJsonArr.getJSONObject(i);
			itemCPD = (CheckPointDetail) JSONObject.toBean(jsonObj, CheckPointDetail.class);
			if (itemCPD != null) {
				itemCPD.setCpMainOid(cpMain.getOid());
				itemCPD.setStockItemType(Constants.STOCK_ITEM_TYPE_MATERIAL);
				itemCPDMap.put(itemCPD.getOid(), itemCPD);
			}
		}
		for (int i = 0; i < prodJsonArr.size(); i++) {
			jsonObj = prodJsonArr.getJSONObject(i);
			prodCPD = (CheckPointDetail) JSONObject.toBean(jsonObj, CheckPointDetail.class);
			if (prodCPD != null) {
				prodCPD.setCpMainOid(cpMain.getOid());
				prodCPD.setStockItemType(Constants.STOCK_ITEM_TYPE_PROD);
				prodCPDMap.put(prodCPD.getOid(), prodCPD);
			}
		}
		
		// 获取当前盘点库存子表信息，将实际库存量和备注存入
		List<CheckPointDetail> cpDetails = stockInfoDao.findCPDetails(cpMain.getOid());
		List<CheckPointDetail> itemCPDList = new ArrayList<>();
		List<CheckPointDetail> prodCPDList = new ArrayList<>();
		for (CheckPointDetail cpd : cpDetails) {
			if (Constants.STOCK_ITEM_TYPE_MATERIAL.equals(cpd.getStockItemType())) {
				CheckPointDetail itemD = itemCPDMap.get(cpd.getOid());
				if (itemD != null) {
					cpd.setActualStockAmt(itemD.getActualStockAmt());
					cpd.setRemark(itemD.getRemark());
					itemCPDList.add(cpd);
				}
			} else if (Constants.STOCK_ITEM_TYPE_PROD.equals(cpd.getStockItemType())) {
				CheckPointDetail prodD = prodCPDMap.get(cpd.getOid());
				if (prodD != null) {
					cpd.setActualStockAmt(prodD.getActualStockAmt());
					cpd.setRemark(prodD.getRemark());
					prodCPDList.add(cpd);
				}
			}
		}
		
		// 保存库存盘点子表信息，若是确认盘点信息则进行库存平衡
		saveItemCPDetailAndBalance(itemCPDList, cpMain, isConfirmCP);
		saveProdCPDetailAndBalance(prodCPDList, cpMain, isConfirmCP);
		
		// 是否确认盘点信息 
		if (isConfirmCP) {
			cpMain.setStatus("1");
		}
		// 更新库存盘点主表最后修改人等信息
		stockInfoDao.saveObject(cpMain);
	}
	
	/**
	 * 保存物料库存盘点子表信息，若是确认盘点则进行库存补正
	 * @param list
	 */
	public void saveItemCPDetailAndBalance(List<CheckPointDetail> list, CheckPointMain cpMain, boolean isConfirm) {
		if (list == null) {
			return;
		}
		List<CheckPointDetail> outstandingCPList = new ArrayList<>();
		
		for (CheckPointDetail cp : list) {
			stockInfoDao.saveObject(cp);
			// 需要补正的库存信息
			if (cp.getCurrentStockAmt() != null && cp.getActualStockAmt() != null) {
				if (cp.getCurrentStockAmt().compareTo(cp.getActualStockAmt()) != 0) {
					outstandingCPList.add(cp);
				}
			}
		}
		if (isConfirm) {
			balanceStock(cpMain, outstandingCPList, Constants.STOCK_ITEM_TYPE_MATERIAL);
		}
	}
	
	/**
	 * 保存产品库存盘点子表信息，若是确认盘点则进行库存补正
	 * @param list
	 */
	public void saveProdCPDetailAndBalance(List<CheckPointDetail> list, CheckPointMain cpMain, boolean isConfirm) {
		if (list == null) {
			return;
		}
		List<CheckPointDetail> outstandingCPList = new ArrayList<>();
		
		for (CheckPointDetail cp : list) {
			stockInfoDao.saveObject(cp);
			// 需要补正的库存信息
			if (cp.getCurrentStockAmt() != null && cp.getActualStockAmt() != null) {
				if (cp.getCurrentStockAmt().compareTo(cp.getActualStockAmt()) != 0) {
					outstandingCPList.add(cp);
				}
			}
		}
		if (isConfirm) {
			balanceStock(cpMain, outstandingCPList, Constants.STOCK_ITEM_TYPE_PROD);
		}
	}
	
	@Override
	public boolean isCheckStockWarehouse(String warehouseOid) {
		return stockInfoDao.isCheckStockWarehouse(warehouseOid);
	}
	
	@Override
	public List<StockInfoDto> getCheckingStock(String stockIds) {
		return stockInfoDao.getCheckingStock(stockIds);
	}
	
}
