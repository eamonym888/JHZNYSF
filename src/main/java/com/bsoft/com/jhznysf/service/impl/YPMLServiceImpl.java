package com.bsoft.com.jhznysf.service.impl;

import com.bsoft.com.jhznysf.mappers.mapperjyzyyhis.YPMLMapper;
import com.bsoft.com.jhznysf.service.YPMLService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YPMLServiceImpl implements YPMLService {
    @Autowired
    private YPMLMapper ypmlmapper;

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
        return ypmlmapper.getDrugDict(pd);
    }
}
