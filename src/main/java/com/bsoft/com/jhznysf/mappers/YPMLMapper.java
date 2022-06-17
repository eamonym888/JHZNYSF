package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
//3.2.4	药品字典
@Mapper
public interface YPMLMapper extends SqlMapper {

    @Select("SELECT GX.MRZ AS ORGAN_CODE,YY.YPXH,YY.YPDM,YY.YPMC,YY.PYDM \n" +
            "FROM YK_YPML YY,GY_XTCS GX \n" +
            "WHERE GX.CSMC='YLJGDM' ")
    List<PageData> getDrugDict(PageData pd) ;
}
