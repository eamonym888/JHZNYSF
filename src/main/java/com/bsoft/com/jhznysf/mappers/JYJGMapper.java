package com.bsoft.com.jhznysf.mappers;


import com.bsoft.com.jhznysf.utils.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;



@Mapper
public interface JYJGMapper {
    //3.2.13	检验结果
    @Select(" <script> SELECT (SELECT GX.CSZ1 FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE, \n" +
            "lt.sampleno as REPORT_NO, \n" +
            "lt.pxxh as REPORT_RESULT_NO,\n" +
            "lt.testid as TEST_ITEM_CODE, \n" +
            "lt.chinesename as TEST_ITEM_NAME, \n" +
            "lt.testresult as TEST_RESULT_VALUE, \n" +
            "lt.unit as TEST_RESULT_VALUE_UNIT, \n" +
            "lt.reflo||'-'||lt.refhi as REFERENCE_RANGES,\n" +
            "lt.refhi as REFERENCE_UPPER_LIMIT, \n" +
            "lt.reflo as REFERENCE_LOWER_LIMIT, \n" +
            "lt.hint as NORMAL_FLAG\n" +
            "FROM v_lis_testresult_all lt\n" +
            "where lt.sampleno=#{reportNo} </script>")
    List<PageData> getTestReport(PageData pd) ;

    //3.2.14	微生物检验结果
    @Select(" <script> SELECT (SELECT GX.CSZ1 FROM GY_XTCS GX where GX.CSMC='YLJGDM_NEW') AS ORGAN_CODE, \n" +
            "lt.sampleno as REPORT_NO, \n" +
            "lt.pxxh as REPORT_RESULT_NO,\n" +
            "--lt.testid as TEST_ITEM_CODE, \n" +
            "lt.chinesename as MICROBE_NAME, \n" +
            "'' as BACTERIAL_COLONY_COUNT, \n" +
            "'' as ANTIBIOTICS, \n" +
            "lt.testresult as TEST_RESULT_VALUE, \n" +
            "lt.unit as TEST_RESULT_VALUE_UNIT, \n" +
            "'' as TEST_QUALITATIVE_RESULT, \n" +
            "'' as TEST_METHOD, \n" +
            "lt.reflo||'-'||lt.refhi as REFERENCE_RANGES,\n" +
            "lt.refhi as REFERENCE_UPPER_LIMIT, \n" +
            "lt.reflo as REFERENCE_LOWER_LIMIT, \n" +
            "'' as NOTE,\n" +
            "lt.hint as NORMAL_FLAG\n" +
            "FROM v_lis_testresult_all lt\n" +
            "where lt.labdepartment='3084' and lt.sampleno=#{reportNo} </script>")
    List<PageData> getMicrobeTestResult(PageData pd) ;

}
