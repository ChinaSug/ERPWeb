/**
 * 
 */
package com.abs.ps.dao;

import java.util.List;

import com.abs.core.paging.IPaging;
import com.abs.ps.domain.BomDetail;
import com.abs.ps.web.dto.BomMainDto;


/**
 * @author zhengxy
 *
 */
public interface BomInfoDao extends IDao {
	public IPaging findBomMainByPaging(int pageNumber, int pageSize, BomMainDto criteria);
	public List<BomDetail> findBomDetailDtoBybomNum(String bomNum);
	public BomDetail getBomDeatilByOid(long oid);
}
