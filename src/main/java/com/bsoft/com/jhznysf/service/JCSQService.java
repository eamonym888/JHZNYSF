package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.20	检查申请单
 * */
@Service
public interface JCSQService extends IBaseService {
    List<PageData> getExamRequisition(PageData pd);
}
