package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.17	住院费用
 * 3.2.23	住院费用明细
 * 3.2.25	住院费用结算
 * */
@Service
public interface ZYFYService extends IBaseService {
    List<PageData> getInhospFee(PageData pd);
    List<PageData> getInhospFeeDetail(PageData pd);
    List<PageData> getInhospBalance(PageData pd);
}
