package com.bsoft.com.jhznysf.base.mapper;

import com.bsoft.com.jhznysf.utils.PageData;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
  *  一个很不优雅，且比较粗陋，勉强还算实用的sql帮助类
 * 
 * @author 丁俊敏
 */
@Mapper
public interface SqlMapper {

	/**
	 * 按照输入的参数原样执行sql，并返回数据，不推荐使用。
	 */
	@Deprecated
	@SelectProvider(type = SqlMapperProvider.class, method = "excuteSql")
	List<PageData> excuteSql(@Param("sql") String sql);

	/**
	 * 按照输入的列名、表名、条件执行sql，并返回单条数据
	 */
	@SelectProvider(type = SqlMapperProvider.class, method = "selectOne")
	PageData selectOne(@Param("columnName") String columnName, @Param("tableName") String tableName,
					   @Param("condition") String condition);

	/**
	 * 按照输入的列名、表名、条件执行sql，并返回多条数据
	 */
	@SelectProvider(type = SqlMapperProvider.class, method = "selectAll")
	List<PageData> selectAll(@Param("columnName") String columnName, @Param("tableName") String tableName,
							 @Param("condition") String condition);

}
