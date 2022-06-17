package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.17	住院费用
@Mapper
public interface ZYFYMapper extends SqlMapper {

    @Select(" <script> SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM') AS ORGAN_CODE, \n" +
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

}
