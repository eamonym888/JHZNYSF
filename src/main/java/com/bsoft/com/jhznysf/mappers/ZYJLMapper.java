package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//3.2.16	住院记录
@Mapper
public interface ZYJLMapper extends SqlMapper {

    @Select(" <script> SELECT A.* from (\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM') AS ORGAN_CODE,ZB.BRID,ZB.MZHM,ZB.BRXM,ZB.SFZH,MB.BRXB,decode(MB.BRXB,1,'男','女') AS SEX_NAME,MB.CSNY,ZB.LXDH,ZB.ZYHM,YZJ.ZYCS,ZB.ZYH, \n" +
            "ZB.BRKS,\n" +
            "(SELECT GK.KSMC FROM GY_KSDM GK where GK.KSDM=ZB.BRKS " +
            "<if  test= \"deptName!=null and deptName!=''\"> AND GK.KSMC= #{deptName} </if>" +//科室名称
            ") AS DEPT_NAME,\n" +
            "ZB.BRBQ,\n" +
            "(SELECT GK1.KSMC FROM GY_KSDM GK1 where GK1.KSDM=ZB.BRBQ " +
            "<if  test= \"wardName!=null and wardName!=''\"> AND GK1.KSMC= #{wardName} </if>" +//病区名称
            ") AS WARD_NAME,\n" +
            "'' AS SICKROOM_NO,ZB.BRCH, \n" +
            "to_char(ZB.RYRQ,'YYYY-MM-DD hh:mm:ss') AS RYRQ,\n" +
            "(SELECT GD.DMMC FROM GY_DMZD GD where GD.DMLB=10 AND GD.DMLB=ZB.RYQK) AS ADMIT_SITUATION, \n" +
            "BEBB.XMQZ AS ADMIT_WAY_CODE,GD1.DMMC AS ADMIT_WAY_NAME, \n" +
            "(select nr.dlnr from emr_bl_dlnr  nr where nr.jzhm=ZB.ZYH and nr.dlmc = '主诉' and rownum=1) AS PAT_CHIEF_DESCR, \n" +
            "'' AS BRIEF_DISEASE_SITUATION, \n" +
            "(select nr.dlnr from emr_bl_dlnr  nr where nr.jzhm=ZB.ZYH and nr.dlmc = '现病史' and rownum=1) AS CURR_DISEASE_HISTORY, \n" +
            "(select nr.dlnr from emr_bl_dlnr  nr where nr.jzhm=ZB.ZYH and nr.dlmc = '既往史' and rownum=1) AS DISEASE_HISTORY, \n" +
            "'' AS SURGERY_HISTORY,'' AS METACHYSIS_HISTORY, '' AS ALLERGY_HISTORY, '' AS INFECT_DISEASE_HISTORY, \n" +
            "(SELECT listagg(GJ.ICD10, ',') within group(order by GJ.ICD10)  FROM YS_ZY_JBZD YZJ LEFT JOIN GY_JBBM GJ ON GJ.JBXH=YZJ.JBDM WHERE YZJ.JZHM=ZB.ZYH AND YZJ.ZDLB='初步诊断' " +
            "<if  test= \"admitDiagCode!=null and admitDiagCode!=''\"> AND GJ.ICD10= #{admitDiagCode} </if> " +//入院诊断代码（多个诊断代码用逗号分隔）
            ") AS ADMIT_DIAG_CODE, \n" +
            "(SELECT listagg(YZJ.JBMC, ',') within group(order by YZJ.JBMC)  FROM YS_ZY_JBZD YZJ WHERE YZJ.JZHM=ZB.ZYH AND YZJ.ZDLB='初步诊断' " +
            "<if  test= \"admitDiagName!=null and admitDiagName!=''\"> AND YZJ.JBMC like CONCAT(CONCAT('%',#{admitDiagName}),'%') </if>" +//入院诊断名称
            ")  AS ADMIT_DIAG_NAME, \n" +
            "ZB.MZYS,\n" +
            "(SELECT GYG1.YGXM FROM GY_YGDM GYG1 where GYG1.YGDM=ZB.MZYS) AS RECEPT_TREAT_DR_NAME,\n" +
            "ZB.ZYYS,\n" +
            "(SELECT GYG2.YGXM FROM GY_YGDM GYG2 where GYG2.YGDM=ZB.ZYYS) AS INHOSP_DR_NAME,\n" +
            "ZB.ZZYS,\n" +
            "(SELECT GYG3.YGXM FROM GY_YGDM GYG3 where GYG3.YGDM=ZB.ZZYS " +
            "<if  test= \"attendDrName!=null and attendDrName!=''\"> AND GYG3.YGXM= #{attendDrName} </if>" +//主治医师姓名
            ") AS ATTEND_DR_NAME,\n" +
            "ZB.ZSYS,\n" +
            "(SELECT GYG4.YGXM FROM GY_YGDM GYG4 where GYG4.YGDM=ZB.ZSYS) AS CHIEF_DR_NAME,\n" +
            "ZB.BRQK AS DISCHARGE_STATUS,to_char(ZB.CYRQ,'YYYY-MM-DD hh:mm:ss') AS CYRQ,  \n" +
            "(SELECT listagg(GJ.ICD10, ',') within group(order by GJ.ICD10)  FROM YS_ZY_JBZD YZJ LEFT JOIN GY_JBBM GJ ON GJ.JBXH=YZJ.JBDM WHERE YZJ.JZHM=ZB.ZYH AND YZJ.ZDLB='出院诊断' " +
            "<if  test= \"dischargeDiagCode!=null and dischargeDiagCode!=''\"> AND GJ.ICD10= #{dischargeDiagCode} </if> " +//出院诊断代码（多个诊断代码用逗号分隔）
            ") AS DISCHARGE_DIAG_CODE, \n" +
            "(SELECT listagg(YZJ.JBMC, ',') within group(order by YZJ.JBMC)  FROM YS_ZY_JBZD YZJ WHERE YZJ.JZHM=ZB.ZYH AND YZJ.ZDLB='出院诊断' " +
            "<if  test= \"dischargeDiagName!=null and dischargeDiagName!=''\"> AND YZJ.JBMC like CONCAT(CONCAT('%',#{dischargeDiagName}),'%') </if>" +//出院诊断名称
            ")  AS DISCHARGE_DIAG_NAME,  \n" +
            "ZB.CYFS,(SELECT GD2.DMMC FROM GY_DMZD GD2 where GD2.DMLB=23 AND GD2.DMSB=ZB.CYFS) AS DISCHARGE_METHOD_NAME,\n" +
            "(select nr.dlnr from emr_bl_dlnr  nr where nr.jzhm=ZB.ZYH and nr.dlmc = '体格检查' and rownum=1) AS PHYSICAL_EXAM, \n" +
            "'' AS TREATMENT_ADVICE, decode(ZB.CYPB,'0','3','1','1','8','1') as CYPB  \n" +
            "FROM ZY_BRRY ZB \n" +
            "LEFT JOIN MS_BRDA MB ON MB.BRID=ZB.BRID \n" +
            "LEFT JOIN YS_ZY_JZJL YZJ ON YZJ.JZHM=ZB.ZYH\n" +
            "LEFT JOIN BSEMR56.emr_bl_basysj BEBB ON BEBB.JZHM=to_char(ZB.ZYH) and BEBB.xmmc = '入院途径'\n" +
            "LEFT JOIN GY_DMZD GD1 ON GD1.DMLB=228 AND GD1.DMSB=BEBB.XMQZ\n" +
            "WHERE ZB.CYPB in (0,1,8) \n" +

            "<if  test= \"patIndexNo!=null and patIndexNo!=''\"> AND ZB.BRID= #{patIndexNo} </if>" +//患者索引号
            "<if  test= \"visitCardNo!=null and visitCardNo!=''\"> AND ZB.MZHM= #{visitCardNo} </if>" +//就诊卡号
            "<if  test= \"inhospNo!=null and inhospNo!=''\"> AND ZB.BAHM= #{inhospNo} </if>" +//住院号
            "<if  test= \"inhospSerialNo!=null and inhospSerialNo!=''\"> AND ZB.ZYH= #{inhospSerialNo} </if>" +//住院流水号
            "<if  test= \"patName!=null and patName!=''\"> AND ZB.BRXM= #{patName} </if>" +//患者姓名
            "<if  test= \"idNumber!=null and idNumber!=''\"> AND ZB.SFZH= #{idNumber} </if>" +//身份证号码
            "<if  test= \"mobileNo!=null and mobileNo!=''\"> AND ZB.LXDH= #{mobileNo} </if>" +//手机号
            "<if  test= \"bedNo!=null and bedNo!=''\"> AND ZB.BRCH= #{bedNo} </if>" +//病床号
            "<if  test= \"wardCode!=null and wardCode!=''\"> AND ZB.BRBQ= #{wardCode} </if>" +//病区代码
            "<if  test= \"deptCode!=null and deptCode!=''\"> AND ZB.BRKS= #{deptCode} </if>" +//科室代码
            "<if  test= \"inhospStatus!=null and inhospStatus!=''\"> AND ZB.CYPB= #{inhospStatus} </if>" +//在院状态
            "<if  test= \"admitStartDate!=null and admitStartDate!=''\"> and ZB.RYRQ &gt;= to_date(#{admitStartDate},'yyyy-MM-dd HH24:mi:ss') </if>" +//入院开始日期时间
            "<if  test= \"admitEndDate!=null and admitEndDate!=''\"> and ZB.RYRQ &lt;= to_date(#{admitEndDate},'yyyy-MM-dd HH24:mi:ss') </if>"+//入院结束日期时间
            "<if  test= \"startDate!=null and startDate!=''\"> and ZB.CYRQ &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>"+//开始日期时间
            "<if  test= \"endDate!=null and endDate!=''\"> and ZB.CYRQ &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if>" +//结束日期时间

            "<if  test= \"flag=='1'.toString()\"> order by ZB.CYRQ desc </if> \n" +
            ")A \n" +
            "WHERE 1=1 " +
            "<if  test= \"flag=='1'.toString()\"> and rownum &gt; (#{num}-1)*#{size} and rownum &lt;= #{num}*#{size} </if> </script>")//取分页数据
    List<PageData> getInhospRecord(PageData pd) ;

