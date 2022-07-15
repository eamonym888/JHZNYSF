package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.21	检验申请单
//3.2.26	检验样本
@Mapper
public interface JYSQMapper extends SqlMapper {

    @Select(" <script>SELECT distinct (SELECT GX.CSZ1 FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE, \n" +
            "lls.BRID AS PAT_INDEX_NO, \n" +
            "DECODE(lls.STAYHOSPITALMODE,1,lls.PATIENTID,'') AS OUTHOSP_NO, \n" +
            "DECODE(lls.STAYHOSPITALMODE,1,lls.jzxh,'') AS OUTHOSP_SERIAL_NO, \n" +
            "DECODE(lls.STAYHOSPITALMODE,2,lls.PATIENTID,'') AS INHOSP_NO, \n" +
            "'' as INHOSP_NUM, \n" +
            "DECODE(lls.STAYHOSPITALMODE,2,lls.jzxh,'') as INHOSP_SERIAL_NO, \n" +
            "lls.doctrequestno as REQUISITION_NO,\n" +
            "substr(lls.ylxh,1,length(lls.ylxh) -1 ) as REQUISITION_NO_ITEM, \n" +
            "lls.requesttime as APPLY_DATE,\n" +
            "lls.examinaimcode as EXAM_APPLY_ITEM_CODE, \n" +
            "lls.examinaim as EXAM_APPLY_ITEM_NAME, \n" +
            "lls.sqdstatus as REQUISITION_STATUS, \n" +
            "lls.bz as NOTE\n" +
            "FROM l_lis_sqd lls where 1=1 "+
            "<if  test= \"outhospNo!=null and outhospNo!=''\"> and lls.PATIENTID= #{outhospNo} </if>" +
            "<if  test= \"outhospSerialNo!=null and outhospSerialNo!=''\"> AND lls.jzxh= #{outhospSerialNo} </if> " +//门诊流水号
            "<if  test= \"inhospNo!=null and inhospNo!=''\"> and lls.PATIENTID = #{inhospNo} </if>" +
            "<if  test= \"inhospSerialNo!=null and inhospSerialNo!=''\"> AND lls.jzxh= #{inhospSerialNo} </if> " +//住院流水号
            "<if  test= \"startDate!=null and startDate!=''\"> and lls.requesttime &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>" +
            "<if  test= \"endDate!=null and endDate!=''\"> and lls.requesttime &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if></script>")
    List<PageData> getTestRequisition(PageData pd) ;

    @Select(" <script>SELECT distinct (SELECT GX.CSZ1 FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE, \n" +
            "lls.BRID AS PAT_INDEX_NO, \n" +
            "DECODE(lls.STAYHOSPITALMODE,1,lls.PATIENTID,'') AS OUTHOSP_NO,\n" +
            "DECODE(lls.STAYHOSPITALMODE,1,lls.jzxh,'') AS OUTHOSP_SERIAL_NO,\n" +
            "DECODE(lls.STAYHOSPITALMODE,2,lls.PATIENTID,'') AS INHOSP_NO,\n" +
            "(SELECT yzj.zycs FROM ys_zy_jzjl@bshis yzj where yzj.jzhm=lls.jzxh) as INHOSP_NUM,\n" +
            "DECODE(lls.STAYHOSPITALMODE,2,lls.jzxh,'') as INHOSP_SERIAL_NO,\n" +
            "lls.doctrequestno as REQUISITION_NO,\n" +
            "lls.ylxh as REQUISITION_NO_ITEM, \n" +
            "substr(lj.doctadviseno,4,10) as BARCODE_NO,\n" +
            "lj.executetime as SAMPLING_DATE,\n" +
            "'' as SAMPLING_LOCATION,\n" +
            "lj.BGSJ as TAKE_REPORT_DATE,\n" +
            "'' as TAKE_REPORT_LOCATION,\n" +
            "lj.PRINTTIME as REQUISITION_PRINT_DATE,\n" +
            "lj.notes as NOTE,\n" +
            "'2-申请单编号' as RELATION_TYPE\n" +
            "FROM l_lis_sqd lls, L_JYTMXX lj\n" +
            "where lj.doctrequestno=lls.doctrequestno "+
            "<if  test= \"patIndexNo!=null and patIndexNo!=''\"> and lls.BRID= #{patIndexNo} </if>" +//患者索引号
            "<if  test= \"outhospNo!=null and outhospNo!=''\"> and lls.PATIENTID= #{outhospNo} </if>" +//门诊号
            "<if  test= \"outhospSerialNo!=null and outhospSerialNo!=''\"> AND lls.jzxh= #{outhospSerialNo} </if> " +//门诊流水号
            "<if  test= \"inhospNo!=null and inhospNo!=''\"> and lls.PATIENTID = #{inhospNo} </if>" +//住院号
            "<if  test= \"inhospSerialNo!=null and inhospSerialNo!=''\"> AND lls.jzxh= #{inhospSerialNo} </if> " +//住院流水号
            "<if  test= \"requisitionNoList!=null and requisitionNoList.size() > 0\"> and lls.doctrequestno in " +
            "<foreach collection='requisitionNoList' item='item' open='(' separator=',' close=')'>#{item}</foreach></if>" +//申请单编号
            "<if  test= \"requisitionNoItem!=null and requisitionNoItem!=''\"> and lls.ylxh = #{requisitionNoItem} </if>" +//申请单分项目序号
            " </script>")
    List<PageData> getTestSample(PageData pd) ;
}
