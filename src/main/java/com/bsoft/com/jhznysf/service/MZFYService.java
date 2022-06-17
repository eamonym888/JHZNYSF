package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.15	门诊费用
 * */
@Service
public interface MZFYService extends IBaseService {
    List<PageData> getOuthospFee(PageData pd);
}
