package com.bsoft.com.jhznysf.base.service;


import com.bsoft.com.jhznysf.utils.PageData;

import net.minidev.json.JSONObject;

import java.util.List;

public interface IBaseService {

	/**
	 * 获取数据列表数据
	 */
	JSONObject getTablePageData(Integer page, Integer limit, PageData pd);

	/**
	 * 插入/更新数据
	 */
	void insertOrUpdate(PageData pd);


	
	/**
	 * 更新所有数据
	 */
	void updateAll(PageData pd);

	/**
	 * 删除数据
	 */
	void delete(PageData pd);

	/**
	 * 获取单个数据
	 */
	PageData get(PageData pd);

	/**
	 * 获取所有数据
	 */
	List<PageData> getAll(PageData pd);

}
