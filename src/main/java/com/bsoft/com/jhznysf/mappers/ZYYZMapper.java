package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.18	住院医嘱
@Mapper
public interface ZYYZMapper extends SqlMapper {

    @Select(" <script> SELECT A.* from (\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "ey.brid as PAT_INDEX_NO,\n" +
            "ZB.ZYHM as INHOSP_NO,\n" +
            "YZJ.ZYCS as INHOSP_NUM,\n" +
            "ZB.ZYH as INHOSP_SERIAL_NO,\n" +
            "ey.yzbxh as ORDER_NO,\n" +
            "ey.yzzh as ORDER_GROUP_NO,\n" +
            "ey.kzsj as ORDER_PLAN_BEGIN_DATE,\n" +
            "NVL(ey.TZSJ,ey.KZSJ) as ORDER_PLAN_END_DATE,\n" +
            "ey.kzsj as ORDER_BEGIN_DATE,\n" +
            "ey.tzsj as ORDER_END_DATE,\n" +
            "ey.kzsj as ORDER_ORDER_DATE,\n" +
            "ey.kzks as ORDER_OPEN_DEPT_CODE,\n" +
            "(SELECT gk.ksmc FROM gy_ksdm gk where gk.ksdm=ey.kzks) as ORDER_OPEN_DEPT_NAME,\n" +
            "ey.kzys as ORDER_OPEN_DR_CODE,\n" +
            "(SELECT gy.ygxm FROM gy_ygdm gy where gy.ygdm=ey.kzys) as ORDER_OPEN_DR_NAME,\n" +
            "NVL(ey.XZJDSJ,TO_DATE('19000101','YYYYMMDD')) as ORDER_EXECUTE_DATE,\n" +
            "ey.YDYZLB as ORDER_ITEM_TYPE_CODE,\n" +
            "(SELECT yd.lbmc FROM emr_ydyzlb yd where yd.ydyzlb=ey.YDYZLB) as ORDER_ITEM_TYPE_NAME,\n" +
            "ey.xmlb as ORDER_CATEG_CODE,\n" +
            "DECODE(ey.xmlb,'1','药品','2','诊疗','3','费用','4','材料','5','草药方','6','组套','7','特殊','9','嘱托','') as ORDER_CATEG_NAME,\n" +
            "ey.XMID as ORDER_ITEM_CODE,\n" +
            "ey.YZMC as ORDER_ITEM_NAME,\n" +
            "decode(ey.ypcd,ey.XMID,'') as DRUG_CODE,\n" +
            "decode(ey.ypcd,ey.YZMC,'') as DRUG_NAME,\n" +
            "ey.YFGG as DRUG_SPECIFICATIONS,\n" +
            "ey.GYTJ as DOSE_WAY_CODE,\n" +
            "(SELECT ZYF.XMMC FROM ZY_YPYF ZYF where ZYF.YPYF=ey.GYTJ) as DOSE_WAY_NAME,\n" +
            "CASE WHEN ey.YCJL = 0 THEN NULL ELSE ey.YCJL END as DRUG_USE_ONE_DOSAGE,\n" +
            "ey.JLDW as DRUG_USE_ONE_DOSAGE_UNIT,\n" +
            "ey.SYPC as DRUG_USE_FREQUENCY_CODE,\n" +
            "(SELECT GS.CHINESENAME FROM GY_SYPC GS where GS.PCBM=ey.SYPC) as DRUG_USE_FREQUENCY_NAME,\n" +
            "YY.YPSX as DRUG_FORM_CODE,\n" +
            "(SELECT YS.SXMC FROM YK_YPSX YS where YS.YPSX=YY.YPSX) as DRUG_FORM_NAME,\n" +
            "ey.ZLDW as DRUG_UNIT,\n" +
            "ey.XMDJ as DRUG_UNIT_PRICE,\n" +
            "YY.YPMC as DRUG_ABBREV,\n" +
            "YY.YPQC as DRUG_DESCR,\n" +
            "ey.ZL as DRUG_AMOUNT,\n" +
            "ceil(ey.tzsj-ey.kzsj) as ORDER_DURATION,\n" +
            "'天' as ORDER_DURATION_UNIT,\n" +
            "'' as BASE_AUX_DRUG_FLAG,\n" +
            "'0' as DISCHARGE_ORDER_FLAG,\n" +
            "'' as DR_ENTRUST,\n" +
            "NVL(ey.BZXX,'') as NOTE\n" +
            "FROM emr_yzb ey\n" +
            "left join zy_brry zb on zb.zyh=ey.zyh\n" +
            "left join ys_zy_jzjl yzj on yzj.jzhm=ey.zyh\n" +
            "LEFT JOIN YK_YPML YY ON YY.YPXH=ey.XMID \n" +

            "where ZB.ZYH= #{inhospSerialNo} " +//住院流水号
            "<if  test= \"startDate!=null and startDate!=''\"> and ey.kzsj &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>"+//开始日期时间
            "<if  test= \"endDate!=null and endDate!=''\"> and ey.kzsj &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if>" +//结束日期时间
            ")A \n" +
            "WHERE 1=1 " +
            "<if  test= \"flag=='1'.toString()\"> and rownum &gt; (#{num}-1)*#{size} and rownum &lt;= #{num}*#{size} </if> </script>")//取分页数据
    List<PageData> getInhospOrder(PageData pd) ;

