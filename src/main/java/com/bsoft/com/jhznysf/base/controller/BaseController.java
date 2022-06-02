package com.bsoft.com.jhznysf.base.controller;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 基本父类控制器
 * 
 * @author 丁俊敏
 */
public abstract class BaseController {
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	public ParameterMap<String, Object> getParameterMap() {
		return ParameterMap.create(getRequest());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	/**
	 * 得到当前登录的用户
	 */


	/**
	 * 得到项目路径
	 */
	public String getContextPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getContextPath();
	}
	
	/**
	 * ======================================================================================================
	   *   丁俊敏：上面的内容可以在各个项目通用，下面的内容是项目独用，仅为个人方便，不是很推荐如此偷懒，规范上SqlHelper应该在对应的serviceImpl中引用
	 * ======================================================================================================
	 */
	
	@Autowired
	SqlMapper sqlMapper;
	
	/**
	 * @return baseMapper
	 */
	@SuppressWarnings("unused")
	protected SqlMapper getSqlMapper(){
		return this.sqlMapper;
	}
	

}
