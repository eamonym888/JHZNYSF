package com.bsoft.com.jhznysf.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.bsoft.com.jhznysf.mappers.JYBGMapper;
import com.bsoft.com.jhznysf.mappers.JYJGMapper;
import com.bsoft.com.jhznysf.service.JYBGService;
import com.bsoft.com.jhznysf.service.JYJGService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JYJGServiceImpl implements JYJGService {
    @Autowired
    private JYJGMapper jyjgmapper;

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
    public List<PageData> getTestReport(PageData pd) {
        return jyjgmapper.getTestReport(pd);
    }

    @Override
    public List<PageData> getMicrobeTestResult(PageData pd) {
        return jyjgmapper.getMicrobeTestResult(pd);
    }
}