    @Select("<script> SELECT count(1)\n" +
            "FROM (\n" +
            "SELECT (SELECT GX.MRZ FROM GY_XTCS GX where GX.CSMC='YLJGDM') AS ORGAN_CODE,ZB.BRID,ZB.MZHM,ZB.BRXM,ZB.SFZH,MB.BRXB,decode(MB.BRXB,1,'男','女') AS SEX_NAME,MB.CSNY,ZB.LXDH,ZB.ZYHM,YZJ.ZYCS,ZB.ZYH, \n" +
            "ZB.BRKS,\n" +
            "(SELECT GK.KSMC FROM GY_KSDM GK where GK.KSDM=ZB.BRKS " +
            "<if  test= \"deptName!=null and deptName!=''\"> AND GK.KSMC= #{deptName} </if>" +//科室名称
            ") AS DEPT_NAME,\n" +
            "ZB.BRBQ,\n" +
            "(SELECT GK1.KSMC FROM GY_KSDM GK1 where GK1.KSDM=ZB.BRBQ " +
            "<if  test= \"wardName!=null and wardName!=''\"> AND GK1.KSMC= #{wardName} </if>" +//病区名称
            ") AS WARD_NAME,\n" +
            "'' AS SICKROOM_NO,ZB.BRCH, \n" +
            "to_char(ZB.RYRQ,'YYYY-MM-DD hh:mm:ss') AS RYRQ,\n" +
            "(SELECT GD.DMMC FROM GY_DMZD GD where GD.DMLB=10 AND GD.DMLB=ZB.RYQK) AS ADMIT_SITUATION, \n" +
            "BEBB.XMQZ AS ADMIT_WAY_CODE,GD1.DMMC AS ADMIT_WAY_NAME, \n" +
            "(select nr.dlnr from emr_bl_dlnr  nr where nr.jzhm=ZB.ZYH and nr.dlmc = '主诉' and rownum=1) AS PAT_CHIEF_DESCR, \n" +
            "'' AS BRIEF_DISEASE_SITUATION, \n" +
            "(select nr.dlnr from emr_bl_dlnr  nr where nr.jzhm=ZB.ZYH and nr.dlmc = '现病史' and rownum=1) AS CURR_DISEASE_HISTORY, \n" +
            "(select nr.dlnr from emr_bl_dlnr  nr where nr.jzhm=ZB.ZYH and nr.dlmc = '既往史' and rownum=1) AS DISEASE_HISTORY, \n" +
            "'' AS SURGERY_HISTORY,'' AS METACHYSIS_HISTORY, '' AS ALLERGY_HISTORY, '' AS INFECT_DISEASE_HISTORY, \n" +
            "(SELECT listagg(GJ.ICD10, ',') within group(order by GJ.ICD10)  FROM YS_ZY_JBZD YZJ LEFT JOIN GY_JBBM GJ ON GJ.JBXH=YZJ.JBDM WHERE YZJ.JZHM=ZB.ZYH AND YZJ.ZDLB='初步诊断' " +
            "<if  test= \"admitDiagCode!=null and admitDiagCode!=''\"> AND GJ.ICD10= #{admitDiagCode} </if> " +//入院诊断代码（多个诊断代码用逗号分隔）
            ") AS ADMIT_DIAG_CODE, \n" +
            "(SELECT listagg(YZJ.JBMC, ',') within group(order by YZJ.JBMC)  FROM YS_ZY_JBZD YZJ WHERE YZJ.JZHM=ZB.ZYH AND YZJ.ZDLB='初步诊断' " +
            "<if  test= \"admitDiagName!=null and admitDiagName!=''\"> AND YZJ.JBMC like CONCAT(CONCAT('%',#{admitDiagName}),'%') </if>" +//入院诊断名称
            ")  AS ADMIT_DIAG_NAME, \n" +
            "ZB.MZYS,\n" +
            "(SELECT GYG1.YGXM FROM GY_YGDM GYG1 where GYG1.YGDM=ZB.MZYS) AS RECEPT_TREAT_DR_NAME,\n" +
            "ZB.ZYYS,\n" +
            "(SELECT GYG2.YGXM FROM GY_YGDM GYG2 where GYG2.YGDM=ZB.ZYYS) AS INHOSP_DR_NAME,\n" +
            "ZB.ZZYS,\n" +
            "(SELECT GYG3.YGXM FROM GY_YGDM GYG3 where GYG3.YGDM=ZB.ZZYS " +
            "<if  test= \"attendDrName!=null and attendDrName!=''\"> AND GYG3.YGXM= #{attendDrName} </if>" +//主治医师姓名
            ") AS ATTEND_DR_NAME,\n" +
            "ZB.ZSYS,\n" +
            "(SELECT GYG4.YGXM FROM GY_YGDM GYG4 where GYG4.YGDM=ZB.ZSYS) AS CHIEF_DR_NAME,\n" +
            "ZB.BRQK AS DISCHARGE_STATUS,to_char(ZB.CYRQ,'YYYY-MM-DD hh:mm:ss') AS CYRQ,  \n" +
            "(SELECT listagg(GJ.ICD10, ',') within group(order by GJ.ICD10)  FROM YS_ZY_JBZD YZJ LEFT JOIN GY_JBBM GJ ON GJ.JBXH=YZJ.JBDM WHERE YZJ.JZHM=ZB.ZYH AND YZJ.ZDLB='出院诊断' " +
            "<if  test= \"dischargeDiagCode!=null and dischargeDiagCode!=''\"> AND GJ.ICD10= #{dischargeDiagCode} </if> " +//出院诊断代码（多个诊断代码用逗号分隔）
            ") AS DISCHARGE_DIAG_CODE, \n" +
            "(SELECT listagg(YZJ.JBMC, ',') within group(order by YZJ.JBMC)  FROM YS_ZY_JBZD YZJ WHERE YZJ.JZHM=ZB.ZYH AND YZJ.ZDLB='出院诊断' " +
            "<if  test= \"dischargeDiagName!=null and dischargeDiagName!=''\"> AND YZJ.JBMC like CONCAT(CONCAT('%',#{dischargeDiagName}),'%') </if>" +//出院诊断名称
            ")  AS DISCHARGE_DIAG_NAME,  \n" +
            "ZB.CYFS,(SELECT GD2.DMMC FROM GY_DMZD GD2 where GD2.DMLB=23 AND GD2.DMSB=ZB.CYFS) AS DISCHARGE_METHOD_NAME,\n" +
            "(select nr.dlnr from emr_bl_dlnr  nr where nr.jzhm=ZB.ZYH and nr.dlmc = '体格检查' and rownum=1) AS PHYSICAL_EXAM, \n" +
            "'' AS TREATMENT_ADVICE, decode(ZB.CYPB,'0','3','1','1','8','1') as CYPB  \n" +
            "FROM ZY_BRRY ZB \n" +
            "LEFT JOIN MS_BRDA MB ON MB.BRID=ZB.BRID \n" +
            "LEFT JOIN YS_ZY_JZJL YZJ ON YZJ.JZHM=ZB.ZYH\n" +
            "LEFT JOIN BSEMR56.emr_bl_basysj BEBB ON BEBB.JZHM=to_char(ZB.ZYH) and BEBB.xmmc = '入院途径'\n" +
            "LEFT JOIN GY_DMZD GD1 ON GD1.DMLB=228 AND GD1.DMSB=BEBB.XMQZ\n" +
            "WHERE ZB.CYPB in (0,1,8) \n" +

            "<if  test= \"patIndexNo!=null and patIndexNo!=''\"> AND ZB.BRID= #{patIndexNo} </if>" +//患者索引号
            "<if  test= \"visitCardNo!=null and visitCardNo!=''\"> AND ZB.MZHM= #{visitCardNo} </if>" +//就诊卡号
            "<if  test= \"inhospNo!=null and inhospNo!=''\"> AND ZB.BAHM= #{inhospNo} </if>" +//住院号
            "<if  test= \"inhospSerialNo!=null and inhospSerialNo!=''\"> AND ZB.ZYH= #{inhospSerialNo} </if>" +//住院流水号
            "<if  test= \"patName!=null and patName!=''\"> AND ZB.BRXM= #{patName} </if>" +//患者姓名
            "<if  test= \"idNumber!=null and idNumber!=''\"> AND ZB.SFZH= #{idNumber} </if>" +//身份证号码
            "<if  test= \"mobileNo!=null and mobileNo!=''\"> AND ZB.LXDH= #{mobileNo} </if>" +//手机号
            "<if  test= \"bedNo!=null and bedNo!=''\"> AND ZB.BRCH= #{bedNo} </if>" +//病床号
            "<if  test= \"wardCode!=null and wardCode!=''\"> AND ZB.BRBQ= #{wardCode} </if>" +//病区代码
            "<if  test= \"deptCode!=null and deptCode!=''\"> AND ZB.BRKS= #{deptCode} </if>" +//科室代码
            "<if  test= \"inhospStatus!=null and inhospStatus!=''\"> AND ZB.CYPB= #{inhospStatus} </if>" +//在院状态
            "<if  test= \"admitStartDate!=null and admitStartDate!=''\"> and ZB.RYRQ &gt;= to_date(#{admitStartDate},'yyyy-MM-dd HH24:mi:ss') </if>" +//入院开始日期时间
            "<if  test= \"admitEndDate!=null and admitEndDate!=''\"> and ZB.RYRQ &lt;= to_date(#{admitEndDate},'yyyy-MM-dd HH24:mi:ss') </if>"+//入院结束日期时间
            "<if  test= \"startDate!=null and startDate!=''\"> and ZB.CYRQ &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>"+//开始日期时间
            "<if  test= \"endDate!=null and endDate!=''\"> and ZB.CYRQ &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if>" +//结束日期时间

            "<if  test= \"flag=='1'.toString()\"> order by ZB.CYRQ desc </if> \n" +
            ")A </script>")

    String getInhospRecordCount(PageData pd) ;

}
