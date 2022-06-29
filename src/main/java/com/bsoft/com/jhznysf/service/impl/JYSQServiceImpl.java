package com.bsoft.com.jhznysf.service.impl;

import com.bsoft.com.jhznysf.mappers.JCSQMapper;
import com.bsoft.com.jhznysf.mappers.JYSQMapper;
import com.bsoft.com.jhznysf.service.JCSQService;
import com.bsoft.com.jhznysf.service.JYSQService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JYSQServiceImpl implements JYSQService {
    @Autowired
    private JYSQMapper jysqmapper;

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
    public List<PageData> getTestRequisition(PageData pd) {
        return jysqmapper.getTestRequisition(pd);
    }

    @Override
    public List<PageData> getTestSample(PageData pd) {
        return jysqmapper.getTestSample(pd);
    }

}
