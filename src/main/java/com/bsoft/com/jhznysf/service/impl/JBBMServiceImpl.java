package com.bsoft.com.jhznysf.service.impl;

import com.bsoft.com.jhznysf.mappers.JBBMMapper;
import com.bsoft.com.jhznysf.service.JBBMService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JBBMServiceImpl implements JBBMService {
    @Autowired
    private JBBMMapper jbbmmapper;

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
        return jbbmmapper.getDiagDict(pd);
    }
}
