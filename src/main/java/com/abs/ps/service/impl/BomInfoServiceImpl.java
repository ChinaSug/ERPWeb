package com.abs.ps.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.abs.core.paging.IPaging;
import com.abs.ps.dao.BomInfoDao;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.domain.BomMain;
import com.abs.ps.domain.Customer;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.Mould;
import com.abs.ps.domain.ScheduleMain;
import com.abs.ps.service.AbstractService;
import com.abs.ps.service.BomInfoService;
import com.abs.ps.util.DateHelper;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.BomDetailDto;
import com.abs.ps.web.dto.BomMainDto;
import com.abs.ps.web.dto.ListResult;

public class BomInfoServiceImpl extends AbstractService implements BomInfoService {
	private BomInfoDao bomInfoDao;
	
	public void setBomInfoDao(BomInfoDao bomInfoDao) {
		this.bomInfoDao = bomInfoDao;
		super.setIDao(bomInfoDao);
	}

	@Override
	public ListResult<BomMainDto> findBomMainByPaging(int pageNumber, int pageSize,
			BomMainDto criteria) {
		IPaging paging = bomInfoDao.findBomMainByPaging(pageNumber, pageSize, criteria);
		ListResult<BomMainDto> result = new ListResult<BomMainDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {	
			result.setRows(paging.getThisPageElements());
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}

	@Override
	public List<BomDetailDto> findSBomDetailDtos(String bomOid) {
		List<BomDetailDto> dtoList = new ArrayList<BomDetailDto>();
		Map<Long, ItemInfo> map = new HashMap<Long, ItemInfo>();
		Map<Long,Mould> mapm = new HashMap<Long,Mould>();
		if (!StringHelper.isEmpty(bomOid)) {
			List<BomDetail> list = bomInfoDao.getEntityListByOid(BomDetail.class, "bomOid", Long.parseLong(bomOid));
			if (list != null) {
				
				for (BomDetail detail : list) {
					BomDetailDto dto = FilterUtil.convertObjectClass(detail, BomDetailDto.class);
					
					ItemInfo item = map.get(detail.getItemOid());
					if (item== null) {
						item = (ItemInfo) bomInfoDao.getEntityByOid(ItemInfo.class, detail.getItemOid());
						map.put(detail.getItemOid(), item);
					}
					
					if (item != null) {
						dto.setItemName(item.getName());
						dto.setModel(item.getModel());
						dto.setItemColor(item.getColor());
						dto.setItemCode(item.getCode());
					}
					
					Mould mould = mapm.get(detail.getMouldOid());
					if (mould == null) {
						mould = (Mould) bomInfoDao.getEntityByOid(Mould.class, detail.getMouldOid());
					}
					if (mould != null) {
						mapm.put(detail.getMouldOid(), mould);
						dto.setMouldName(mould.getName());
					}
					
					//设置合金的值是相对应数据的值
					ItemInfo alloyItem = (ItemInfo) bomInfoDao.getEntityByOid(ItemInfo.class, detail.getAlloyOid());
					if (alloyItem != null) {
						dto.setAlloyName(alloyItem.getName());
					}
					//设置油墨的值是相对应数据的值
					ItemInfo pinkItem = (ItemInfo) bomInfoDao.getEntityByOid(ItemInfo.class, detail.getPinkOid());
					if (pinkItem != null) {
						dto.setPinkName(pinkItem.getName());
					}
					
					if (detail.getCreateDate() != null) {
						dto.setCreateDate(DateHelper.convert2String(detail.getCreateDate(), DateHelper.DATE_FORMATE));
					}
						
					dtoList.add(dto);
				}
			}
		}
		return dtoList;
		
	}
	
	public BomMainDto getBomByOid(String bomOid) {
		BomMainDto dto = null;
		Map<Long, ItemInfo> map = new HashMap<Long, ItemInfo>();
		Map<Long, Customer> mapc = new HashMap<Long, Customer>();
		Map<Long,Mould> mapm = new HashMap<Long,Mould>();
		if (!StringHelper.isEmpty(bomOid)) {
			BomMain bomMain = (BomMain) bomInfoDao.getEntityByOid(BomMain.class, Long.parseLong(bomOid));
			if (bomMain != null) {
				dto = new BomMainDto();
				
				try {
					BeanUtils.copyProperties(dto, bomMain);
					Customer cust = mapc.get(bomMain.getCustomerOid());
					if (cust== null) {
						cust = (Customer) bomInfoDao.getEntityByOid(Customer.class, bomMain.getCustomerOid());
						mapc.put(bomMain.getCustomerOid(), cust);
					}
					if(bomMain.getCustomerOid()!=0){
						dto.setCustomerName(cust.getName());
					}
					
					
					if (bomMain.getCreateDate() != null) {
						dto.setCreateDate(DateHelper.convert2String(bomMain.getCreateDate(), DateHelper.DATETIME_FORMATE));
					}
					if (bomMain.getLastModifyDate() != null) {
						dto.setLastModifyDate(DateHelper.convert2String(bomMain.getLastModifyDate(), DateHelper.DATETIME_FORMATE));
					}
					
					
					List<BomDetail> details = bomInfoDao.getEntityListByOid(BomDetail.class, "bomOid", Long.parseLong(bomOid));
					if (details != null) {
						List<BomDetailDto> list = new ArrayList<BomDetailDto>();
						
						for (BomDetail detail : details) {
							BomDetailDto detailDto = FilterUtil.convertObjectClass(detail, BomDetailDto.class);
							//通过itemOid的值拿到数据
							ItemInfo item = map.get(detail.getItemOid());
							if (item == null) {
								item = (ItemInfo) bomInfoDao.getEntityByOid(ItemInfo.class, detail.getItemOid());
								
								if (item != null) {
									map.put(detail.getItemOid(), item);
								}
							}
							
							if(item != null){
								detailDto.setItemColor(item.getColor());
								detailDto.setItemCode(item.getCode());
								detailDto.setItemName(item.getName());
								detailDto.setModel(item.getModel());
							}
							
							//设置合金的值是相对应数据的值
							ItemInfo alloyItem = (ItemInfo) bomInfoDao.getEntityByOid(ItemInfo.class, detail.getAlloyOid());
							if (alloyItem != null) {
								detailDto.setAlloyName(alloyItem.getName());
							}
							//设置油墨的值是相对应数据的值
							ItemInfo pinkItem = (ItemInfo) bomInfoDao.getEntityByOid(ItemInfo.class, detail.getPinkOid());
							if (pinkItem != null) {
								detailDto.setPinkName(pinkItem.getName());
							}

							Mould mould = mapm.get(detail.getMouldOid());
							if (mould == null) {
								mould = (Mould) bomInfoDao.getEntityByOid(Mould.class, detail.getMouldOid());
								if (mould != null) {
									mapm.put(detail.getMouldOid(), mould);
									detailDto.setMouldName(mould.getName());
								}
							}
							
							if (detail.getCreateDate() != null) {
								dto.setCreateDate(DateHelper.convert2String(detail.getCreateDate(), DateHelper.DATE_FORMATE));
							}
							
							list.add(detailDto);
						}	
						dto.setDetails(list);
					}
					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
			}
		}
		return dto;
	}
	
	@Override
	public List<BomDetailDto> findSBomDetailDtosBybomNum(String bomNum) {
		List<BomDetailDto> dtoList = new ArrayList<BomDetailDto>();
		Map<Long, ItemInfo> map = new HashMap<Long, ItemInfo>();
		Map<Long,Mould> mapm = new HashMap<Long,Mould>();
		if (!StringHelper.isEmpty(bomNum)) {
			List<BomDetail> list = bomInfoDao.findBomDetailDtoBybomNum(bomNum);
			if (list != null) {
				for (BomDetail detail : list) {
					BomDetailDto dto = FilterUtil.convertObjectClass(detail, BomDetailDto.class);
					
					ItemInfo item = map.get(detail.getItemOid());
					if (item == null) {
						item = (ItemInfo) bomInfoDao.getEntityByOid(ItemInfo.class, detail.getItemOid());
						map.put(detail.getItemOid(), item);
					}
					
					if(item != null){
						dto.setItemName(item.getName());
						dto.setModel(item.getModel());
						dto.setItemColor(item.getColor());
						dto.setItemCode(item.getCode());
					}
					
					Mould mould = mapm.get(detail.getMouldOid());
					if (mould == null) {
						mould = (Mould) bomInfoDao.getEntityByOid(Mould.class, detail.getMouldOid());
						mapm.put(detail.getMouldOid(), mould);
					}
					if (mould != null) {
						dto.setMouldName(mould.getName());
					}
					if (detail.getCreateDate() != null) {
						dto.setCreateDate(DateHelper.convert2String(detail.getCreateDate(), DateHelper.DATE_FORMATE));
					}
						
					dtoList.add(dto);
				}
			}
		}
		return dtoList;
	}

	@Override
	public List<BomDetailDto> findBomDetailById(String oid) {
		List<BomDetailDto> dtoList = new ArrayList<BomDetailDto>();
		Map<Long, ItemInfo> map = new HashMap<Long, ItemInfo>();
		Map<Long,Mould> mapm = new HashMap<Long,Mould>();
		if (!StringHelper.isEmpty(oid)) {
			List<BomDetail> list = bomInfoDao.getEntityListByOid(BomDetail.class, "oid", Long.parseLong(oid));
			if (list != null) {
				for (BomDetail detail : list) {
					BomDetailDto dto = new BomDetailDto();
					try {
						ItemInfo item = map.get(detail.getItemOid());
						if (item == null) {
							item = (ItemInfo) bomInfoDao.getEntityByOid(ItemInfo.class, detail.getItemOid());
							
							map.put(detail.getItemOid(), item);
						}
						if(item != null){
							dto.setItemName(item.getName());
							dto.setModel(item.getModel());
							dto.setItemColor(item.getColor());
						}
						
						Mould mould = mapm.get(detail.getMouldOid());
						if (mould== null) {
							mould = (Mould) bomInfoDao.getEntityByOid(Mould.class, detail.getMouldOid());
							mapm.put(detail.getMouldOid(), mould);
						}
						dto.setMouldName(mould.getName());
						if (detail.getCreateDate() != null) {
							dto.setCreateDate(DateHelper.convert2String(detail.getCreateDate(), DateHelper.DATE_FORMATE));
						}
						
						BeanUtils.copyProperties(dto, detail);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					dtoList.add(dto);
				}
			}
		}
		return dtoList;
	}
	
	
}
