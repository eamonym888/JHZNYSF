package com.bsoft.com.jhznysf.service.impl;

import com.bsoft.com.jhznysf.mappers.BRDAMapper;
import com.bsoft.com.jhznysf.mappers.ZDXXMapper;
import com.bsoft.com.jhznysf.service.BRDAService;
import com.bsoft.com.jhznysf.service.ZDXXService;
import com.bsoft.com.jhznysf.utils.PageData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZDXXServiceImpl implements ZDXXService {
    @Autowired
    private ZDXXMapper zdxxmapper;

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
    public List<PageData> getOuthospDiag(PageData pd) {
        return zdxxmapper.getOuthospDiag(pd);
    }

    @Override
    public List<PageData> getInhospDiag(PageData pd) {
        return zdxxmapper.getInhospDiag(pd);
    }
}
