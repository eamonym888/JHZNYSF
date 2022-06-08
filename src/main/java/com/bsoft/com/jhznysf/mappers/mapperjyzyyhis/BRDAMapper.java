package com.bsoft.com.jhznysf.mappers.mapperjyzyyhis;


import com.bsoft.com.jhznysf.base.mapper.SqlMapper;
import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
//3.2.6	卡号信息
//3.2.7	患者基本信息
@Mapper
public interface BRDAMapper extends SqlMapper {

    @Select(" <script> SELECT GX.MRZ AS ORGAN_CODE,MB.BRID,MB.MZHM,DECODE(MB.BRXZ,1000,'0','1') AS VISIT_CARD_TYPE,MB.BRXM,MB.SFZH,DECODE(MB.ZXBZ,0,'1','0') AS ZXBZ,to_char(MB.JDSJ,'YYYY-MM-DD hh:mm:ss') AS JDSJ \n" +
            "FROM MS_BRDA MB \n" +
            "LEFT JOIN MS_GHMX MG ON MG.BRID=MB.BRID \n" +
            "LEFT JOIN GY_XTCS GX ON GX.CSMC='YLJGDM' \n" +
            "WHERE MB.BRXZ IN (1000,5000) \n"+
            "<if  test= \"visitCardNo!=null and visitCardNo!=''\"> and MB.MZHM = #{visitCardNo} </if>" +
            "<if  test= \"patName!=null and patName!=''\"> and MB.BRXM = #{patName} </if>" +
            "<if  test= \"idNumber!=null and idNumber!=''\"> and MB.SFZH = #{idNumber} </if>" +
            "<if  test= \"startDate!=null and startDate!=''\"> and MB.JDSJ &gt;= to_date(#{startDate},'yyyy-MM-dd HH24:mi:ss') </if>" +
            "<if  test= \"endDate!=null and endDate!=''\"> and MB.JDSJ &lt;= to_date(#{endDate},'yyyy-MM-dd HH24:mi:ss') </if></script>")
    List<PageData> getCardNoInfo(PageData pd) ;

    @Select(" <script> SELECT GX.MRZ AS ORGAN_CODE,MB.BRID,MB.MZHM,ZB.ZYHM,MB.BRXM,MB.SFZH,MB.CSNY,MB.BRXB,decode(MB.BRXB,1,'男','女') AS SEX_NAME, \n" +
            "MB.MZDM,GD1.DMMC AS ETHNIC_NAME,MB.JTDH,MB.LXRM,GD2.DMMC AS CONTACT_RELATION,MB.LXDH \n" +
            "FROM MS_BRDA MB\n" +
            "LEFT JOIN GY_XTCS GX ON GX.CSMC='YLJGDM'\n" +
            "LEFT JOIN ZY_BRRY ZB ON ZB.BRID=MB.BRID\n" +
            "LEFT JOIN GY_DMZD GD1 ON GD1.DMLB=1 AND GD1.DMSB=MB.MZDM\n" +
            "LEFT JOIN GY_DMZD GD2 ON GD2.DMLB=4 AND GD2.DMSB=MB.LXGX\n" +
            "WHERE 1=1 " +
            "<if  test= \"patIndexNo!=null and patIndexNo!=''\"> AND MB.BRID like CONCAT(CONCAT('%',#{patIndexNo}),'%') </if>" +
            "<if  test= \"patName!=null and patName!=''\"> AND MB.BRXM like CONCAT(CONCAT('%',#{patName}),'%') </if>" +
            "<if  test= \"idNumber!=null and idNumber!=''\"> AND MB.SFZH like CONCAT(CONCAT('%',#{idNumber}),'%') </if> </script>")
    List<PageData> getPatientInfo(PageData pd) ;

}
