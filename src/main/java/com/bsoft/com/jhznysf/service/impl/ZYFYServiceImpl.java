package com.bsoft.com.jhznysf.service.impl;

import com.bsoft.com.jhznysf.mappers.MZFYMapper;
import com.bsoft.com.jhznysf.mappers.ZYFYMapper;
import com.bsoft.com.jhznysf.service.MZFYService;
import com.bsoft.com.jhznysf.service.ZYFYService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZYFYServiceImpl implements ZYFYService {
    @Autowired
    private ZYFYMapper zyfymapper;

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
    public List<PageData> getInhospFee(PageData pd) {
        return zyfymapper.getInhospFee(pd);
    }

    @Override
    public List<PageData> getInhospFeeDetail(PageData pd) {
        return zyfymapper.getInhospFeeDetail(pd);
    }

    @Override
    public List<PageData> getInhospBalance(PageData pd) {
        return zyfymapper.getInhospBalance(pd);
    }
}
