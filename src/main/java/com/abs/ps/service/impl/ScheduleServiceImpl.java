package com.abs.ps.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.abs.core.paging.IPaging;
import com.abs.core.util.DateHelper;
import com.abs.ps.dao.ScheduleDao;
import com.abs.ps.domain.Customer;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.ScheduleDetail;
import com.abs.ps.domain.ScheduleMain;
import com.abs.ps.service.AbstractService;
import com.abs.ps.service.ScheduleService;
import com.abs.ps.util.StringHelper;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ScheduleDetailDto;
import com.abs.ps.web.dto.ScheduleMainDto;

public class ScheduleServiceImpl extends AbstractService implements ScheduleService{
	private ScheduleDao scheduleDao;
	
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
		super.setIDao(scheduleDao);
	}
	
	public ListResult<ScheduleMainDto> findScheduleMainWithPaging(int pageNumber, int pageSize, ScheduleMainDto criteria) {
		/*IPaging paging = scheduleDao.findScheduleMainWithPaging(pageNumber, pageSize, criteria);
		
		ListResult<ScheduleMainDto> result = new ListResult<ScheduleMainDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {			
			List<ScheduleMainDto> dtoList = new ArrayList<ScheduleMainDto>();			
			
			Map<Long,ItemInfo> cacheMap = new HashMap<Long, ItemInfo>();
			for (int i = 0, j = paging.getThisPageElements().size(); i < j; i++) {
				ScheduleMain scheduleMain = (ScheduleMain) paging.getThisPageElements().get(i);
				ScheduleMainDto dto = new ScheduleMainDto();

				try {
					BeanUtils.copyProperties(dto, scheduleMain);
					
					ItemInfo itemInfo = cacheMap.get(scheduleMain.getItemOid());
					if (itemInfo == null) {
						itemInfo = (ItemInfo) scheduleDao.getEntityByOid(ItemInfo.class, scheduleMain.getItemOid());
						cacheMap.put(scheduleMain.getItemOid(), itemInfo);
					}
					if (itemInfo != null) {
						dto.setItemOid(itemInfo.getOid().toString());
						dto.setItemName(itemInfo.getName());
					}
					if (scheduleMain.getScheduleDate() != null) {
						dto.setScheduleDate(DateHelper.convert2String(scheduleMain.getScheduleDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getProjectDate() != null) {
						dto.setProjectDate(DateHelper.convert2String(scheduleMain.getProjectDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getMouldTgCompleteDate() != null) {
						dto.setMouldTgCompleteDate(DateHelper.convert2String(scheduleMain.getMouldTgCompleteDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getMouldActCompleteDate() != null) {
						dto.setMouldActCompleteDate(DateHelper.convert2String(scheduleMain.getMouldActCompleteDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getProdSignDate() != null) {
						dto.setProdSignDate(DateHelper.convert2String(scheduleMain.getProdSignDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getCreateDate() != null) {
						dto.setCreateDate(DateHelper.convert2String(scheduleMain.getCreateDate(), DateHelper.DATETIME_FORMATE));
					}
					if (scheduleMain.getLastModifyDate() != null) {
						dto.setLastModifyDate(DateHelper.convert2String(scheduleMain.getLastModifyDate(), DateHelper.DATETIME_FORMATE));
					}
					
					if (scheduleMain.getCustomerOid() != null) {
						Customer customer = (Customer) scheduleDao.getEntityByOid(Customer.class, scheduleMain.getCustomerOid());
						if (customer != null) {
							dto.setCustomerName(customer.getName());
						}
					}
					
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				dtoList.add(dto);
			}

			result.setRows(dtoList);
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));

		}
		return result;*/
		
		IPaging paging = scheduleDao.findScheduleMainWithPaging(pageNumber, pageSize, criteria);
		ListResult<ScheduleMainDto> result = new ListResult<ScheduleMainDto>();
		result.setResults("0");
		if (paging != null && paging.getThisPageElements() != null) {	
			result.setRows(paging.getThisPageElements());
			result.setResults(String.valueOf(paging.getTotalNumberOfElements()));
		}
		return result;
	}
	
	public List<ScheduleDetailDto> findScheduleDetailDtos(String scheduleOid) {
		List<ScheduleDetailDto> dtoList = new ArrayList<ScheduleDetailDto>();
		if (!StringHelper.isEmpty(scheduleOid) && !"undefined".equals(scheduleOid)) {
			List<ScheduleDetail> list = scheduleDao.getEntityListByOid(ScheduleDetail.class, "scheduleOid", Long.parseLong(scheduleOid));
			if (list != null) {
				
				for (ScheduleDetail detail : list) {
					ScheduleDetailDto dto = new ScheduleDetailDto();
					try {
						BeanUtils.copyProperties(dto, detail);
						
						dto.setScheduleOid(detail.getScheduleOid().toString());
						if (detail.getActualDate() != null) {
							dto.setActualDate(DateHelper.convert2String(detail.getActualDate(), DateHelper.DATE_FORMATE));
						}
						if (detail.getTryDate() != null) {
							dto.setTryDate(DateHelper.convert2String(detail.getTryDate(), DateHelper.DATE_FORMATE));
						}
						if (detail.getCreateDate() != null) {
							dto.setCreateDate(DateHelper.convert2String(detail.getCreateDate(), DateHelper.DATETIME_FORMATE));
						}
						
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dtoList.add(dto);
				}
			}
		}
		return dtoList;
	} 
	
	public ScheduleMainDto getScheduleByOid(String schedOid) {
		ScheduleMainDto dto = null;
		if (!StringHelper.isEmpty(schedOid) && !"undefined".equals(schedOid)) {
			ScheduleMain scheduleMain = (ScheduleMain) scheduleDao.getEntityByOid(ScheduleMain.class, Long.parseLong(schedOid));
			if (scheduleMain != null) {
				dto = new ScheduleMainDto();
				
				try {
					BeanUtils.copyProperties(dto, scheduleMain);
					
					ItemInfo itemInfo = (ItemInfo) scheduleDao.getEntityByOid(ItemInfo.class, scheduleMain.getItemOid());
					if (itemInfo != null) {
						dto.setItemOid(itemInfo.getOid().toString());
						dto.setItemName(itemInfo.getName());
					}
					if (scheduleMain.getScheduleDate() != null) {
						dto.setScheduleDate(DateHelper.convert2String(scheduleMain.getScheduleDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getProjectDate() != null) {
						dto.setProjectDate(DateHelper.convert2String(scheduleMain.getProjectDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getMouldTgCompleteDate() != null) {
						dto.setMouldTgCompleteDate(DateHelper.convert2String(scheduleMain.getMouldTgCompleteDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getMouldActCompleteDate() != null) {
						dto.setMouldActCompleteDate(DateHelper.convert2String(scheduleMain.getMouldActCompleteDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getProdSignDate() != null) {
						dto.setProdSignDate(DateHelper.convert2String(scheduleMain.getProdSignDate(), DateHelper.DATE_FORMATE));
					}
					if (scheduleMain.getCreateDate() != null) {
						dto.setCreateDate(DateHelper.convert2String(scheduleMain.getCreateDate(), DateHelper.DATETIME_FORMATE));
					}
					if (scheduleMain.getLastModifyDate() != null) {
						dto.setLastModifyDate(DateHelper.convert2String(scheduleMain.getLastModifyDate(), DateHelper.DATETIME_FORMATE));
					}
					if (scheduleMain.getCustomerOid() != null) {
						Customer customer = (Customer) scheduleDao.getEntityByOid(Customer.class, scheduleMain.getCustomerOid());
						if (customer != null) {
							dto.setCustomerName(customer.getName());
						}
					}
					
					List<ScheduleDetail> details = scheduleDao.getEntityListByOid(ScheduleDetail.class, "scheduleOid", Long.parseLong(schedOid));
					if (details != null) {
						List<ScheduleDetailDto> list = new ArrayList<ScheduleDetailDto>();
						
						for (ScheduleDetail detail : details) {
							ScheduleDetailDto detailDto = new ScheduleDetailDto();
							BeanUtils.copyProperties(detailDto, detail);
							
							detailDto.setScheduleOid(detail.getScheduleOid().toString());
							if (detail.getActualDate() != null) {
								detailDto.setActualDate(DateHelper.convert2String(detail.getActualDate(), DateHelper.DATE_FORMATE));
							}
							if (detail.getTryDate() != null) {
								detailDto.setTryDate(DateHelper.convert2String(detail.getTryDate(), DateHelper.DATE_FORMATE));
							}
							if (detail.getCreateDate() != null) {
								detailDto.setCreateDate(DateHelper.convert2String(detail.getCreateDate(), DateHelper.DATETIME_FORMATE));
							}

							list.add(detailDto);
						}	
						dto.setDetails(list);
					}
					
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return dto;
	}
	
	public static void main(String[] argu) throws Exception {
		ScheduleMainDto dto = new ScheduleMainDto();		
		ScheduleMain scheduleMain = new ScheduleMain();
		scheduleMain.setOid(new Long(1));
		scheduleMain.setCreateBy("HUANGWI2");
		scheduleMain.setCreateDate(new Date());
		scheduleMain.setName("Test");
		
		BeanUtils.copyProperties(dto, scheduleMain);
		System.out.println("dto = " + dto);
	}	
}
