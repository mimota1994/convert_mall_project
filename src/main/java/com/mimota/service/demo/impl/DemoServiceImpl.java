package com.mimota.service.demo.impl;

import com.mimota.dao.table.DemoTable;
import com.mimota.model.DemoEntity;
import com.mimota.service.demo.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    DemoTable demoTable;

    @Override
    public String createDemo(DemoEntity demoEntity) {
        if(demoTable.store(demoEntity)){
            return "ok";
        }else{
            return "error";
        }
    }

    @Override
    public DemoEntity getDemoById(String id) {
        DemoEntity demoEntity = demoTable.getById(id);
        return demoEntity;
    }

    @Override
    public String updateDemo(DemoEntity demoEntity) {
        demoTable.update(demoEntity);
        return "ok";
    }

    @Override
    public  String deleteDemo(String id) {
        demoTable.deleteById(id);
        return "ok";
    }
}