    @Select("<script> SELECT count(1)\n" +
            "FROM (\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "ey.brid as PAT_INDEX_NO,\n" +
            "ZB.ZYHM as INHOSP_NO,\n" +
            "YZJ.ZYCS as INHOSP_NUM,\n" +
            "ZB.ZYH as INHOSP_SERIAL_NO,\n" +
            "ey.yzbxh as ORDER_NO,\n" +
            "ey.yzzh as ORDER_GROUP_NO,\n" +
            "ey.kzsj as ORDER_PLAN_BEGIN_DATE,\n" +
            "to_char(NVL(ey.TZSJ,ey.KZSJ),'YYYY-MM-DD hh:mm:ss')  as ORDER_PLAN_END_DATE,\n" +
            "to_char(ey.kzsj,'YYYY-MM-DD hh:mm:ss') as ORDER_BEGIN_DATE,\n" +
            "to_char(ey.tzsj,'YYYY-MM-DD hh:mm:ss') as ORDER_END_DATE,\n" +
            "to_char(ey.kzsj,'YYYY-MM-DD hh:mm:ss') as ORDER_ORDER_DATE,\n" +
            "to_char(ey.kzks,'YYYY-MM-DD hh:mm:ss') as ORDER_OPEN_DEPT_CODE,\n" +
            "(SELECT gk.ksmc FROM gy_ksdm gk where gk.ksdm=ey.kzks) as ORDER_OPEN_DEPT_NAME,\n" +
            "ey.kzys as ORDER_OPEN_DR_CODE,\n" +
            "(SELECT gy.ygxm FROM gy_ygdm gy where gy.ygdm=ey.kzys) as ORDER_OPEN_DR_NAME,\n" +
            "NVL(ey.XZJDSJ,TO_DATE('19000101','YYYYMMDD')) as ORDER_EXECUTE_DATE,\n" +
            "ey.YDYZLB as ORDER_ITEM_TYPE_CODE,\n" +
            "(SELECT yd.lbmc FROM emr_ydyzlb yd where yd.ydyzlb=ey.YDYZLB) as ORDER_ITEM_TYPE_NAME,\n" +
            "ey.xmlb as ORDER_CATEG_CODE,\n" +
            "DECODE(ey.xmlb,'1','药品','2','诊疗','3','费用','4','材料','5','草药方','6','组套','7','特殊','9','嘱托','') as ORDER_CATEG_NAME,\n" +
            "ey.XMID as ORDER_ITEM_CODE,\n" +
            "ey.YZMC as ORDER_ITEM_NAME,\n" +
            "decode(ey.ypcd,ey.XMID,'') as DRUG_CODE,\n" +
            "decode(ey.ypcd,ey.YZMC,'') as DRUG_NAME,\n" +
            "ey.YFGG as DRUG_SPECIFICATIONS,\n" +
            "ey.GYTJ as DOSE_WAY_CODE,\n" +
            "(SELECT ZYF.XMMC FROM ZY_YPYF ZYF where ZYF.YPYF=ey.GYTJ) as DOSE_WAY_NAME,\n" +
            "CASE WHEN ey.YCJL = 0 THEN NULL ELSE ey.YCJL END as DRUG_USE_ONE_DOSAGE,\n" +
            "ey.JLDW as DRUG_USE_ONE_DOSAGE_UNIT,\n" +
            "ey.SYPC as DRUG_USE_FREQUENCY_CODE,\n" +
            "(SELECT GS.CHINESENAME FROM GY_SYPC GS where GS.PCBM=ey.SYPC) as DRUG_USE_FREQUENCY_NAME,\n" +
            "YY.YPSX as DRUG_FORM_CODE,\n" +
            "(SELECT YS.SXMC FROM YK_YPSX YS where YS.YPSX=YY.YPSX) as DRUG_FORM_NAME,\n" +
            "ey.ZLDW as DRUG_UNIT,\n" +
            "ey.XMDJ as DRUG_UNIT_PRICE,\n" +
            "YY.YPMC as DRUG_ABBREV,\n" +
            "YY.YPQC as DRUG_DESCR,\n" +
            "ey.ZL as DRUG_AMOUNT,\n" +
            "ceil(ey.tzsj-ey.kzsj) as ORDER_DURATION,\n" +
            "'天' as ORDER_DURATION_UNIT,\n" +
            "'' as BASE_AUX_DRUG_FLAG,\n" +
            "'0' as DISCHARGE_ORDER_FLAG,\n" +
            "'' as DR_ENTRUST,\n" +
            "NVL(ey.BZXX,'') as NOTE\n" +
            "FROM emr_yzb ey\n" +
            "left join zy_brry zb on zb.zyh=ey.zyh\n" +
            "left join ys_zy_jzjl yzj on yzj.jzhm=ey.zyh\n" +
            "LEFT JOIN YK_YPML YY ON YY.YPXH=ey.XMID \n" +

            "where ZB.ZYH= #{inhospSerialNo} " +//住院流水号
            "<if  test= \"startDate!=null and startDate!=''\"> and ey.kzsj &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>"+//开始日期时间
            "<if  test= \"endDate!=null and endDate!=''\"> and ey.kzsj &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if>" +//结束日期时间
            ")A </script>")

    String getInhospOrderCount(PageData pd) ;

}
