package com.bsoft.com.jhznysf.controller;

import com.bsoft.com.jhznysf.constant.WsConst;
import com.bsoft.com.jhznysf.utils.PageData;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * his服务类 必须使用 @WebService
 * */
@WebService(targetNamespace = WsConst.NAMESPACE_URI ,name = "hisPortType")
public interface HisService {
    /**
     * 云随访入口
     * */
    @WebMethod(operationName="YSF")
    public String YSFRK(String XML);

}
