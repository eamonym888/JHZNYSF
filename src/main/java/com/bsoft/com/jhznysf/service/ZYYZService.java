package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.18	住院医嘱
 * */
@Service
public interface ZYYZService extends IBaseService {
    List<PageData> getInhospOrder(PageData pd);
    String getInhospOrderCount(PageData pd);
}
