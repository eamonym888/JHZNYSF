package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.21	检验申请单
@Mapper
public interface JYSQMapper extends SqlMapper {

    @Select(" <script>SELECT distinct '46640408-2' AS ORGAN_CODE, \n" +
            "lls.BRID AS PAT_INDEX_NO, \n" +
            "DECODE(lls.STAYHOSPITALMODE,1,lls.PATIENTID,'') AS OUTHOSP_NO, \n" +
            "DECODE(lls.STAYHOSPITALMODE,1,lls.jzxh,'') AS OUTHOSP_SERIAL_NO, \n" +
            "DECODE(lls.STAYHOSPITALMODE,2,lls.PATIENTID,'') AS INHOSP_NO, \n" +
            "'' as INHOSP_NUM, \n" +
            "DECODE(lls.STAYHOSPITALMODE,2,lls.jzxh,'') as INHOSP_SERIAL_NO, \n" +
            "lls.doctrequestno as REQUISITION_NO,\n" +
            "lls.ylxh as REQUISITION_NO_ITEM, \n" +
            "lls.requesttime as APPLY_DATE,\n" +
            "lls.examinaimcode as EXAM_APPLY_ITEM_CODE, \n" +
            "lls.examinaim as EXAM_APPLY_ITEM_NAME, \n" +
            "lls.sqdstatus as REQUISITION_STATUS, \n" +
            "lls.bz as NOTE\n" +
            "FROM l_lis_sqd lls where 1=1 "+
            "<if  test= \"outhospNo!=null and outhospNo!=''\"> and lls.PATIENTID= #{outhospNo} </if>" +
            "<if  test= \"outhospSerialNo!=null and outhospSerialNo!=''\"> AND ymj.jzxh= #{outhospSerialNo} </if> " +//门诊流水号
            "<if  test= \"inhospNo!=null and inhospNo!=''\"> and lls.PATIENTID = #{inhospNo} </if>" +
            "<if  test= \"inhospSerialNo!=null and inhospSerialNo!=''\"> AND zb.zyh= #{inhospSerialNo} </if> " +//住院流水号
            "<if  test= \"startDate!=null and startDate!=''\"> and lls.requesttime &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>" +
            "<if  test= \"endDate!=null and endDate!=''\"> and lls.requesttime &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if></script>")
    List<PageData> getTestRequisition(PageData pd) ;

}
