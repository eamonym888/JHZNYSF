package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.15	门诊费用
//3.2.22	门诊费用明细
//3.2.24	门诊费用结算
@Mapper
public interface MZFYMapper extends SqlMapper {

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "MM.BRID AS PAT_INDEX_NO,\n" +
            "MB.MZHM AS OUTHOSP_NO,\n" +
            "ymj.Jzxh AS OUTHOSP_SERIAL_NO, \n" +
            "MM.Fphm AS DEAL_NO, \n" +
            "(SELECT listagg(MC.CFHM, ',') within group(order by MC.CFHM)  FROM MS_CF01 MC WHERE MC.FPHM=MM.FPHM) AS PRES_NO, \n" +
            "MG.BRXZ AS MFS_METHOD_CODE, \n" +
            "DECODE(MB.BRXZ,1000,'自费','医保') AS MFS_METHOD_NAME, \n" +
            "YSD.LABEL AS MEDICARE_CATEGORY, \n" +
            "MM.ZJJE AS MEDICAL_TOTAL_FEE, \n" +
            "(MM.ZJJE-MG.JMJE-MM.ZFJE) AS MEDICARE_FEE, \n" +
            "MG.JMJE AS REDUCE_FEE, \n" +
            "MM.ZFJE AS SELF_EXPENSE_FEE, \n" +
            "'' AS SELF_PAYMENT_FEE, \n" +
            "'' AS SELF_NEGATIVE_FEE,\n" +
            "MM.JZRQ AS SETTLEMENT_DATE, \n" +
            "MM.SFRQ AS CHARGE_DATE,\n" +
            "DECODE(MM.FPGL,NULL,'0','1') AS REFUND_FLAG\n" +
            "FROM MS_MZXX MM\n" +
            "LEFT JOIN MS_GHMX MG ON MG.SBXH=MM.GHGL\n" +
            "LEFT JOIN MS_BRDA MB ON MB.BRID=MM.BRID \n" +
            "LEFT JOIN ys_mz_jzls ymj ON ymj.ghxh=MM.GHGL\n" +
            "LEFT JOIN YB_SI_DICT YSD ON YSD.TYPE='INSUTYPE' AND YSD.VALUE=MM.YBXZLX \n" +
            "WHERE ymj.Jzxh = #{outhosppSerialNo} </script>")
    List<PageData> getOuthospFee(PageData pd) ;

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "MZXX.Fphm as DEAL_NO,\n" +
            "MZXX.MZXH as DEAL_SUB_NO,\n" +
            "CF01.CFHM as PRES_NO,\n" +
            "CF01.CFSB as PRES_SUB_NO,\n" +
            "CF02.YPXH as CHARGE_ITEM_CODE,\n" +
            "(SELECT yp.ypmc FROM yk_ypml yp where yp.ypxh=CF02.YPXH) as CHARGE_ITEM_NAME,\n" +
            "CF01.CFLX as FEE_CATEG_CODE,\n" +
            "decode(CF01.CFLX,'1','西药','2','中成药','3','中草药')AS FEE_CATEG_NAME,\n" +
            "(SELECT CD.YBZFLB FROM YK_YPCD CD WHERE CD.YPXH=CF02.YPXH AND ROWNUM=1) as DRUG_CATALOG_TYPE,\n" +
            "ROUND(CF02.YPSL,2) as DRUG_AMOUNT,\n" +
            "CF02.YFDW as DRUG_UNIT,\n" +
            "CF02.YPDJ as DRUG_UNIT_PRICE,\n" +
            "ROUND(CF02.YPSL*CF02.YPDJ*CF02.ZFBL,2) as SELF_PERCENT,\n" +
            "ROUND(CF02.YPSL*CF02.YPDJ,2) as TOTAL_MONEY,\n" +
            "'1' as VALUATION_FLAG\n" +
            "FROM\n" +
            "       MS_CF01 CF01,\n" +
            "       MS_CF02 CF02,\n" +
            "       MS_MZXX MZXX\n" +
            "WHERE\n" +
            "  MZXX.FPHM = CF01.FPHM\n" +
            "AND MZXX.JGID = CF01.JGID \n" +
            "AND CF01.CFSB = CF02.CFSB\n" +
            "and MZXX.Fphm = #{dealNo} \n"+
            "union all\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "MZXX.Fphm as DEAL_NO,\n" +
            "MZXX.MZXH as DEAL_SUB_NO,\n" +
            "to_char(YJ01.YJXH) as PRES_NO,\n" +
            "YJ01.YJXH as PRES_SUB_NO,\n" +
            "YJ02.YLXH as CHARGE_ITEM_CODE,\n" +
            "(SELECT YLSF.FYMC FROM GY_YLSF YLSF WHERE YLSF.FYXH = YJ02.YLXH) as CHARGE_ITEM_NAME,\n" +
            "YJ02.XMLX as FEE_CATEG_CODE,\n" +
            "'医技' AS FEE_CATEG_NAME,\n" +
            "'' as DRUG_CATALOG_TYPE,\n" +
            "ROUND(YJ02.YLSL,2) as DRUG_AMOUNT,\n" +
            "(SELECT YLSF.FYDW FROM GY_YLSF YLSF WHERE YLSF.FYXH = YJ02.YLXH) as DRUG_UNIT,\n" +
            "YJ02.YLDJ as DRUG_UNIT_PRICE,\n" +
            "ROUND(YJ02.YLSL*YJ02.YLDJ*YJ02.ZFBL,2) as SELF_PERCENT,\n" +
            "ROUND(YJ02.YLSL*YJ02.YLDJ,2) as TOTAL_MONEY,\n" +
            "'1' as VALUATION_FLAG\n" +
            "FROM\n" +
            "MS_YJ01 YJ01,\n" +
            "MS_YJ02 YJ02,\n" +
            "MS_MZXX MZXX\n" +
            "WHERE\n" +
            "MZXX.FPHM = YJ01.FPHM \n" +
            "AND MZXX.JGID = YJ01.JGID \n" +
            "AND YJ01.YJXH = YJ02.YJXH  \n" +
            "and MZXX.Fphm = #{dealNo} </script>")
    List<PageData> getOuthospFeeDetail(PageData pd) ;

    @Select(" <script> SELECT a.ORGAN_CODE,a.DEAL_NO,a.BALANCE_NO,a.FEE_CATEG_CODE,a.FEE_CATEG_NAME,sum(a.TOTAL_MONEY) FROM (\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "MZXX.FPHM as DEAL_NO,\n" +
            "MZXX.Jslsh as BALANCE_NO,\n" +
            "CF01.CFLX as FEE_CATEG_CODE,\n" +
            "decode(CF01.CFLX,'1','西药','2','中成药','3','中草药')AS FEE_CATEG_NAME,\n" +
            "ROUND(CF02.YPSL*CF02.YPDJ,2) as TOTAL_MONEY\n" +
            "FROM\n" +
            "       MS_CF01 CF01,\n" +
            "       MS_CF02 CF02,\n" +
            "       MS_MZXX MZXX\n" +
            "WHERE\n" +
            "  MZXX.FPHM = CF01.FPHM\n" +
            "AND MZXX.JGID = CF01.JGID \n" +
            "AND CF01.CFSB = CF02.CFSB\n" +
            "and MZXX.Jslsh is not null\n" +
            "union all\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "MZXX.Fphm as DEAL_NO,\n" +
            "MZXX.Jslsh as BALANCE_NO,\n" +
            "YJ02.XMLX as FEE_CATEG_CODE,\n" +
            "DECODE(YJ02.XMLX,'0','检查项目','4','检验','5','检查','6','手术','7','治疗','8','护理','9','饮食','10','卫材','99','其他','') AS FEE_CATEG_NAME,\n" +
            "ROUND(YJ02.YLSL*YJ02.YLDJ,2) as TOTAL_MONEY\n" +
            "FROM\n" +
            "MS_YJ01 YJ01,\n" +
            "MS_YJ02 YJ02,\n" +
            "MS_MZXX MZXX\n" +
            "WHERE\n" +
            "MZXX.FPHM = YJ01.FPHM \n" +
            "AND MZXX.JGID = YJ01.JGID \n" +
            "AND YJ01.YJXH = YJ02.YJXH \n" +
            "and MZXX.Jslsh is not null\n" +
            ")a \n" +
            "WHERE a.DEAL_NO = #{dealNo} " +
            "group by a.ORGAN_CODE,a.DEAL_NO,a.BALANCE_NO,a.FEE_CATEG_CODE,a.FEE_CATEG_NAME </script>")
    List<PageData> getOuthospBalance(PageData pd) ;
}
