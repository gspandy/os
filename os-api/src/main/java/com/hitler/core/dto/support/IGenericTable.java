package com.hitler.core.dto.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.hitler.core.repository.AggregateExpression;

/**
 * @author Lane
 * 
 * 通用表格接口
 */
public interface IGenericTable<DTO, E> extends AggregateExpression<E>, Serializable {
 
	List<DTO> getData();
	
	/**
	 * @param data
	 */
	void setData(List<DTO> data);
	
	/**
	 * 
	 * @return
	 */
	Long getRecordsTotal();
	
	/**
	 * 设置总记录数
	 */
	void setRecordsTotal(Long recordsTotal);
	
	/**
	 * 获取总记录数
	 */
	Long getRecordsFiltered();
	
	/**
	 * 设置总记录数
	 */
	void setRecordsFiltered(Long recordsFiltered);
	
	/**
	 * 转为Map对象
	 */
	Map<String, Object> map() throws IllegalAccessException, InvocationTargetException;
	
}
