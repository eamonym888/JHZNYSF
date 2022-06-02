package com.bsoft.com.jhznysf.mapperJYZYY;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
//3.2.2	职工字典
@Mapper
public interface YGDMMapper extends SqlMapper {

    @Select("SELECT GX.MRZ AS ORGAN_CODE,GY.YGDM,GY.YGBH,GY.YGXM,GY.PYDM,GY.SFZ,GY.YGXB,decode(GY.YGXB,1,'男','女') AS SEX_NAME, \n" +
            "GY.YGJB AS TITLE_CODE,GD.DMMC TITLE_NAME,GY.SJHM,GY.YXDZ AS EMAIL,GY.CSNY,GY.YGJS AS STAFF_BRIEFING, \n" +
            "GY.YSJJ AS GOOD_DESCR,GY.KSDM AS SUBOR_DEPT_CODE,GK.KSMC AS SUBOR_DEPT_NAME,DECODE(GY.ZFPB,0,'1','0') AS ZFPB \n" +
            "FROM GY_YGDM GY\n" +
            "LEFT JOIN GY_DMZD GD ON DMLB=27 AND GD.DMSB=GY.YGJB\n" +
            "LEFT JOIN GY_KSDM GK ON GK.KSDM=GY.KSDM\n" +
            "LEFT JOIN GY_XTCS GX ON GX.CSMC='YLJGDM' ")
    List<PageData> getStaffDict(PageData pd) ;

}
