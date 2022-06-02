package com.bsoft.com.jhznysf.service.impl;

import com.bsoft.com.jhznysf.mapperJYZYY.BRDAMapper;
import com.bsoft.com.jhznysf.mapperJYZYY.CFXXMapper;
import com.bsoft.com.jhznysf.service.BRDAService;
import com.bsoft.com.jhznysf.service.CFXXService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CFXXServiceImpl implements CFXXService {
    @Autowired
    private CFXXMapper cfxxmapper;

    @Override
    public JSONObject getTablePageData(Integer page, Integer limit, PageData pd) {
        return null;
    }

    @Override
    public void insertOrUpdate(PageData pd) {

    }

    @Override
    public void updateAll(PageData pd) {

    }

    @Override
    public void delete(PageData pd) {

    }

    @Override
    public PageData get(PageData pd) {
        return null;
    }

    @Override
    public List<PageData> getAll(PageData pd) {
        return null;
    }

    @Override
    public List<PageData> getOrderInfo(PageData pd) {
        return cfxxmapper.getOrderInfo(pd);
    }

    @Override
    public List<PageData> getOrderDetailInfo(PageData pd) {
        return cfxxmapper.getOrderDetailInfo(pd);
    }
}
