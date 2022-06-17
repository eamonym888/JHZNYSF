package com.bsoft.com.jhznysf.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.13	检验结果
 * */
@DS("jyzyylis")
@Service
public interface JYJGService extends IBaseService {
    List<PageData> getTestReport(PageData pd);
    List<PageData> getMicrobeTestResult(PageData pd);
}
