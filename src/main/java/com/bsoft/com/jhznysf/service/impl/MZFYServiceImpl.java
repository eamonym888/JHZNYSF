package com.bsoft.com.jhznysf.service.impl;

import com.bsoft.com.jhznysf.mappers.JZXXMapper;
import com.bsoft.com.jhznysf.mappers.MZFYMapper;
import com.bsoft.com.jhznysf.service.JZXXService;
import com.bsoft.com.jhznysf.service.MZFYService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MZFYServiceImpl implements MZFYService {
    @Autowired
    private MZFYMapper mzfymapper;

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
    public List<PageData> getOuthospFee(PageData pd) {
        return mzfymapper.getOuthospFee(pd);
    }
}
