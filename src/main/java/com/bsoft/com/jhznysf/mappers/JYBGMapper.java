package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.12	检验报告

@Mapper
public interface JYBGMapper {

    @Select(" <script> SELECT distinct (SELECT GX.CSZ1 FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,LP.BRID AS PAT_INDEX_NO, \n" +
            "DECODE(LP.STAYHOSPITALMODE,1,LP.PATIENTID,'') AS OUTHOSP_NO, \n" +
            "(SELECT yj.jzxh FROM ms_yj01@bshis yj,L_JYTMXX tm where yj.fphm=tm.fphm and tm.doctadviseno=LP.doctadviseno " +
            "<if  test= \"outhospSerialNo!=null and outhospSerialNo!=''\"> AND yj.jzxh= #{outhospSerialNo} </if> " +//门诊流水号
            " and rownum=1) AS OUTHOSP_SERIAL_NO, \n" +
            "DECODE(LP.STAYHOSPITALMODE,2,LP.PATIENTID,'') AS INHOSP_NO, \n" +
            "(SELECT yzj.zycs FROM ys_zy_jzjl@bshis yzj,zy_brry@bshis zb where lp.patientid=zb.zyhm and yzj.jzhm=zb.zyh) as INHOSP_NUM, \n" +
            "(SELECT zb.zyh FROM zy_brry@bshis zb where lp.patientid=zb.zyhm " +
            "<if  test= \"inhospSerialNo!=null and inhospSerialNo!=''\"> AND zb.zyh= #{inhospSerialNo} </if> " +//住院流水号
            " and rownum=1) as INHOSP_SERIAL_NO, \n" +
            "(SELECT LJ.DOCTREQUESTNO FROM L_JYTMXX LJ where LP.DOCTADVISENO = LJ.DOCTADVISENO) AS REQUISITION_NO, \n" +
            "(SELECT LISTAGG(LT.TESTID, ',') WITHIN GROUP(ORDER BY LT.TESTID)  FROM L_TESTRESULT LT WHERE LT.SAMPLENO=LP.SAMPLENO) AS REQUISITION_NO_ITEM, \n" +
            "LP.SAMPLENO AS REPORT_NO, \n" +
            "LP.DOCTADVISENO AS BARCODE_NO, \n" +
            "LP.EXAMINAIM AS REPORT_NAME, \n" +
            "LP.SAMPLETYPE AS SAMPLE_TYPE_CODE, \n" +
            "(SELECT LA.SAMPLEDESCRIBE FROM L_SAMPLETYPE LA where LA.SAMPLETYPE=LP.SAMPLETYPE) AS SAMPLE_TYPE_NAME,  \n" +
            "LP.EXAMINAIMCODE AS SAMPLE_TYPE_CODE, \n" +
            "LP.EXAMINAIM AS SAMPLE_TYPE_NAME, \n" +
            "DECODE(LP.LABDEPARTMENT,'3084','1','0') AS MICROBE_TEST_FLAG, \n" +
            "SUBSTR(LP.SAMPLENO,9,3) AS EQUIPMENT_CODE,\n" +
            "(SELECT LPT.PROFILEDESCRIBE FROM L_PROFILETEST LPT, L_TESTRESULT LT where LT.SAMPLENO=LP.SAMPLENO and LPT.PROFILENAME=SUBSTR(LP.SAMPLENO,9,3) and LT.DEVICEID=LPT.DEVICEID AND ROWNUM=1) AS EQUIPMENT_NAME, \n \n" +
            "LP.REQUESTTIME AS APPLY_DATE, \n" +
            "LP.SECTION AS APPLY_DEPT_CODE,(SELECT GK.KSMC FROM GY_KSDM GK where GK.KSDM=LP.SECTION) AS APPLY_DEPT_NAME,  \n" +
            "LP.REQUESTER AS APPLY_DR_CODE,(SELECT GY.YGXM FROM GY_YGDM GY where GY.YGDM=LP.REQUESTER) AS APPLY_DR_NAME, \n" +
            "LP.EXECUTETIME AS EXECUTE_DATE,LP.BGSJ AS REPORT_DATE,LP.NOTES AS NOTE \n" +
            "FROM L_PATIENTINFO LP \n" +
            "WHERE LP.RESULTSTATUS>4  \n" +
            "<if  test= \"patIndexNo!=null and patIndexNo!=''\"> and lp.brid = #{patIndexNo} </if>" +
            "<if  test= \"outhospNo!=null and outhospNo!=''\"> and lp.patientid = #{outhospNo} </if>" +
            "<if  test= \"inhospNo!=null and inhospNo!=''\"> and lp.patientid = #{inhospNo} </if>" +
            "<if  test= \"barcodeNo!=null and barcodeNo!=''\"> and lp.doctadviseno = #{barcodeNo} </if>" +
            "<if  test= \"reportNo!=null and reportNo!=''\"> and lp.sampleno = #{reportNo} </if>" +
            "<if  test= \"startDate!=null and startDate!=''\"> and lp.BGSJ &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>" +
            "<if  test= \"endDate!=null and endDate!=''\"> and lp.BGSJ &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if>" +
            " </script>")
    List<PageData> getTestReport(PageData pd) ;

}
