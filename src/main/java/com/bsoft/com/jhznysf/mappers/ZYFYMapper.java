package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.17	住院费用
//3.2.23	住院费用明细
//3.2.25	住院费用结算
@Mapper
public interface ZYFYMapper extends SqlMapper {

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE, \n" +
            "zb.BRID AS PAT_INDEX_NO, \n" +
            "zb.zyhm as INHOSP_NO,\n" +
            "YZJ.ZYCS as INHOSP_NUM, \n" +
            "zzj.zyh as INHOSP_SERIAL_NO, \n" +
            "'' as ORDER_NO, \n" +
            "zzj.fphm as DEAL_NO, \n" +
            "zzj.BRXZ AS MFS_METHOD_CODE, \n" +
            "DECODE(zzj.BRXZ,1000,'自费','医保') AS MFS_METHOD_NAME, \n" +
            "YSD.LABEL AS MEDICARE_CATEGORY, \n" +
            "zzj.fyhj as MEDICAL_TOTAL_FEE, \n" +
            "(zzj.fyhj-zzj.zfhj) AS MEDICARE_FEE, \n" +
            "'' AS REDUCE_FEE, \n" +
            "zzj.zfhj AS SELF_EXPENSE_FEE, \n" +
            "'' AS SELF_PAYMENT_FEE, \n" +
            "'' AS SELF_NEGATIVE_FEE, \n" +
            "zzj.JSRQ AS SETTLEMENT_DATE, \n" +
            "zzj.JZRQ AS CHARGE_DATE,\n" +
            "DECODE((SELECT count(1) FROM zy_fymx zf where zf.zyh=zzj.zyh and tfgl>0),0,'0','1') AS REFUND_FLAG\n" +
            "FROM zy_zyjs zzj \n" +
            "left join ZY_BRRY zb on zb.zyh=zzj.zyh\n" +
            "LEFT JOIN YS_ZY_JZJL YZJ ON YZJ.JZHM=zzj.ZYH\n" +
            "LEFT JOIN YB_SI_DICT YSD ON YSD.TYPE='INSUTYPE' AND YSD.VALUE=zzj.yblb\n" +
            "WHERE zzj.zyh = #{inhospSerialNo} </script>")
    List<PageData> getInhospFee(PageData pd) ;

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "FYMX.YZXH as ORDER_NO,\n" +
            "ZYJS.FPHM as DEAL_NO,\n" +
            "FYMX.JLXH as DEAL_SUB_NO,\n" +
            "FYMX.FYXH as CHARGE_ITEM_CODE,\n" +
            "FYMX.FYMC as CHARGE_ITEM_NAME,\n" +
            "FYMX.XMLX as FEE_CATEG_CODE,\n" +
            "DECODE(FYMX.XMLX,'1','病区系统记帐','2','药房系统记帐','3','医技系统记帐','4','住院系统记帐','5','手术麻醉记帐','9','自动累加费用','6','pacs系统记账','')AS FEE_CATEG_NAME,\n" +
            "'' as DRUG_CATALOG_TYPE,\n" +
            "FYMX.FYSL as DRUG_AMOUNT,\n" +
            "'' as DRUG_UNIT,\n" +
            "FYMX.FYDJ as DRUG_UNIT_PRICE,\n" +
            "FYMX.ZFJE as SELF_PERCENT,\n" +
            "FYMX.ZJJE as TOTAL_MONEY\n" +
            "FROM \n" +
            "V_ZY_FYMX FYMX,  ZY_BRRY RY, zy_zyjs ZYJS\n" +
            "WHERE \n" +
            "FYMX.ZYH = RY.ZYH\n" +
            "and ZYJS.ZYH=FYMX.ZYH\n" +
            "and ZYJS.FPHM = #{dealNo} </script>")
    List<PageData> getInhospFeeDetail(PageData pd) ;

    @Select(" <script> SELECT a.ORGAN_CODE,a.DEAL_NO,a.BALANCE_NO,a.FEE_CATEG_CODE,a.FEE_CATEG_NAME,sum(a.TOTAL_MONEY) FROM (\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,\n" +
            "ZYJS.FPHM as DEAL_NO,\n" +
            "ZYJS.Jslsh as BALANCE_NO,\n" +
            "FYMX.XMLX as FEE_CATEG_CODE,\n" +
            "DECODE(FYMX.XMLX,'1','病区系统记帐','2','药房系统记帐','3','医技系统记帐','4','住院系统记帐','5','手术麻醉记帐','9','自动累加费用','6','pacs系统记账','')AS FEE_CATEG_NAME,\n" +
            "FYMX.ZJJE as TOTAL_MONEY\n" +
            "FROM \n" +
            "V_ZY_FYMX FYMX,  ZY_BRRY RY, zy_zyjs ZYJS\n" +
            "WHERE \n" +
            "FYMX.ZYH = RY.ZYH\n" +
            "and ZYJS.ZYH=FYMX.ZYH\n" +
            "and ZYJS.Jslsh is not null\n" +
            ")a \n" +
            "WHERE a.DEAL_NO = #{dealNo} " +
            "group by a.ORGAN_CODE,a.DEAL_NO,a.BALANCE_NO,a.FEE_CATEG_CODE,a.FEE_CATEG_NAME </script>")
    List<PageData> getInhospBalance(PageData pd) ;
}
