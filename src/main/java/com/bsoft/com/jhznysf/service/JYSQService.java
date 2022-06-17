package com.bsoft.com.jhznysf.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.21	检验申请单
 * */
@DS("jyzyylis")
@Service
public interface JYSQService extends IBaseService {
    List<PageData> getTestRequisition(PageData pd);
}
