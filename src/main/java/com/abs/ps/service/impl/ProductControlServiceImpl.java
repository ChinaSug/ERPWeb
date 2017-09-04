package com.abs.ps.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.abs.core.paging.IPaging;
import com.abs.core.util.DateHelper;
import com.abs.ps.dao.ProductControlDao;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.domain.BomMain;
import com.abs.ps.domain.Department;
import com.abs.ps.domain.ItemInfo;
import com.abs.ps.domain.Machine;
import com.abs.ps.domain.Mould;
import com.abs.ps.domain.ProductControlDetail;
import com.abs.ps.domain.ProductControlMain;
import com.abs.ps.service.AbstractService;
import com.abs.ps.service.ProductControlService;
import com.abs.ps.util.FilterUtil;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ProductControlDetailDto;
import com.abs.ps.web.dto.ProductControlMainDto;

public class ProductControlServiceImpl extends AbstractService implements ProductControlService{
	private ProductControlDao productControlDao;
	
	public void setProductControlDao(ProductControlDao productControlDao) {
		this.productControlDao = productControlDao;
		super.setIDao(productControlDao);
	}
	
	public ListResult<ProductControlMainDto> findProductControlMainWithPaging(int pageNumber, int pageSize, ProductControlMainDto criteria) {
		IPaging paging = productControlDao.findProductControlMainWithPaging(pageNumber, pageSize, criteria);
		
		ListResult<ProductControlMainDto> result = new ListResult<ProductControlMainDto>();
		result.setResults("0");
		result.setRows(paging.getThisPageElements());
		result.setResults(String.valueOf(paging.getTotalNumberOfElements()));

		return result;
	}
	
