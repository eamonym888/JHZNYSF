package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.20	检查申请单
@Mapper
public interface JCSQMapper extends SqlMapper {

    @Select(" <script>SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM') AS ORGAN_CODE, \n" +
            "ejs.BRID AS PAT_INDEX_NO, \n" +
            "ymj.BRBH AS OUTHOSP_NO,\n" +
            "ymj.JZXH AS OUTHOSP_SERIAL_NO, \n" +
            "zb.zyhm as INHOSP_NO,\n" +
            "yzj.zycs as INHOSP_NUM, \n" +
            "yzj.JZHM as INHOSP_SERIAL_NO， \n" +
            "ejs.sqdh as REQUISITION_NO,\n" +
            "ejs.kdsj as APPLY_DATE,\n" +
            "ejc.zlxmid as EXAM_APPLY_ITEM_CODE, \n" +
            "ejs.jcxm as EXAM_APPLY_ITEM_NAME, \n" +
            "ejs.jclx as EXAM_TYPE_CODE, \n" +
            "'' as EXAM_TYPE_NAME,\n" +
            "(SELECT ejb.bgrq FROM emr_jcbg ejb where ejb.sqdh=ejs.sqdh) as FINISH_DATE, \n" +
            "ejc.jcbw as OPER_PART_CODE, \n" +
            "(SELECT ed.dmmc FROM  emr_dmzd ed where ed.dmlb='1' and ejc.jcbw=to_char(ed.dmsb)) as OPER_PART_NAME,  \n" +
            "ejs.jczt as REQUISITION_STATUS, \n" +
            "ejs.jbmc as NOTE\n" +
            "FROM emr_jcsq ejs \n" +
            "left join YS_MZ_JZLS ymj on ymj.jzxh=ejs.jzhm and ejs.jzlx=1\n" +
            "LEFT JOIN YS_ZY_JZJL yzj ON yzj.JZHM=ejs.JZHM and ejs.jzlx=2\n" +
            "LEFT JOIN ZY_BRRY ZB ON ZB.ZYH=yzj.JZHM\n" +
            "left join emr_jcxm ejc on ejc.sqdh=ejs.sqdh\n"+
            "WHERE 1=1 "+
            "<if  test= \"outhospSerialNo!=null and outhospSerialNo!=''\"> and ymj.JZXH = #{outhospSerialNo} </if>" +
            "<if  test= \"inhospSerialNo!=null and inhospSerialNo!=''\"> and yzj.JZHM = #{inhospSerialNo} </if>" +
            "<if  test= \"outhospNo!=null and outhospNo!=''\"> and ymj.BRBH = #{outhospNo} </if>" +
            "<if  test= \"inhospNo!=null and inhospNo!=''\"> and zb.zyhm = #{inhospNo} </if>" +
            "<if  test= \"startDate!=null and startDate!=''\"> and ejs.kdsj &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>" +
            "<if  test= \"endDate!=null and endDate!=''\"> and ejs.kdsj &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if></script>")
    List<PageData> getExamRequisition(PageData pd) ;

}
