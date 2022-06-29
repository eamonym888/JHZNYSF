package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.8	就诊信息（门、急诊）
@Mapper
public interface JZXXMapper extends SqlMapper {

    @Select(" <script> SELECT A.*\n" +
            "FROM (\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE,MG.BRID,MB.MZHM AS VISIT_CARD_NO,MB.BRXM,MB.SFZH,MB.BRXB,decode(MB.BRXB,1,'男','女') AS SEX_NAME, \n" +
            "MB.CSNY,MB.JTDH,MB.MZHM AS OUTHOSP_NO,MG.SBXH AS OUTHOSP_SERIAL_NO,MG.BRXZ,DECODE(MB.BRXZ,1000,'自费','医保') AS PAT_TYPE_NAME, \n" +
            "MG.JZHM AS REGIST_NO,to_char(MG.GHSJ,'YYYY-MM-DD hh:mm:ss') AS GHSJ,to_char(YMJ.KSSJ,'YYYY-MM-DD hh:mm:ss') AS KSSJ,to_char(YMJ.JSSJ,'YYYY-MM-DD hh:mm:ss') AS JSSJ,SBO.ZSXX AS CHIEF_DESCR,SBO.XBSXX AS CURR_DISEASE_HISTORY ,SBO.XJWS AS PAST_DISEASE_HISTORY, \n" +
            "'' AS ALLERGY_HISTORY, YMJ.YSDM," +
            "(SELECT GY.YGXM FROM GY_YGDM GY where GY.YGDM=YMJ.YSDM " +
            "<if  test= \"receptTreatDrName!=null and receptTreatDrName!=''\"> AND GY.YGXM= #{receptTreatDrName} </if>\n" +//接诊医生姓名
            ") AS RECEPT_TREAT_DR_NAME," +
            "YMJ.KSDM," +
            "(SELECT GK.KSMC FROM GY_KSDM GK where GK.KSDM=YMJ.KSDM " +
            "<if  test= \"visitDeptName!=null and visitDeptName!=''\"> AND GK.KSMC= #{visitDeptName} </if>\n" +//就诊科室名称
            ") AS VISIT_DEPT_NAME,\n" +
            "(SELECT listagg(YMZ.ICD, ',') within group(order by YMZ.ICD)  FROM YS_MZ_JBZD YMZ WHERE YMJ.JZXH=YMZ.JZXH) AS DIAG_CODE, \n" +
            "(SELECT listagg(YMZ.JBMC, ',') within group(order by YMZ.JBMC)  FROM YS_MZ_JBZD YMZ WHERE YMJ.JZXH=YMZ.JZXH) AS DIAG_NAME,  \n" +
            "SBO.XTGJC AS PHYSICAL_EXAM,SBO.MJZYY AS TREATMENT_ADVICE,DECODE((SELECT COUNT(1) FROM EMR_ZDBK WHERE JZHM=YMJ.JZXH),0,0,1) AS DISEASES_REPORTED_FLAG \n" +
            "FROM MS_GHMX MG \n" +
            "LEFT JOIN MS_BRDA MB ON MB.BRID=MG.BRID \n" +
            "INNER JOIN YS_MZ_JZLS YMJ ON YMJ.GHXH=MG.SBXH\n" +
            "LEFT JOIN OMR_BL01 OB ON OB.JZXH=YMJ.JZXH AND OB.BLZT IN (0,1)\n" +
            "LEFT JOIN SJCJ_BLJX_OMR SBO ON SBO.JZHM=YMJ.JZXH AND SBO.BLBH=OB.BLBH\n" +
            "WHERE MG.JZJS=1 AND MB.BRXZ IN (1000,5000) \n" +
            "<if  test= \"visitCardNo!=null and visitCardNo!=''\"> AND MB.MZHM= #{visitCardNo} </if>\n" +//就诊卡号
            "<if  test= \"patIndexNo!=null and patIndexNo!=''\"> AND MG.BRID= #{patIndexNo} </if>\n" +//患者索引号
            "<if  test= \"outhospNo!=null and outhospNo!=''\"> AND MB.MZHM= #{outhospNo} </if>\n" +//门诊号
            "<if  test= \"outhospSerialNo!=null and outhospSerialNo!=''\"> AND YMJ.JZXH= #{outhospSerialNo} </if>\n" +//门诊流水号
            "<if  test= \"patName!=null and patName!=''\"> AND MB.BRXM= #{patName} </if>\n" +//患者姓名
            "<if  test= \"idNumber!=null and idNumber!=''\"> AND MB.SFZH= #{idNumber} </if>\n" +//身份证号码
            "<if  test= \"mobileNo!=null and mobileNo!=''\"> AND MB.JTDH= #{mobileNo} </if>\n" +//手机号

            "<if  test= \"visitDeptCode!=null and visitDeptCode!=''\"> AND YMJ.KSDM= #{visitDeptCode} </if>\n" +//就诊科室代码

            "<if  test= \"diagName!=null and diagName!=''\"> AND YMZ.JBMC= #{diagName} </if>\n" +//诊断名称
            "<if  test= \"startDate!=null and startDate!=''\"> and YMJ.KSSJ &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>" +//开始日期时间
            "<if  test= \"endDate!=null and endDate!=''\"> and YMJ.KSSJ &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if>\n" +//结束日期时间

            "<if  test= \"flag=='1'.toString()\"> order by YMJ.KSSJ </if> \n" +
            ")A\n" +
            "WHERE 1=1 " +
            "<if  test= \"flag=='1'.toString()\"> and rownum &gt; (#{num}-1)*#{size} and rownum &lt;= #{num}*#{size} </if> </script>")//取分页数据
    List<PageData> getVisitInfo(PageData pd) ;

}
