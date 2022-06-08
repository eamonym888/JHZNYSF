package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.6	卡号信息(病人档案)
 * 3.2.7	患者基本信息
 * */
@Service
public interface BRDAService extends IBaseService {
    List<PageData> getCardNoInfo(PageData pd);
    List<PageData> getPatientInfo(PageData pd);
}
