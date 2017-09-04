/**
 * 
 */
package com.abs.ps.service;

import java.util.List;

import com.abs.ps.web.dto.BomDetailDto;
import com.abs.ps.web.dto.BomMainDto;
import com.abs.ps.web.dto.ListResult;

/**
 * @author zhengxy
 *
 */
public interface BomInfoService extends IService {
	public ListResult<BomMainDto> findBomMainByPaging(int pageNumber, int pageSize, BomMainDto criteria);
	public List<BomDetailDto> findSBomDetailDtos(String bomOid);
	public BomMainDto getBomByOid(String bomOid);
	public List<BomDetailDto> findSBomDetailDtosBybomNum(String bomNum);
	public List<BomDetailDto> findBomDetailById(String oid);
}

