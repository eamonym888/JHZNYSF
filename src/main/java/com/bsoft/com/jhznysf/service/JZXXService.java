package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.8	就诊信息（门、急诊）
 * */
@Service
public interface JZXXService extends IBaseService {
    List<PageData> getVisitInfo(PageData pd);
}
