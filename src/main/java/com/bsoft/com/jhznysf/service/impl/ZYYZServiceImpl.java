package com.bsoft.com.jhznysf.service.impl;

import com.bsoft.com.jhznysf.mappers.ZYJLMapper;
import com.bsoft.com.jhznysf.mappers.ZYYZMapper;
import com.bsoft.com.jhznysf.service.ZYJLService;
import com.bsoft.com.jhznysf.service.ZYYZService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZYYZServiceImpl implements ZYYZService {
    @Autowired
    private ZYYZMapper zyyzmapper;

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
    public List<PageData> getInhospOrder(PageData pd) {
        return zyyzmapper.getInhospOrder(pd);
    }
    @Override
    public String getInhospOrderCount(PageData pd) {
        return zyyzmapper.getInhospOrderCount(pd);
    }
}
