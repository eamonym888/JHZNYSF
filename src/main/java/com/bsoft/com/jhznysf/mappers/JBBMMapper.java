package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
//3.2.3	疾病字典
@Mapper
public interface JBBMMapper extends SqlMapper {

    @Select(" SELECT GX.MRZ AS ORGAN_CODE,GJ.JBXH,ICD10 AS DIAG_CODE,GJ.JBMC,GJ.PYDM\n" +
            "FROM GY_JBBM GJ,GY_XTCS GX \n" +
            "WHERE GX.CSMC='YLJGDM_NEW' AND GJ.ZFPB=0 ")
    List<PageData> getDiagDict(PageData pd) ;
}
