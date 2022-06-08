package com.bsoft.com.jhznysf.controller.impl;

import com.bsoft.com.jhznysf.constant.WsConst;
import com.bsoft.com.jhznysf.controller.HisService;
import com.bsoft.com.jhznysf.service.*;
import com.bsoft.com.jhznysf.utils.PageData;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(
        targetNamespace = WsConst.NAMESPACE_URI, //wsdl命名空间
        name = "hisPortType",                 //portType名称 客户端生成代码时 为接口名称
        serviceName = "hisService",           //服务name名称
        portName = "hisPortName",             //port名称
        endpointInterface = "com.bsoft.com.jhznysf.controller.HisService")//指定发布webservcie的接口类，此类也需要接入@WebService注解
public class HisServiceImpl implements HisService {
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
    /**
     * 云随访入口
     * */
    @Override
    public String YSFRK(String XML) {
        Document document;
        try {
            document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("interface");//解析XML
            Element met1 = met.element("code");//解析XML接口代码
            //Element met2 = met.element("method");//解析XML接口方法
            String code = met1.getText();//接口代码
            //String method = met2.getText();//接口方法

            if(code.equals("1001")){//获取医院科室字典信息
                return this.getDeptDict(XML);
            }
            if(code.equals("1002")){//获取医院职工字典信息
                return this.getStaffDict(XML);
            }
            if(code.equals("1003")){//获取疾病字典
                return this.getDiagDict(XML);
            }
            if(code.equals("1047")){//获取药品字典
                return this.getDrugDict(XML);
            }

            if(code.equals("1004")){//3.2.6	卡号信息
                return this.getCardNoInfo(XML);
            }
            if(code.equals("1005")){//3.2.7	患者基本信息
                return this.getPatientInfo(XML);
            }
            if(code.equals("1006")){//3.2.8	就诊信息（门、急诊）
                return this.getVisitInfo(XML);
            }
            if(code.equals("1008")){//3.2.9	处方信息
                return this.getOrderInfo(XML);
            }
            if(code.equals("1009")){//3.2.10	处方明细信息
                return this.getOrderDetailInfo(XML);
            }

            if(code.equals("1011")){//3.2.12	检验报告
                return this.getTestReport(XML);
            }

            if(code.equals("1015")){//3.2.16	住院记录
                return this.getInhospRecord(XML);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return this.getFHJG("-1","request code does not exists","");
    }

    /**
     * 统一返回结果格式
     * */
    private String getFHJG(String returnCode,String returnException,String returnRow){
        if(returnCode.equals("1")){
            return "<root><retInfo><code>"+returnCode+"</code><name>成功，客户端请求成功，且有结果消息返回"+returnException+"</name></retInfo>"+ returnRow +"</root>";
        }else if(returnCode.equals("0")){
            return "<root><retInfo><code>"+returnCode+"</code><name>成功，客户端请求成功，但是无结果消息返回"+returnException+"</name></retInfo>"+ returnRow +"</root>";
        }else{
            return "<root><retInfo><code>"+returnCode+"</code><name>失败，客户端请求失败"+returnException+"</name></retInfo></root>";
        }
    }

    /**
     * 获取医院科室字典信息
     * */
    @Qualifier("KSDMServiceImpl")
    @Autowired
    private KSDMService ksdmservice;
    public String getDeptDict(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            Element met1 = met.element("DICT");//解析XML

            List<PageData> stringList = new ArrayList<>();
            StringBuilder str = new StringBuilder();
            if(met1.getText().equals("ALL")){
                stringList = ksdmservice.getAll(new PageData());
                for (PageData pgd :stringList) {
                    str.append("<msg><row>");
                    str.append("<ORGAN_CODE>");
                    str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                    str.append("</ORGAN_CODE>");
                    str.append("<DEPT_INDEX_NO>");
                    str.append(pgd.getString("PLSX"));//科室索引号
                    str.append("</DEPT_INDEX_NO>");
                    str.append("<DEPT_CODE>");
                    str.append(pgd.getString("KSDM"));//科室代码
                    str.append("</DEPT_CODE>");
                    str.append("<DEPT_NAME>");
                    str.append(pgd.getString("KSMC"));//科室名称
                    str.append("</DEPT_NAME>");
                    str.append("<DEPT_ALIAS>");
                    str.append(pgd.getString("KSMC"));//科室别名
                    str.append("</DEPT_ALIAS>");
                    str.append("<PINYIN_CODE>");
                    str.append(pgd.getString("PYDM"));//拼音码
                    str.append("</PINYIN_CODE>");
                    str.append("<OI_DEPT_FLAG>");
                    str.append(pgd.getString("OI_DEPT_FLAG"));//门诊住院科室标志
                    str.append("</OI_DEPT_FLAG>");
                    str.append("<SUPERIOR_DEPT_CODE>");
                    str.append(pgd.getString("SJKS"));//上级科室代码
                    str.append("</SUPERIOR_DEPT_CODE>");
                    str.append("<SUPERIOR_DEPT_NAME>");
                    str.append(pgd.getString("SJKSMC"));//上级科室名称
                    str.append("</SUPERIOR_DEPT_NAME>");
                    str.append("<DESCR>");
                    str.append(pgd.getString("KSMS"));//描述
                    str.append("</DESCR>");
                    str.append("<INVALID_FLAG>");
                    str.append(pgd.getString("ZFBZ"));//作废标志
                    str.append("</INVALID_FLAG>");
                    str.append("</row></msg>");
                }
            }
            if(!stringList.isEmpty()){
                 fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
            } catch (Exception e) {
                e.printStackTrace();
                return this.getFHJG("-1",e.getMessage(),"");
            }
        return fhjg;
    }

    /**
     * 获取医院职工字典信息
     * */
    @Qualifier("YGDMServiceImpl")
    @Autowired
    private YGDMService ygdmservice;
    public String getStaffDict(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            Element met1 = met.element("DICT");//解析XML

            List<PageData> stringList = new ArrayList<>();
            StringBuilder str = new StringBuilder();
            if(met1.getText().equals("ALL")){
                stringList = ygdmservice.getAll(new PageData());
                for (PageData pgd :stringList) {
                    str.append("<msg><row>");
                    str.append("<ORGAN_CODE>");
                    str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                    str.append("</ORGAN_CODE>");
                    str.append("<STAFF_INDEX_NO>");
                    str.append(pgd.getString("YGDM"));//职工索引号
                    str.append("</STAFF_INDEX_NO>");
                    str.append("<STAFF_CODE>");
                    str.append(pgd.getString("YGBH"));//职工工号
                    str.append("</STAFF_CODE>");
                    str.append("<STAFF_NAME>");
                    str.append(pgd.getString("YGXM"));//职工姓名
                    str.append("</STAFF_NAME>");
                    str.append("<PINYIN_CODE>");
                    str.append(pgd.getString("PYDM"));//拼音码
                    str.append("</PINYIN_CODE>");
                    str.append("<ID_NUMBER>");
                    str.append(pgd.getString("SFZ"));//身份证号码
                    str.append("</ID_NUMBER>");
                    str.append("<SEX_CODE>");
                    str.append(pgd.getString("YGXB"));//性别代码
                    str.append("</SEX_CODE>");
                    str.append("<SEX_NAME>");
                    str.append(pgd.getString("SEX_NAME"));//性别名称
                    str.append("</SEX_NAME>");
                    str.append("<TITLE_NAME>");
                    str.append(pgd.getString("TITLE_CODE"));//职称代码
                    str.append("</TITLE_NAME>");
                    str.append("<DESCR>");
                    str.append(pgd.getString("TITLE_NAME"));//职称名称
                    str.append("</DESCR>");
                    str.append("<MOBILE_NO>");
                    str.append(pgd.getString("SJHM"));//手机号码
                    str.append("</MOBILE_NO>");
                    str.append("<EMAIL>");
                    str.append(pgd.getString("EMAIL"));//邮箱
                    str.append("</EMAIL>");
                    str.append("<BIRTH_DATE>");
                    str.append(pgd.getString("CSNY"));//出生日期
                    str.append("</BIRTH_DATE>");
                    str.append("<STAFF_BRIEFING>");
                    str.append(pgd.getString("STAFF_BRIEFING"));//职工简介
                    str.append("</STAFF_BRIEFING>");
                    str.append("<GOOD_DESCR>");
                    str.append(pgd.getString("GOOD_DESCR"));//擅长说明
                    str.append("</GOOD_DESCR>");
                    str.append("<SUBOR_DEPT_CODE>");
                    str.append(pgd.getString("SUBOR_DEPT_CODE"));//从属科室代码
                    str.append("</SUBOR_DEPT_CODE>");
                    str.append("<SUBOR_DEPT_NAME>");
                    str.append(pgd.getString("SUBOR_DEPT_NAME"));//从属科室名称
                    str.append("</SUBOR_DEPT_NAME>");
                    str.append("<INVALID_FLAG>");
                    str.append(pgd.getString("ZFPB"));//作废标志
                    str.append("</INVALID_FLAG>");

                    str.append("</row></msg>");
                }
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }

    /**
     * 获取疾病字典
     * */
    @Qualifier("JBBMServiceImpl")
    @Autowired
    private JBBMService jbbmservice;
    public String getDiagDict(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            Element met1 = met.element("DICT");//解析XML

            List<PageData> stringList = new ArrayList<>();
            StringBuilder str = new StringBuilder();
            if(met1.getText().equals("ALL")){
                stringList = jbbmservice.getAll(new PageData());
                for (PageData pgd :stringList) {
                    str.append("<msg><row>");
                    str.append("<ORGAN_CODE>");
                    str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                    str.append("</ORGAN_CODE>");
                    str.append("<DIAG_INDEX_NO>");
                    str.append(pgd.getString("JBXH"));//疾病索引号
                    str.append("</DIAG_INDEX_NO>");
                    str.append("<DIAG_CODE>");
                    str.append(pgd.getString("DIAG_CODE"));//疾病代码
                    str.append("</DIAG_CODE>");
                    str.append("<DIAG_NAME>");
                    str.append(pgd.getString("JBMC"));//疾病名称
                    str.append("</DIAG_NAME>");
                    str.append("<PINYIN_CODE>");
                    str.append(pgd.getString("PYDM"));//拼音码
                    str.append("</PINYIN_CODE>");
                    str.append("</row></msg>");
                }
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }

    /**
     * 获取药品字典
     * */
    @Qualifier("YPMLServiceImpl")
    @Autowired
    private YPMLService ypmlservice;
    public String getDrugDict(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            Element met1 = met.element("DICT");//解析XML

            List<PageData> stringList = new ArrayList<>();
            StringBuilder str = new StringBuilder();
            if(met1.getText().equals("ALL")){
                stringList = ypmlservice.getAll(new PageData());
                for (PageData pgd :stringList) {
                    str.append("<msg><row>");
                    str.append("<ORGAN_CODE>");
                    str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                    str.append("</ORGAN_CODE>");
                    str.append("<DRUG_INDEX_NO>");
                    str.append(pgd.getString("YPXH"));//药品索引号
                    str.append("</DRUG_INDEX_NO>");
                    str.append("<DRUG_CODE>");
                    str.append(pgd.getString("YPDM"));//药品代码
                    str.append("</DRUG_CODE>");
                    str.append("<DRUG_NAME>");
                    str.append(pgd.getString("YPMC"));//药品名称
                    str.append("</DRUG_NAME>");
                    str.append("<PINYIN_CODE>");
                    str.append(pgd.getString("PYDM"));//拼音码
                    str.append("</PINYIN_CODE>");
                    str.append("</row></msg>");
                }
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }
    //...

    /**
     * 获取卡号信息
     * */
    @Qualifier("BRDAServiceImpl")
    @Autowired
    private BRDAService brdaservice;
    public String getCardNoInfo(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            String visitCardNo = met.element("VISIT_CARD_NO").getText();//解析XML就诊卡号
            String patName = met.element("PAT_NAME").getText();//解析XML患者姓名
            String idNumber = met.element("ID_NUMBER").getText();//解析XML身份证号码
            String startDate = met.element("START_DATE").getText();//解析XML开始日期时间
            String endDate = met.element("END_DATE").getText();//解析XML结束日期时间
            List<PageData> stringList;
            StringBuilder str = new StringBuilder();
            PageData pd = new PageData();
            pd.put("visitCardNo",visitCardNo);
            pd.put("patName",patName);
            pd.put("idNumber",idNumber);
            pd.put("startDate",startDate);
            pd.put("endDate",endDate);
            stringList = brdaservice.getCardNoInfo(pd);
            for (PageData pgd :stringList) {
                str.append("<msg><row>");
                str.append("<ORGAN_CODE>");
                str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                str.append("</ORGAN_CODE>");
                str.append("<PAT_INDEX_NO>");
                str.append(pgd.getString("BRID"));//患者索引号
                str.append("</PAT_INDEX_NO>");
                str.append("<VISIT_CARD_NO>");
                str.append(pgd.getString("MZHM"));//就诊卡号
                str.append("</VISIT_CARD_NO>");
                str.append("<VISIT_CARD_TYPE>");
                str.append(pgd.getString("VISIT_CARD_TYPE"));//就诊卡类别
                str.append("</VISIT_CARD_TYPE>");
                str.append("<PAT_NAME>");
                str.append(pgd.getString("BRXM"));//患者姓名
                str.append("</PAT_NAME>");
                str.append("<ID_NUMBER>");
                str.append(pgd.getString("SFZH"));//身份证号
                str.append("</ID_NUMBER>");
                str.append("<INVALID_FLAG>");
                str.append(pgd.getString("ZXBZ"));//作废标志
                str.append("</INVALID_FLAG>");
                str.append("<OPEN_DATE>");
                str.append(pgd.getString("JDSJ"));//开卡日期
                str.append("</OPEN_DATE>");
                str.append("</row></msg>");
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }

    /**
     * 根据身份证号、姓名等获取患者基本信息（参数可选）
     * */
    public String getPatientInfo(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            String patIndexNo = met.element("PAT_INDEX_NO").getText();//解析XML患者索引号
            String patName = met.element("PAT_NAME").getText();//解析XML患者姓名
            String idNumber = met.element("ID_NUMBER").getText();//解析XML身份证号码
            List<PageData> stringList;
            StringBuilder str = new StringBuilder();
            PageData pd = new PageData();
            pd.put("patIndexNo",patIndexNo);
            pd.put("patName",patName);
            pd.put("idNumber",idNumber);
            stringList = brdaservice.getPatientInfo(pd);
            for (PageData pgd :stringList) {
                str.append("<msg><row>");
                str.append("<ORGAN_CODE>");
                str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                str.append("</ORGAN_CODE>");
                str.append("<PAT_INDEX_NO>");
                str.append(pgd.getString("BRID"));//患者索引号
                str.append("</PAT_INDEX_NO>");
                str.append("<OUTHOSP_NO>");
                str.append(pgd.getString("MZHM"));//门诊号
                str.append("</OUTHOSP_NO>");
                str.append("<INHOSP_NO>");
                str.append(pgd.getString("ZYHM"));//住院号
                str.append("</INHOSP_NO>");
                str.append("<PAT_NAME>");
                str.append(pgd.getString("BRXM"));//患者姓名
                str.append("</PAT_NAME>");
                str.append("<ID_NUMBER>");
                str.append(pgd.getString("SFZH"));//身份证号
                str.append("</ID_NUMBER>");
                str.append("<BIRTH_DATE>");
                str.append(pgd.getString("CSNY"));//出生日期
                str.append("</BIRTH_DATE>");
                str.append("<SEX_CODE>");
                str.append(pgd.getString("BRXB"));//性别代码
                str.append("</SEX_CODE>");
                str.append("<SEX_NAME>");
                str.append(pgd.getString("SEX_NAME"));//性别名称
                str.append("</SEX_NAME>");
                str.append("<ETHNIC_CODE>");
                str.append(pgd.getString("MZDM"));//民族代码
                str.append("</ETHNIC_CODE>");
                str.append("<ETHNIC_NAME>");
                str.append(pgd.getString("ETHNIC_NAME"));//民族名称
                str.append("</ETHNIC_NAME>");
                str.append("<MOBILE_NO>");
                str.append(pgd.getString("JTDH"));//手机号码
                str.append("</MOBILE_NO>");
                str.append("<CONTACT_NAME>");
                str.append(pgd.getString("LXRM"));//联系人姓名
                str.append("</CONTACT_NAME>");
                str.append("<CONTACT_RELATION>");
                str.append(pgd.getString("CONTACT_RELATION"));//联系人关系
                str.append("</CONTACT_RELATION>");
                str.append("<CONTACT_PHONE_NO>");
                str.append(pgd.getString("LXDH"));//联系人电话
                str.append("</CONTACT_PHONE_NO>");
                str.append("</row></msg>");
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }

    /**
     * 根据患者索引号、就诊卡号等获取患者内的就诊信息（参数可选），可指定时间区间（需要支持分页）
     * */
    @Qualifier("JZXXServiceImpl")
    @Autowired
    private JZXXService jzxxservice;
    public String getVisitInfo(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met0 = root.element("page");//解析XML中page
            String flag = met0.element("flag").getText();//解析XML分页标志,0不分页，1分页
            String num = "";
            String size = "";
            if(flag.equals("1")){
                num = met0.element("num").getText();//解析XML当前的页数
                size = met0.element("size").getText();//解析XML每页显示的条数
            }
            Element met = root.element("param");//解析XML中param
            String visitCardNo = met.element("VISIT_CARD_NO").getText();//解析XML就诊卡号
            String patIndexNo = met.element("PAT_INDEX_NO").getText();//解析XML患者索引号
            String outhospNo = met.element("OUTHOSP_NO").getText();//解析XML门诊号
            String outhospSerialNo = met.element("OUTHOSP_SERIAL_NO").getText();//解析XML门诊流水号
            String patName = met.element("PAT_NAME").getText();//解析XML患者姓名
            String idNumber = met.element("ID_NUMBER").getText();//解析XML身份证号码
            String mobileNo = met.element("MOBILE_NO").getText();//解析XML手机号
            String receptTreatDrName = met.element("RECEPT_TREAT_DR_NAME").getText();//解析XML接诊医生姓名
            String visitDeptCode = met.element("VISIT_DEPT_CODE").getText();//解析XML就诊科室代码
            String visitDeptName = met.element("VISIT_DEPT_NAME").getText();//解析XML就诊科室名称
            String diagName = met.element("DIAG_NAME").getText();//解析XML诊断名称
            String startDate = met.element("START_DATE").getText();//解析XML开始日期时间
            String endDate = met.element("END_DATE").getText();//解析XML结束日期时间
            List<PageData> stringList;
            StringBuilder str = new StringBuilder();
            PageData pd = new PageData();

            pd.put("flag",flag);
            pd.put("num",num);
            pd.put("size",size);


            pd.put("visitCardNo",visitCardNo);
            pd.put("patIndexNo",patIndexNo);
            pd.put("outhospNo",outhospNo);
            pd.put("outhospSerialNo",outhospSerialNo);
            pd.put("patName",patName);
            pd.put("idNumber",idNumber);
            pd.put("mobileNo",mobileNo);
            pd.put("receptTreatDrName",receptTreatDrName);
            pd.put("visitDeptCode",visitDeptCode);
            pd.put("visitDeptName",visitDeptName);
            pd.put("diagName",diagName);
            pd.put("startDate",startDate);
            pd.put("endDate",endDate);


            stringList = jzxxservice.getVisitInfo(pd);
            for (PageData pgd :stringList) {
                str.append("<msg><row>");
                str.append("<ORGAN_CODE>");
                str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                str.append("</ORGAN_CODE>");
                str.append("<PAT_INDEX_NO>");
                str.append(pgd.getString("BRID"));//患者索引号
                str.append("</PAT_INDEX_NO>");
                str.append("<VISIT_CARD_NO>");
                str.append(pgd.getString("VISIT_CARD_NO"));//就诊卡号
                str.append("</VISIT_CARD_NO>");
                str.append("<PAT_NAME>");
                str.append(pgd.getString("BRXM"));//患者姓名
                str.append("</PAT_NAME>");
                str.append("<ID_NUMBER>");
                str.append(pgd.getString("SFZH"));//身份证号
                str.append("</ID_NUMBER>");
                str.append("<SEX_CODE>");
                str.append(pgd.getString("BRXB"));//性别代码
                str.append("</SEX_CODE>");
                str.append("<SEX_NAME>");
                str.append(pgd.getString("SEX_NAME"));//性别名称
                str.append("</SEX_NAME>");
                str.append("<BIRTH_DATE>");
                str.append(pgd.getString("CSNY"));//出生日期
                str.append("</BIRTH_DATE>");
                str.append("<MOBILE_NO>");
                str.append(pgd.getString("JTDH"));//手机号码
                str.append("</MOBILE_NO>");
                str.append("<OUTHOSP_NO>");
                str.append(pgd.getString("OUTHOSP_NO"));//门诊号
                str.append("</OUTHOSP_NO>");
                str.append("<OUTHOSP_SERIAL_NO>");
                str.append(pgd.getString("OUTHOSP_SERIAL_NO"));//门诊流水号
                str.append("</OUTHOSP_SERIAL_NO>");
                str.append("<PAT_TYPE_CODE>");
                str.append(pgd.getString("BRXZ"));//患者类型代码
                str.append("</PAT_TYPE_CODE>");
                str.append("<PAT_TYPE_NAME>");
                str.append(pgd.getString("PAT_TYPE_NAME"));//患者类型名称
                str.append("</PAT_TYPE_NAME>");
                str.append("<REGIST_NO>");
                str.append(pgd.getString("REGIST_NO"));//挂号流水号
                str.append("</REGIST_NO>");
                str.append("<REGIST_DATE>");
                str.append(pgd.getString("GHSJ"));//挂号日期时间
                str.append("</REGIST_DATE>");
                str.append("<VISIT_DATE>");
                str.append(pgd.getString("KSSJ"));//就诊日期时间
                str.append("</VISIT_DATE>");
                str.append("<VISIT_START_DATE>");
                str.append(pgd.getString("KSSJ"));//就诊开始时间
                str.append("</VISIT_START_DATE>");
                str.append("<VISIT_END_DATE>");
                str.append(pgd.getString("JSSJ"));//就诊结束时间
                str.append("</VISIT_END_DATE>");
                str.append("<CHIEF_DESCR>");
                str.append(pgd.getString("CHIEF_DESCR"));//主诉
                str.append("</CHIEF_DESCR>");
                str.append("<CURR_DISEASE_HISTORY >");
                str.append(pgd.getString("CURR_DISEASE_HISTORY"));//现病史
                str.append("</CURR_DISEASE_HISTORY >");
                str.append("<PAST_DISEASE_HISTORY>");
                str.append(pgd.getString("PAST_DISEASE_HISTORY"));//既往史
                str.append("</PAST_DISEASE_HISTORY>");
                str.append("<ALLERGY_HISTORY>");
                str.append(pgd.getString("ALLERGY_HISTORY"));//过敏史
                str.append("</ALLERGY_HISTORY>");
                str.append("<RECEPT_TREAT_DR_CODE>");
                str.append(pgd.getString("YSDM"));//接诊医生工号
                str.append("</RECEPT_TREAT_DR_CODE>");
                str.append("<RECEPT_TREAT_DR_NAME>");
                str.append(pgd.getString("RECEPT_TREAT_DR_NAME"));//接诊医生姓名
                str.append("</RECEPT_TREAT_DR_NAME>");
                str.append("<VISIT_DEPT_CODE>");
                str.append(pgd.getString("KSDM"));//就诊科室代码
                str.append("</VISIT_DEPT_CODE>");
                str.append("<VISIT_DEPT_NAME>");
                str.append(pgd.getString("VISIT_DEPT_NAME"));//就诊科室名称
                str.append("</VISIT_DEPT_NAME>");
                str.append("<DIAG_CODE>");
                str.append(pgd.getString("DIAG_CODE"));//诊断代码
                str.append("</DIAG_CODE>");
                str.append("<DIAG_NAME>");
                str.append(pgd.getString("DIAG_NAME"));//诊断名称
                str.append("</DIAG_NAME>");
                str.append("<PHYSICAL_EXAM>");
                str.append(pgd.getString("PHYSICAL_EXAM"));//体格检查
                str.append("</PHYSICAL_EXAM>");
                str.append("<TREATMENT_ADVICE>");
                str.append(pgd.getString("TREATMENT_ADVICE"));//处理意见
                str.append("</TREATMENT_ADVICE>");
                str.append("<DISEASES_REPORTED_FLAG>");
                str.append(pgd.getString("DISEASES_REPORTED_FLAG"));//疾病报卡标志
                str.append("</DISEASES_REPORTED_FLAG>");
                str.append("</row></msg>");
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }

    /**
     * 根据门诊流水号获取患者某一次就诊的处方信息（药物处方）
     * */
    @Qualifier("CFXXServiceImpl")
    @Autowired
    private CFXXService cfxxservice;
    public String getOrderInfo(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            String outhospSerialNo = met.element("OUTHOSP_SERIAL_NO").getText();//解析XML门诊流水号
            List<PageData> stringList;
            StringBuilder str = new StringBuilder();
            PageData pd = new PageData();
            pd.put("outhospSerialNo",outhospSerialNo);
            stringList = cfxxservice.getOrderInfo(pd);
            for (PageData pgd :stringList) {
                str.append("<msg><row>");
                str.append("<ORGAN_CODE>");
                str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                str.append("</ORGAN_CODE>");
                str.append("<PAT_INDEX_NO>");
                str.append(pgd.getString("BRID"));//患者索引号
                str.append("</PAT_INDEX_NO>");
                str.append("<OUTHOSP_NO>");
                str.append(pgd.getString("MZHM"));//门诊号
                str.append("</OUTHOSP_NO>");
                str.append("<OUTHOSP_SERIAL_NO>");
                str.append(pgd.getString("JZXH"));//门诊流水号
                str.append("</OUTHOSP_SERIAL_NO>");
                str.append("<PRES_NO>");
                str.append(pgd.getString("CFHM"));//处方号
                str.append("</PRES_NO>");
                str.append("<PRES_GROUP_NO>");
                str.append(pgd.getString("PRES_GROUP_NO"));//处方组号
                str.append("</PRES_GROUP_NO>");
                str.append("<PRES_OPEN_DEPT_CODE>");
                str.append(pgd.getString("KSDM"));//处方开立科室代码
                str.append("</PRES_OPEN_DEPT_CODE>");
                str.append("<PRES_OPEN_DEPT_NAME>");
                str.append(pgd.getString("PRES_OPEN_DEPT_NAME"));//处方开立科室名称
                str.append("</PRES_OPEN_DEPT_NAME>");
                str.append("<PRES_OPEN_DR_CODE>");
                str.append(pgd.getString("YSDM"));//处方开立医生工号
                str.append("</PRES_OPEN_DR_CODE>");
                str.append("<PRES_OPEN_DR_NAME>");
                str.append(pgd.getString("PRES_OPEN_DR_NAME"));//处方开立医生姓名
                str.append("</PRES_OPEN_DR_NAME>");
                str.append("<PRES_ORDER_DATE>");
                str.append(pgd.getString("PRES_ORDER_DATE"));//处方开立日期
                str.append("</PRES_ORDER_DATE>");
                str.append("<PRES_VALID_DAY>");
                str.append(pgd.getString("PRES_VALID_DAY"));//处方有效天数
                str.append("</PRES_VALID_DAY>");
                str.append("<PRES_CATEG_CODE>");
                str.append(pgd.getString("CFLX"));//处方类别代码
                str.append("</PRES_CATEG_CODE>");
                str.append("<PRES_CATEG_NAME>");
                str.append(pgd.getString("PRES_CATEG_NAME"));//处方类别名称
                str.append("</PRES_CATEG_NAME>");
                str.append("<CHARGE_FLAG>");
                str.append(pgd.getString("CHARGE_FLAG"));//收费标志
                str.append("</CHARGE_FLAG>");
                str.append("<NOTE>");
                str.append(pgd.getString("NOTE"));//备注
                str.append("</NOTE>");

                str.append("</row></msg>");
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }

    /**
     * 根据处方号获取处方明细信息
     * */
    public String getOrderDetailInfo(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            String presNo = met.element("PRES_NO").getText();//解析XML处方号
            List<PageData> stringList;
            StringBuilder str = new StringBuilder();
            PageData pd = new PageData();
            pd.put("presNo",presNo);
            stringList = cfxxservice.getOrderDetailInfo(pd);
            for (PageData pgd :stringList) {
                str.append("<msg><row>");
                str.append("<ORGAN_CODE>");
                str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                str.append("</ORGAN_CODE>");
                str.append("<PRES_NO\n>");
                str.append(pgd.getString("CFHM"));//处方号
                str.append("</PRES_NO\n>");
                str.append("<PRES_SUB_NO\n>");
                str.append(pgd.getString("SBXH"));//处方子序号
                str.append("</PRES_SUB_NO\n>");
                str.append("<PRES_GROUP_NO\n>");
                str.append(pgd.getString("PRES_GROUP_NO"));//处方组号
                str.append("</PRES_GROUP_NO\n>");
                str.append("<DRUG_CODE\n>");
                str.append(pgd.getString("YPXH"));//药品代码
                str.append("</DRUG_CODE\n>");
                str.append("<DRUG_NAME\n>");
                str.append(pgd.getString("DRUG_NAME"));//药品名称
                str.append("</DRUG_NAME\n>");
                str.append("<DRUG_SPECIFICATIONS\n>");
                str.append(pgd.getString("YFGG"));//药品规格
                str.append("</DRUG_SPECIFICATIONS\n>");
                str.append("<DOSE_WAY_CODE\n>");
                str.append(pgd.getString("DOSE_WAY_CODE"));//用药途径代码
                str.append("</DOSE_WAY_CODE\n>");
                str.append("<DOSE_WAY_NAME\n>");
                str.append(pgd.getString("DOSE_WAY_NAME"));//用药途径名称
                str.append("</DOSE_WAY_NAME\n>");
                str.append("<DRUG_USE_ONE_DOSAGE\n>");
                str.append(pgd.getString("DRUG_USE_ONE_DOSAGE"));//药品使用次剂量
                str.append("</DRUG_USE_ONE_DOSAGE\n>");
                str.append("<DRUG_USE_ONE_DOSAGE_UNIT\n>");
                str.append(pgd.getString("DRUG_USE_ONE_DOSAGE_UNIT"));//药品使用次剂量单位
                str.append("</DRUG_USE_ONE_DOSAGE_UNIT\n>");
                str.append("<DRUG_USE_FREQUENCY_CODE\n>");
                str.append(pgd.getString("DRUG_USE_FREQUENCY_CODE"));//药品使用频次代码
                str.append("</DRUG_USE_FREQUENCY_CODE\n>");
                str.append("<DRUG_USE_FREQUENCY_NAME\n>");
                str.append(pgd.getString("DRUG_USE_FREQUENCY_NAME"));//药品使用频次名称
                str.append("</DRUG_USE_FREQUENCY_NAME\n>");
                str.append("<DRUG_FORM_CODE\n>");
                str.append(pgd.getString("DRUG_FORM_CODE"));//药品剂型代码
                str.append("</DRUG_FORM_CODE\n>");
                str.append("<DRUG_FORM_NAME\n>");
                str.append(pgd.getString("DRUG_FORM_NAME"));//药品剂型名称
                str.append("</DRUG_FORM_NAME\n>");
                str.append("<DRUG_UNIT\n>");
                str.append(pgd.getString("DRUG_UNIT"));//药品单位
                str.append("</DRUG_UNIT\n>");
                str.append("<DRUG_UNIT_PRICE\n>");
                str.append(pgd.getString("DRUG_UNIT_PRICE"));//药品单价
                str.append("</DRUG_UNIT_PRICE\n>");
                str.append("<DRUG_ABBREV\n>");
                str.append(pgd.getString("DRUG_ABBREV"));//药品简称
                str.append("</DRUG_ABBREV\n>");
                str.append("<DRUG_DESCR\n>");
                str.append(pgd.getString("DRUG_DESCR"));//药品描述
                str.append("</DRUG_DESCR\n>");
                str.append("<PRES_SUSTAINED_DAYS\n>");
                str.append(pgd.getString("PRES_SUSTAINED_DAYS"));//处方持续天数
                str.append("</PRES_SUSTAINED_DAYS\n>");
                str.append("<DRUG_AMOUNT\n>");
                str.append(pgd.getString("YPSL"));//药品数量
                str.append("</DRUG_AMOUNT\n>");
                str.append("<BASE_AUX_DRUG_FLAG\n>");
                str.append(pgd.getString("BASE_AUX_DRUG_FLAG\n"));//主副药标志
                str.append("</BASE_AUX_DRUG_FLAG\n>");
                str.append("<SELF_DRUG_FLAG\n>");
                str.append(pgd.getString("SELF_DRUG_FLAG\n"));//自备药标志
                str.append("</SELF_DRUG_FLAG\n>");
                str.append("<GROUP_FLAG\n>");
                str.append(pgd.getString("GROUP_FLAG\n"));//成组标志
                str.append("</GROUP_FLAG\n>");
                str.append("<NOTE>");
                str.append(pgd.getString("NOTE"));//备注
                str.append("</NOTE>");

                str.append("</row></msg>");
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }

    /**
     * 根据门诊流水号、住院流水号等获取患者检验报告（参数可选）
     * */
    @Qualifier("JYBGServiceImpl")
    @Autowired
    private JYBGService jybgservice;
    public String getTestReport(String XML) {
        String fhjg;
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met = root.element("param");//解析XML
            String patIndexNo = met.element("PAT_INDEX_NO").getText();//解析XML患者索引号
            String outhospNo = met.element("OUTHOSP_NO").getText();//解析XML门诊号
            String inhospNo = met.element("INHOSP_NO").getText();//解析XML住院号
            String barcodeNo = met.element("BARCODE_NO").getText();//解析XML条码号(多个条码号用逗号分隔)
            String reportNo = met.element("REPORT_NO").getText();//解析XML报告单号
            String startDate = met.element("START_DATE").getText();//解析XML开始日期时间
            String endDate = met.element("END_DATE").getText();//解析XML结束日期时间
            List<PageData> stringList;
            StringBuilder str = new StringBuilder();
            PageData pd = new PageData();
            pd.put("patIndexNo",patIndexNo);
            pd.put("outhospNo",outhospNo);
            pd.put("inhospNo",inhospNo);
            pd.put("barcodeNo",barcodeNo);
            pd.put("reportNo",reportNo);
            pd.put("startDate",startDate);
            pd.put("endDate",endDate);

            stringList = jybgservice.getTestReport(pd);
            for (PageData pgd :stringList) {
                str.append("<msg><row>");
                str.append("<ORGAN_CODE>");
                str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                str.append("</ORGAN_CODE>");
                str.append("<PAT_INDEX_NO\n>");
                str.append(pgd.getString("PAT_INDEX_NO"));//患者索引号
                str.append("</PAT_INDEX_NO\n>");
                str.append("<OUTHOSP_NO\n>");
                str.append(pgd.getString("OUTHOSP_NO"));//门诊号
                str.append("</OUTHOSP_NO\n>");
                str.append("<INHOSP_NO\n>");
                str.append(pgd.getString("INHOSP_NO"));//住院号
                str.append("</INHOSP_NO\n>");
                str.append("<REQUISITION_NO\n>");
                str.append(pgd.getString("REQUISITION_NO"));//申请单编号
                str.append("</REQUISITION_NO\n>");
                str.append("<REQUISITION_NO_ITEM\n>");
                str.append(pgd.getString("REQUISITION_NO_ITEM"));//申请单分项目序号
                str.append("</REQUISITION_NO_ITEM\n>");
                str.append("<REPORT_NO\n>");
                str.append(pgd.getString("REPORT_NO"));//报告单编号
                str.append("</REPORT_NO\n>");
                str.append("<BARCODE_NO\n>");
                str.append(pgd.getString("BARCODE_NO"));//条码号
                str.append("</BARCODE_NO\n>");
                str.append("<REPORT_NAME\n>");
                str.append(pgd.getString("REPORT_NAME"));//报告名称
                str.append("</REPORT_NAME\n>");
                str.append("<SAMPLE_TYPE_CODE\n>");
                str.append(pgd.getString("SAMPLE_TYPE_CODE"));//样本类型代码
                str.append("</SAMPLE_TYPE_CODE\n>");
                str.append("<SAMPLE_TYPE_NAME\n>");
                str.append(pgd.getString("SAMPLE_TYPE_NAME"));//样本类型名称
                str.append("</SAMPLE_TYPE_NAME\n>");
                str.append("<TEST_ITEM_CODE\n>");
                str.append(pgd.getString("TEST_ITEM_CODE"));//检验项目代码
                str.append("</TEST_ITEM_CODE\n>");
                str.append("<TEST_ ITEM _NAME\n>");
                str.append(pgd.getString("TEST_ ITEM _NAME"));//检验项目名称
                str.append("</TEST_ ITEM _NAME\n>");
                str.append("<MICROBE_TEST_FLAG\n>");
                str.append(pgd.getString("MICROBE_TEST_FLAG"));//微生物检验标志
                str.append("</MICROBE_TEST_FLAG\n>");
                str.append("<EQUIPMENT_CODE\n>");
                str.append(pgd.getString("EQUIPMENT_CODE"));//设备代码
                str.append("</EQUIPMENT_CODE\n>");
                str.append("<EQUIPMENT_NAME\n>");
                str.append(pgd.getString("EQUIPMENT_NAME"));//设备名称
                str.append("</EQUIPMENT_NAME\n>");
                str.append("<APPLY_DATE\n>");
                str.append(pgd.getString("APPLY_DATE"));//申请日期
                str.append("</APPLY_DATE\n>");
                str.append("<APPLY_DEPT_CODE\n>");
                str.append(pgd.getString("APPLY_DEPT_CODE"));//申请科室代码
                str.append("</APPLY_DEPT_CODE\n>");
                str.append("<APPLY_DEPT_NAME\n>");
                str.append(pgd.getString("APPLY_DEPT_NAME"));//申请科室名称
                str.append("</APPLY_DEPT_NAME\n>");
                str.append("<APPLY_DR_CODE\n>");
                str.append(pgd.getString("APPLY_DR_CODE"));//申请医生工号
                str.append("</APPLY_DR_CODE\n>");
                str.append("<APPLY_DR_NAME\n>");
                str.append(pgd.getString("APPLY_DR_NAME"));//申请医生姓名
                str.append("</APPLY_DR_NAME\n>");
                str.append("<EXECUTE_DATE\n>");
                str.append(pgd.getString("EXECUTE_DATE\n"));//执行日期
                str.append("</EXECUTE_DATE\n>");
                str.append("<REPORT_DATE\n>");
                str.append(pgd.getString("REPORT_DATE\n"));//报告日期
                str.append("</REPORT_DATE\n>");
                str.append("<NOTE>");
                str.append(pgd.getString("NOTE"));//备注
                str.append("</NOTE>");

                str.append("</row></msg>");
            }
            if(!stringList.isEmpty()){
                fhjg =this.getFHJG("1","",str.toString());
            }else{
                fhjg =this.getFHJG("0","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }


    /**
     * 根据就诊卡号、患者姓名、身份证号码等获取患者内住院记录（参数可选），可指定时间区间（需要支持分页）
     * */
    @Qualifier("ZYJLServiceImpl")
    @Autowired
    private ZYJLService zyjlservice;
    public String getInhospRecord(String XML) {
        String fhjg = "";
        try {
            Document document = DocumentHelper.parseText(XML);
            Element root = document.getRootElement();
            Element met0 = root.element("page");//解析XML中page
            String flag = met0.element("flag").getText();//解析XML分页标志,0不分页，1分页
            String num = "";
            String size = "";
            if(flag.equals("1")){
                num = met0.element("num").getText();//解析XML当前的页数
                size = met0.element("size").getText();//解析XML每页显示的条数
            }
            Element met = root.element("param");//解析XML中param
            String statistics = met.element("STATISTICS").getText();//解析XML统计标识

            String patIndexNo = met.element("PAT_INDEX_NO").getText();//解析XML患者索引号
            String visitCardNo = met.element("VISIT_CARD_NO").getText();//解析XML就诊卡号
            String inhospNo = met.element("INHOSP_NO").getText();//解析XML住院号
            String inhospSerialNo = met.element("INHOSP_SERIAL_NO").getText();//解析XML住院流水号
            String patName = met.element("PAT_NAME").getText();//解析XML患者姓名
            String idNumber = met.element("ID_NUMBER").getText();//解析XML身份证号码
            String mobileNo = met.element("MOBILE_NO").getText();//解析XML手机号码
            String sickroomNo = met.element("SICKROOM_NO").getText();//解析XML病房号
            String bedNo = met.element("BED_NO").getText();//解析XML病床号
            String wardCode = met.element("WARD_CODE").getText();//解析XML病区代码
            String wardName = met.element("WARD_NAME").getText();//解析XML病区名称
            String deptCode = met.element("DEPT_CODE").getText();//解析XML科室代码
            String deptName = met.element("DEPT_NAME").getText();//解析XML科室名称
            String attendDrName = met.element("ATTEND_DR_NAME").getText();//解析XML主治医师姓名
            String admitDiagCode = met.element("ADMIT_DIAG_CODE").getText();//解析XML入院诊断代码
            String admitDiagName = met.element("ADMIT_DIAG_NAME").getText();//解析XML入院诊断名称
            String dischargeDiagCode = met.element("DISCHARGE_DIAG_CODE").getText();//解析XML出院诊断代码
            String dischargeDiagName = met.element("DISCHARGE_DIAG_NAME").getText();//解析XML出院诊断名称
            String inhospStatus = met.element("INHOSP_STATUS").getText();//解析XML在院状态
            String admitStartDate = met.element("ADMIT_START_DATE").getText();//解析XML入院开始日期时间
            String admitEndDate = met.element("ADMIT_END_DATE").getText();//解析XML入院结束日期时间
            String startDate = met.element("START_DATE").getText();//解析XML出院开始日期时间
            String endDate = met.element("END_DATE").getText();//解析XML出院结束日期时间

            List<PageData> stringList;
            StringBuilder str = new StringBuilder();
            PageData pd = new PageData();

            pd.put("flag",flag);
            pd.put("num",num);
            pd.put("size",size);

            pd.put("patIndexNo",patIndexNo);
            pd.put("visitCardNo",visitCardNo);
            pd.put("inhospNo",inhospNo);
            pd.put("inhospSerialNo",inhospSerialNo);
            pd.put("patName",patName);
            pd.put("idNumber",idNumber);
            pd.put("mobileNo",mobileNo);
            pd.put("sickroomNo",sickroomNo);
            pd.put("bedNo",bedNo);
            pd.put("wardCode",wardCode);
            pd.put("wardName",wardName);
            pd.put("deptCode",deptCode);
            pd.put("deptName",deptName);
            pd.put("attendDrName",attendDrName);
            pd.put("admitDiagCode",admitDiagCode);
            pd.put("admitDiagName",admitDiagName);
            pd.put("dischargeDiagCode",dischargeDiagCode);
            pd.put("dischargeDiagName",dischargeDiagName);
            pd.put("inhospStatus",inhospStatus);
            pd.put("admitStartDate",admitStartDate);
            pd.put("admitEndDate",admitEndDate);
            pd.put("startDate",startDate);
            pd.put("endDate",endDate);

            if(statistics.equals("0")){
                stringList = zyjlservice.getInhospRecord(pd);
                String totalCount = zyjlservice.getInhospRecordCount(pd);//总记录数
                for (PageData pgd :stringList) {
                    str.append("<count>");
                    str.append(totalCount);//总记录数
                    str.append("</count>");
                    str.append("<msg><row>");
                    str.append("<ORGAN_CODE>");
                    str.append(pgd.getString("ORGAN_CODE"));//组织机构代码
                    str.append("</ORGAN_CODE>");
                    str.append("<PAT_INDEX_NO>");
                    str.append(pgd.getString("BRID"));//患者索引号
                    str.append("</PAT_INDEX_NO>");
                    str.append("<VISIT_CARD_NO>");
                    str.append(pgd.getString("MZHM"));//就诊卡号
                    str.append("</VISIT_CARD_NO>");
                    str.append("<PAT_NAME>");
                    str.append(pgd.getString("BRXM"));//患者姓名
                    str.append("</PAT_NAME>");
                    str.append("<ID_NUMBER>");
                    str.append(pgd.getString("SFZH"));//身份证号
                    str.append("</ID_NUMBER>");
                    str.append("<SEX_CODE>");
                    str.append(pgd.getString("BRXB"));//性别代码
                    str.append("</SEX_CODE>");
                    str.append("<SEX_NAME>");
                    str.append(pgd.getString("SEX_NAME"));//性别名称
                    str.append("</SEX_NAME>");
                    str.append("<BIRTH_DATE>");
                    str.append(pgd.getString("CSNY"));//出生日期
                    str.append("</BIRTH_DATE>");
                    str.append("<MOBILE_NO>");
                    str.append(pgd.getString("LXDH"));//手机号码
                    str.append("</MOBILE_NO>");
                    str.append("<INHOSP_NO>");
                    str.append(pgd.getString("ZYHM"));//住院号
                    str.append("</INHOSP_NO>");
                    str.append("<INHOSP_NUM>");
                    str.append(pgd.getString("ZYCS"));//住院次数
                    str.append("</INHOSP_NUM>");
                    str.append("<INHOSP_SERIAL_NO>");
                    str.append(pgd.getString("ZYH"));//住院流水号
                    str.append("</INHOSP_SERIAL_NO>");
                    str.append("<DEPT_CODE>");
                    str.append(pgd.getString("BRKS"));//科室代码
                    str.append("</DEPT_CODE>");
                    str.append("<DEPT_NAME>");
                    str.append(pgd.getString("DEPT_NAME"));//科室名称
                    str.append("</DEPT_NAME>");
                    str.append("<WARD_CODE>");
                    str.append(pgd.getString("BRBQ"));//病区代码
                    str.append("</WARD_CODE>");
                    str.append("<WARD_NAME>");
                    str.append(pgd.getString("WARD_NAME"));//病区名称
                    str.append("</WARD_NAME>");
                    str.append("<SICKROOM_NO>");
                    str.append(pgd.getString("SICKROOM_NO"));//病房号
                    str.append("</SICKROOM_NO>");
                    str.append("<BED_NO>");
                    str.append(pgd.getString("BRCH"));//病床号
                    str.append("</BED_NO>");
                    str.append("<ADMIT_DATE>");
                    str.append(pgd.getString("RYRQ"));//入院日期
                    str.append("</ADMIT_DATE>");
                    str.append("<ADMIT_SITUATION>");
                    str.append(pgd.getString("ADMIT_SITUATION"));//入院情况
                    str.append("</ADMIT_SITUATION>");
                    str.append("<ADMIT_WAY_CODE>");
                    str.append(pgd.getString("ADMIT_WAY_CODE"));//入院途径代码
                    str.append("</ADMIT_WAY_CODE>");
                    str.append("<ADMIT_WAY_NAME>");
                    str.append(pgd.getString("ADMIT_WAY_NAME"));//入院途径名称
                    str.append("</ADMIT_WAY_NAME>");
                    str.append("<PAT_CHIEF_DESCR>");
                    str.append(pgd.getString("PAT_CHIEF_DESCR"));//患者主诉
                    str.append("</PAT_CHIEF_DESCR>");
                    str.append("<BRIEF_DISEASE_SITUATION>");
                    str.append(pgd.getString("BRIEF_DISEASE_SITUATION"));//简要病情
                    str.append("</BRIEF_DISEASE_SITUATION>");
                    str.append("<CURR_DISEASE_HISTORY>");
                    str.append(pgd.getString("CURR_DISEASE_HISTORY"));//现病史
                    str.append("</CURR_DISEASE_HISTORY>");
                    str.append("<DISEASE_HISTORY>");
                    str.append(pgd.getString("DISEASE_HISTORY"));//疾病史
                    str.append("</DISEASE_HISTORY>");
                    str.append("<SURGERY_HISTORY>");
                    str.append(pgd.getString("SURGERY_HISTORY"));//手术史
                    str.append("</SURGERY_HISTORY>");
                    str.append("<METACHYSIS_HISTORY>");
                    str.append(pgd.getString("METACHYSIS_HISTORY"));//输血史
                    str.append("</METACHYSIS_HISTORY>");
                    str.append("<ALLERGY_HISTORY>");
                    str.append(pgd.getString("ALLERGY_HISTORY"));//过敏史
                    str.append("</ALLERGY_HISTORY>");
                    str.append("<INFECT_DISEASE_HISTORY>");
                    str.append(pgd.getString("INFECT_DISEASE_HISTORY"));//传染病史
                    str.append("</INFECT_DISEASE_HISTORY>");
                    str.append("<ADMIT_DIAG_CODE>");
                    str.append(pgd.getString("ADMIT_DIAG_CODE"));//入院诊断代码
                    str.append("</ADMIT_DIAG_CODE>");
                    str.append("<ADMIT_DIAG_NAME>");
                    str.append(pgd.getString("_DIAG_NAME"));//入院诊断名称
                    str.append("</ADMIT_DIAG_NAME>");
                    str.append("<RECEPT_TREAT_DR_CODE>");
                    str.append(pgd.getString("MZYS"));//接诊医师工号
                    str.append("</RECEPT_TREAT_DR_CODE>");
                    str.append("<RECEPT_TREAT_DR_NAME>");
                    str.append(pgd.getString("RECEPT_TREAT_DR_NAME"));//接诊医师姓名
                    str.append("</RECEPT_TREAT_DR_NAME>");
                    str.append("<INHOSP_DR_CODE>");
                    str.append(pgd.getString("ZYYS"));//住院医师工号
                    str.append("</INHOSP_DR_CODE>");
                    str.append("<INHOSP_DR_NAME>");
                    str.append(pgd.getString("INHOSP_DR_NAME"));//住院医师姓名
                    str.append("</INHOSP_DR_NAME>");
                    str.append("<ATTEND_DR_CODE>");
                    str.append(pgd.getString("ZZYS"));//主治医师工号
                    str.append("</ATTEND_DR_CODE>");
                    str.append("<ATTEND_DR_NAME>");
                    str.append(pgd.getString("ATTEND_DR_NAME"));//主治医师姓名
                    str.append("</ATTEND_DR_NAME>");
                    str.append("<CHIEF_DR_CODE>");
                    str.append(pgd.getString("ZSYS"));//主任医师工号
                    str.append("</CHIEF_DR_CODE>");
                    str.append("<CHIEF_DR_NAME>");
                    str.append(pgd.getString("CHIEF_DR_NAME"));//主任医师姓名
                    str.append("</CHIEF_DR_NAME>");
                    str.append("<DISCHARGE_STATUS>");
                    str.append(pgd.getString("DISCHARGE_STATUS"));//出院情况
                    str.append("</DISCHARGE_STATUS>");
                    str.append("<DISCHARGE_DATE>");
                    str.append(pgd.getString("CYRQ"));//出院日期时间
                    str.append("</DISCHARGE_DATE>");
                    str.append("<DISCHARGE_DIAG_NAME>");
                    str.append(pgd.getString("DISCHARGE_DIAG_CODE"));//出院诊断代码
                    str.append("</DISCHARGE_DIAG_NAME>");
                    str.append("<DISCHARGE_DIAG_NAME>");
                    str.append(pgd.getString("DISCHARGE_DIAG_NAME"));//出院诊断名称
                    str.append("</DISCHARGE_DIAG_NAME>");
                    str.append("<OUTCOME_CODE>");
                    str.append(pgd.getString("CYFS"));//转归情况代码
                    str.append("</OUTCOME_CODE>");
                    str.append("<OUTCOME_NAME>");
                    str.append(pgd.getString("DISCHARGE_METHOD_NAME"));//转归情况名称
                    str.append("</OUTCOME_NAME>");
                    str.append("<DISCHARGE_METHOD_CODE>");
                    str.append(pgd.getString("CYFS"));//离院方式代码
                    str.append("</DISCHARGE_METHOD_CODE>");
                    str.append("<DISCHARGE_METHOD_NAME>");
                    str.append(pgd.getString("DISCHARGE_METHOD_NAME"));//离院方式名称
                    str.append("</DISCHARGE_METHOD_NAME>");
                    str.append("<PHYSICAL_EXAM>");
                    str.append(pgd.getString("PHYSICAL_EXAM"));//体格检查
                    str.append("</PHYSICAL_EXAM>");
                    str.append("<TREATMENT_ADVICE>");
                    str.append(pgd.getString("TREATMENT_ADVICE"));//处理意见
                    str.append("</TREATMENT_ADVICE>");
                    str.append("<INHOSP_STATUS >");
                    str.append(pgd.getString("CYPB"));//在院状态
                    str.append("</INHOSP_STATUS >");
                    str.append("</row></msg>");
                }
                if(!stringList.isEmpty()){
                    fhjg =this.getFHJG("1","",str.toString());
                }else{
                    fhjg =this.getFHJG("0","",str.toString());
                }
            }else if(statistics.equals("1")) {
                str.append("<count>");
                str.append(zyjlservice.getInhospRecordCount(pd));//总记录数
                str.append("</count>");
                fhjg =this.getFHJG("1","",str.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFHJG("-1",e.getMessage(),"");
        }
        return fhjg;
    }
}
