package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.19	手术记录
@Mapper
public interface SSJLMapper extends SqlMapper {

    @Select(" <script>SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "zb.brid as PAT_INDEX_NO,\n" +
            "'' as OUTHOSP_NO,'' as OUTHOSP_SERIAL_NO,\n" +
            "zb.ZYHM as INHOSP_NO,\n" +
            "(SELECT yzj.zycs FROM YS_ZY_JZJL YZJ where YZJ.JZHM=ssj.ZYH) as INHOSP_NUM,\n" +
            "ssj.zyh as INHOSP_SERIAL_NO,\n" +
            "ssj.ssbh as SURGERY_NO,\n" +
            "'' as SURGERY_SEQ_NO,\n" +
            "gs.ssdm as SURGERY_OPER_CODE,\n" +
            "gs.ssmc as SURGERY_OPER_NAME,\n" +
            "gs.ssdj as SURGERY_LEVEL_CODE,\n" +
            "(SELECT gsj.djmc FROM gy_ssdj gsj where gsj.ssdj=gs.ssdj) as SURGERY_LEVEL_NAME,\n" +
            "'1' as SURGERY_WOUND_CATEG_CODE,\n" +
            "'0类切口' as SURGERY_WOUND_CATEG_NAME,\n" +
            "'9' as WOUND_HEALING_LEVEL_CODE,\n" +
            "'其他' as WOUND_HEALING_LEVEL_NAME,\n" +
            "ssj.kssj as SURGERY_BEGIN_DATE,\n" +
            "ssj.jssj as SURGERY_END_DATE,\n" +
            "ssj.ssys as SURGERY_DR_CODE,\n" +
            "(SELECT gy.ygxm FROM gy_ygdm gy where gy.ygdm=ssj.ssys) as SURGERY_DR_NAME,\n" +
            "AP.MZDM as ANES_METHOD_CODE,\n" +
            "(SELECT DMMC FROM GY_DMZD gd WHERE gd.DMLB = 28 AND gd.DMSB = AP.MZDM) as ANES_METHOD_NAME,\n" +
            "AP.MZYS as ANES_DR_CODE,\n" +
            "(SELECT gy.ygxm FROM gy_ygdm gy where gy.ygdm=AP.MZYS) as ANES_DR_NAME\n" +
            "FROM sm_ssjl ssj\n" +
            "left join zy_brry zb on zb.zyh=ssj.zyh\n" +
            "left join gy_ssdm gs on gs.ssnm=ssj.ssnm\n" +
            "left join SM_SSAP AP on ssj.SSBH=AP.SSBH \n"+
            "where ssj.zyh = #{inhospSerialNo} </script>")
    List<PageData> getSurgeryRecord(PageData pd) ;


}
