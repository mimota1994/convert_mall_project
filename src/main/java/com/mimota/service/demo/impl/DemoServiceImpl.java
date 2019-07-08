package com.mimota.service.demo.impl;

import com.mimota.dao.table.DemoTable;
import com.mimota.model.DemoEntity;
import com.mimota.service.demo.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private DemoTable demoTable;

    public String createDemo(DemoEntity demoEntity) {
        if(demoTable.store(demoEntity)){
            return "ok";
        }else{
            return "error";
        }
    }

    public DemoEntity getDemoById(String id) {
        return demoTable.getById(id);

    }

    public String updateDemo(DemoEntity demoEntity) {
        demoTable.update(demoEntity);
        return "ok";
    }

    public  String deleteDemo(String id) {
        demoTable.deleteById(id);
        return "ok";
    }

    public String deleteAll() {
        demoTable.batchDelete(demoTable.createQuery());
        return "ok";
    }

    public List<DemoEntity> searchAll() {

        return demoTable.batchSearch(demoTable.createQuery());

    }
}