	public ProductControlMainDto getProductControlByPcOid(Long pcOid) {
		ProductControlMain pcMain = (ProductControlMain) productControlDao.getEntityByOid(ProductControlMain.class, pcOid);
		ProductControlMainDto dto = FilterUtil.convertObjectClass(pcMain, ProductControlMainDto.class);
		if (pcMain != null) {
			if (pcMain.getMachineOid() != null) {
				Machine machine = (Machine) productControlDao.getEntityByOid(Machine.class, pcMain.getMachineOid());
				if (machine != null) {
					dto.setMachineOid(machine.getOid().toString());
					dto.setMachineName(machine.getName());
				}
			}
			
			if (pcMain.getDepartOid() != null) {
				Department depart = (Department) productControlDao.getEntityByOid(Department.class, pcMain.getDepartOid());
				if (depart != null) {
					dto.setDepartOid(depart.getOid().toString());
					dto.setDepartName(depart.getDepartName());
				}
			}
			
			if (pcMain.getMouldOid() != null) {
				Mould mould = (Mould) productControlDao.getEntityByOid(Mould.class, pcMain.getMouldOid());
				if (mould != null) {
					dto.setMouldOid(mould.getOid().toString());
					dto.setMouldName(mould.getName());
				}
			}
			
			if (pcMain.getItemOid() != null) {
				ItemInfo item = (ItemInfo) productControlDao.getEntityByOid(ItemInfo.class, pcMain.getItemOid());
				if (item != null) {
					dto.setItemCode(item.getCode());
					dto.setItemColor(item.getColor());
				}
			}
			
			if (pcMain.getPublishDate() != null) {
				dto.setPublishDate(DateHelper.convert2String(pcMain.getPublishDate(), DateHelper.DATE_FORMATE));
			}
			
			if (pcMain.getPcDate() != null) {
				dto.setPcDate(DateHelper.convert2String(pcMain.getPcDate(), DateHelper.DATE_FORMATE));
			}
			
			if (pcMain.getTargetStartDate() != null) {
				dto.setTargetStartDate(DateHelper.convert2String(pcMain.getTargetStartDate(), DateHelper.DATE_FORMATE));
			}
			
			if (pcMain.getTargetEndDate() != null) {
				dto.setTargetEndDate(DateHelper.convert2String(pcMain.getTargetEndDate(), DateHelper.DATE_FORMATE));
			}
			
			if (pcMain.getCreateDate() != null) {
				dto.setCreateDate(DateHelper.convert2String(pcMain.getCreateDate(), DateHelper.DATE_FORMATE));
			}
			
			if (pcMain.getLastModifyDate() != null) {
				dto.setLastModifyDate(DateHelper.convert2String(pcMain.getLastModifyDate(), DateHelper.DATE_FORMATE));
			}
			
			BomDetail bomDetail = (BomDetail) productControlDao.getEntityByOid(BomDetail.class, pcMain.getBomDetailOid());
			if (bomDetail != null && bomDetail.getOid() > 0) {
				dto.setProdId(bomDetail.getProdId());
				
				BomMain bomMain = (BomMain) productControlDao.getEntityByOid(BomMain.class, bomDetail.getBomOid());
				if (bomMain != null && bomMain.getOid() > 0) {
					dto.setBomNum(bomMain.getBomNum());
				}
			}
			
			List details = productControlDao.getEntityListByOid(ProductControlDetail.class, "pcOid", pcOid);
			List<ProductControlDetailDto> detailList = new ArrayList<ProductControlDetailDto>();
			if (details != null) {
				for (Object obj : details) {
					ProductControlDetail pcDetail = (ProductControlDetail) obj;
					ProductControlDetailDto detailDto = new ProductControlDetailDto();
					try {
						BeanUtils.copyProperties(detailDto, pcDetail);
						
						if (pcDetail.getProduceDate() != null) {
							detailDto.setProduceDate(DateHelper.convert2String(pcDetail.getProduceDate(), DateHelper.DATE_FORMATE));
						}
						
						if (pcDetail.getCreateDate() != null) {
							detailDto.setCreateDate(DateHelper.convert2String(pcDetail.getCreateDate(), DateHelper.DATE_FORMATE));
						}
						
						if (pcDetail.getLastModifyDate() != null) {
							detailDto.setLastModifyDate(DateHelper.convert2String(pcDetail.getLastModifyDate(), DateHelper.DATE_FORMATE));
						}
						
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					detailList.add(detailDto);
				}
				dto.setDetailDtos(detailList);
			}
		}
		
		return dto;
	}
	
	
	public List<ProductControlDetailDto> findProductControlDetails(String pcOid) {
		List<ProductControlDetail> details = productControlDao.getEntityListByOid(ProductControlDetail.class, "pcOid", Long.parseLong(pcOid));
		List<ProductControlDetailDto> detailList = new ArrayList<ProductControlDetailDto>();
		if (details != null) {
			for (ProductControlDetail pcDetail : details) {
				ProductControlDetailDto detailDto = new ProductControlDetailDto();
				try {
					BeanUtils.copyProperties(detailDto, pcDetail);
					
					if (pcDetail.getProduceDate() != null) {
						detailDto.setProduceDate(DateHelper.convert2String(pcDetail.getProduceDate(), DateHelper.DATE_FORMATE));
					}
					
					if (pcDetail.getCreateDate() != null) {
						detailDto.setCreateDate(DateHelper.convert2String(pcDetail.getCreateDate(), DateHelper.DATE_FORMATE));
					}
					
					if (pcDetail.getLastModifyDate() != null) {
						detailDto.setLastModifyDate(DateHelper.convert2String(pcDetail.getLastModifyDate(), DateHelper.DATE_FORMATE));
					}
					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				detailList.add(detailDto);
			}
		}
		return detailList;
	}
	
	@Override
	public List<Object> getWeekPlanTable(int year, int month, int week) {
		return productControlDao.getWeekPlanTable(year, month, week);
	}
	
	@Override
	public boolean isExceedProdAmt(long pcmOid) {
		return productControlDao.isExceedProdAmt(pcmOid);
	}
	
	@Override
	public ListResult<ProductControlDetailDto> findPCDetailPaging(
			int pageNumber, int pageSize, long pcmOid) {
		return productControlDao.findPCDetailPaging(pageNumber, pageSize, pcmOid);
	}
	
	@Override
	public JSONObject getNewlyWeekPlanTable(int year, int month, int week) {
		return productControlDao.getNewlyWeekPlanTable(year, month, week);
	}
}
