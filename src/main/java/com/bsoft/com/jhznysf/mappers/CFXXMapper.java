package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.9	处方信息
//3.2.10	处方明细信息
@Mapper
public interface CFXXMapper extends SqlMapper {

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE, MC.BRID,MB.MZHM,MC.JZXH, \n" +
            "MC.CFHM,MCX.YPZH AS PRES_GROUP_NO,MC.KSDM,GK.KSMC AS PRES_OPEN_DEPT_NAME,MC.YSDM,GYG.YGXM AS PRES_OPEN_DR_NAME, \n" +
            "to_char(MC.KFRQ,'YYYY-MM-DD hh:mm:ss') AS PRES_ORDER_DATE,'' AS PRES_VALID_DAY,MC.CFLX,decode(MC.CFLX,'1','西药','2','中成药','3','中草药')AS PRES_CATEG_NAME, \n" +
            "'1' AS CHARGE_FLAG,'' AS NOTE \n" +
            "FROM MS_CF01 MC \n" +
            "LEFT JOIN MS_CF02 MCX ON MCX.CFSB=MC.CFSB \n" +
            "LEFT JOIN MS_BRDA MB ON MB.BRID=MC.BRID \n" +
            "LEFT JOIN GY_KSDM GK ON GK.KSDM=MC.KSDM \n" +
            "LEFT JOIN GY_YGDM GYG ON GYG.YGDM=MC.YSDM \n" +
            "WHERE MC.ZFPB=0 AND MC.FPHM IS NOT NULL \n"+
            "AND MC.JZXH = #{outhospSerialNo} </script>")
    List<PageData> getOrderInfo(PageData pd) ;

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,MC.CFHM,MCX.SBXH,MCX.YPZH AS PRES_GROUP_NO, \n" +
            "MCX.YPXH,YY.YPMC AS DRUG_NAME,MCX.YFGG,MCX.GYTJ AS DOSE_WAY_CODE,ZYF.XMMC AS DOSE_WAY_NAME, \n" +
            "MCX.YCJL AS DRUG_USE_ONE_DOSAGE,YY.JLDW AS DRUG_USE_ONE_DOSAGE_UNIT, \n" +
            "MCX.YPYF AS DRUG_USE_FREQUENCY_CODE,GS.CHINESENAME AS DRUG_USE_FREQUENCY_NAME, \n" +
            "YY.YPSX AS DRUG_FORM_CODE,YS.SXMC AS DRUG_FORM_NAME, \n" +
            "MCX.YFDW AS DRUG_UNIT,MCX.YPDJ AS DRUG_UNIT_PRICE, \n" +
            "YY.YPMC AS DRUG_ABBREV,YY.YPQC AS DRUG_DESCR,MCX.YYTS AS PRES_SUSTAINED_DAYS, \n" +
            "MCX.YPSL,'' AS BASE_AUX_DRUG_FLAG,decode(substr(YY.YPMC,2,2),'自备','1','0') AS SELF_DRUG_FLAG, \n" +
            "DECODE(MCX.ZTMC,NULL,'0','1') AS GROUP_FLAG,'' AS NOTE \n" +
            "FROM MS_CF02 MCX \n" +
            "INNER JOIN MS_CF01 MC ON MCX.CFSB=MC.CFSB AND MC.ZFPB=0 AND MC.FPHM IS NOT NULL \n" +
            "LEFT JOIN YK_YPML YY ON YY.YPXH=MCX.YPXH \n" +
            "LEFT JOIN ZY_YPYF ZYF ON ZYF.YPYF=MCX.GYTJ \n" +
            "LEFT JOIN GY_SYPC GS ON GS.PCBM=MCX.YPYF \n" +
            "LEFT JOIN YK_YPSX YS ON YS.YPSX=YY.YPSX \n" +
            "WHERE MC.CFHM = #{presNo} </script>")
    List<PageData> getOrderDetailInfo(PageData pd) ;

}
