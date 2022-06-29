package com.bsoft.com.jhznysf.service;

import com.bsoft.com.jhznysf.base.service.IBaseService;
import com.bsoft.com.jhznysf.utils.PageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 3.2.27	诊断信息-门诊
 * 3.2.28	诊断信息-住院
 * */
@Service
public interface ZDXXService extends IBaseService {
    List<PageData> getOuthospDiag(PageData pd);
    List<PageData> getInhospDiag(PageData pd);
}
