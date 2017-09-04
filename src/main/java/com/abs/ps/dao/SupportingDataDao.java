package com.abs.ps.dao;

import java.util.List;

import com.abs.ps.web.dto.NameCodeDto;

public interface SupportingDataDao {
	public List<NameCodeDto> findSupportingDataByType(String dataType);
	
	/**
	 * 通过数据名称与数据类型获得唯一Oid
	 * 
	 * @param dataName
	 * @param dataType
	 * @return
	 */
	public Long getSupportDataOid(String dataName, String dataType);
	
	/**
	 * 获取园区可查询哪些园区信息的园区Oid配置列表
	 * 
	 * @param orgOid
	 * @return
	 */
	public List<Long> getOrgConfList(long orgOid);
	
	/**
	 * 通过type和value获取数据类型
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public List<NameCodeDto> findSupportingData(String type, String value);
}
