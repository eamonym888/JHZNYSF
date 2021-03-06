package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.*;

import java.util.List;
//3.2.1	科室字典
@Mapper
public interface KSDMMapper extends SqlMapper {

    @Select(" SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,GK.PLSX,GK.KSDM,GK.KSMC,GK.KSMC,GK.PYDM,decode(GK.MZSY,'Y',1,2) AS OI_DEPT_FLAG,\n" +
            "GK.SJKS,GK1.KSMC AS SJKSMC,GK.KSDZ AS KSMS,1 AS ZFBZ  \n" +
            "FROM GY_KSDM GK\n" +
            "left join GY_KSDM GK1 on GK.SJKS = GK1.KSDM \n" +
            "WHERE GK.MZSY='Y' OR GK.ZYSY='Y' ")
    List<PageData> getDeptDict(PageData pd) ;

}
