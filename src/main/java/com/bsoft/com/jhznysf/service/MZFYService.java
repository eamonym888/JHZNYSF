package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.15	门诊费用
 * 3.2.22	门诊费用明细
 * 3.2.24	门诊费用结算
 * */
@Service
public interface MZFYService extends IBaseService {
    List<PageData> getOuthospFee(PageData pd);
    List<PageData> getOuthospFeeDetail(PageData pd) ;
    List<PageData> getOuthospBalance(PageData pd) ;
}
