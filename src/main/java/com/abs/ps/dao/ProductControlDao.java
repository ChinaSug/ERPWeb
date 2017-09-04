package com.abs.ps.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.abs.core.paging.IPaging;
import com.abs.ps.web.dto.ListResult;
import com.abs.ps.web.dto.ProductControlDetailDto;
import com.abs.ps.web.dto.ProductControlMainDto;

public interface ProductControlDao extends IDao{
	public IPaging findProductControlMainWithPaging(int pageNumber, int pageSize, ProductControlMainDto criteria);
	
	/**
	 * 获取周计划表
	 * 
	 * @param year
	 * @param month
	 * @param week
	 * @return
	 */
	public List<Object> getWeekPlanTable(int year, int month, int week);
	
	/**
	 * 判断生产控制单实际生产数是否超过要生产数
	 * 
	 * @param pcmOid
	 * @return
	 */
	public boolean isExceedProdAmt(long pcmOid);
	
	/**
	 * 获取生产控制子表分页数据
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public ListResult<ProductControlDetailDto> findPCDetailPaging(int pageNumber, int pageSize, long pcmOid);
	
	/**
	 * 获取周计划表的数据，返回格式为
	 * {
			机台：{				机器名称
				部番：{				产品id
					穴数：				hole
					周期：				period
					生产数：				prodAmt
					生产时间：{				prodTime
						时间：{				2017-01-01
							计划生产量：			planNum
							实际生产量：			actAmt
							加工良品数：			goodAmt
							不良数：				badAmt
						},
						...
					}
				},
				...
			},
			...
		}
	 * 
	 * @param year
	 * @param month
	 * @param week
	 * @return
	 */
	public JSONObject getNewlyWeekPlanTable(int year, int month, int week);
}
