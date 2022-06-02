package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.16	住院记录
 * */
@Service
public interface ZYJLService extends IBaseService {
    List<PageData> getInhospRecord(PageData pd);
    String getInhospRecordCount(PageData pd);
}
