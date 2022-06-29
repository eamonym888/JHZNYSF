package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.19	手术记录
 * */
@Service
public interface SSJLService extends IBaseService {
    List<PageData> getSurgeryRecord(PageData pd);
}
