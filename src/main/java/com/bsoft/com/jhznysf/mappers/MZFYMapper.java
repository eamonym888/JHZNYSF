package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.15	门诊费用
@Mapper
public interface MZFYMapper extends SqlMapper {

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM') AS ORGAN_CODE, \n" +
            "MM.BRID AS PAT_INDEX_NO,\n" +
            "MB.MZHM AS OUTHOSP_NO,MG.SBXH AS OUTHOSP_SERIAL_NO, \n" +
            "MM.MZXH AS DEAL_NO, \n" +
            "MC1.CFHM AS PRES_NO, \n" +
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
            "FROM MS_MZXX MM \n" +
            "LEFT JOIN MS_GHMX MG ON MG.SBXH=MM.GHGL\n" +
            "LEFT JOIN MS_BRDA MB ON MB.BRID=MM.BRID \n" +
            "LEFT JOIN MS_CF01 MC1 ON MC1.FPHM=MM.FPHM\n" +
            "LEFT JOIN YB_SI_DICT YSD ON YSD.TYPE='INSUTYPE' AND YSD.VALUE=MM.YBXZLX \n" +
            "WHERE MM.ghgl = #{outhosppSerialNo} </script>")
    List<PageData> getOuthospFee(PageData pd) ;

}
