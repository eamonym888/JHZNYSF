package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.27	诊断信息-门诊
//3.2.28	诊断信息-住院
@Mapper
public interface ZDXXMapper extends SqlMapper {

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "JBZD.Brbh as PAT_INDEX_NO,\n" +
            "BRDA.MZHM AS OUTHOSP_NO,\n" +
            "JBZD.JZXH AS OUTHOSP_SERIAL_NO,\n" +
            "JBZD.JLBH AS DIAG_INDEX_NO,\n" +
            "JBZD.ZDSJ AS DIAG_DATE,\n" +
            "JBZD.ZDXH AS DIAG_NO,\n" +
            "JBZD.ZDLB AS DIAG_TYPE_CODE,\n" +
            "'门（急）诊诊断' AS DIAG_TYPE_NAME,\n" +
            "JBZD.ICD AS DIAG_CODE,\n" +
            "JBZD.MSZD AS DIAG_NAME,\n" +
            "JBZD.FJMC AS DIAG_INTRODUCTION\n" +
            "FROM YS_MZ_JBZD JBZD,MS_BRDA BRDA\n" +
            "where JBZD.BRBH=BRDA.BRID \n"+
            "and JBZD.JZXH = #{outhospSerialNo} </script>")
    List<PageData> getOuthospDiag(PageData pd) ;

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "JBZD.Brbh as PAT_INDEX_NO,\n" +
            "ry.zyhm AS INHOSP_NO,\n" +
            "YZJ.ZYCS AS INHOSP_NUM,\n" +
            "JBZD.jzhm AS INHOSP_SERIAL_NO,\n" +
            "JBZD.JLBH AS DIAG_INDEX_NO,\n" +
            "JBZD.ZDSJ AS DIAG_DATE,\n" +
            "JBZD.ZDXH AS DIAG_NO,\n" +
            "JBZD.ZXLB AS DIAG_TYPE_CODE,\n" +
            "JBZD.ZDLB AS DIAG_TYPE_NAME,\n" +
            "JBZD.JBXH AS DIAG_CODE,\n" +
            "JBZD.MSZD AS DIAG_NAME,\n" +
            "JBZD.FJMC AS DIAG_INTRODUCTION\n" +
            "FROM YS_ZY_JBZD JBZD \n" +
            "left join ZY_BRRY ry on ry.zyh=JBZD.jzhm\n" +
            "LEFT JOIN YS_ZY_JZJL YZJ ON YZJ.JZHM=JBZD.jzhm " +
            "where JBZD.jzhm =#{inhospSerialNo} </script>")
    List<PageData> getInhospDiag(PageData pd) ;

}
