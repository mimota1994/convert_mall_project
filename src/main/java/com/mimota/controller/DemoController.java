package com.mimota.controller;

import com.mimota.model.DemoEntity;
import com.mimota.service.demo.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    IDemoService demoService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(DemoEntity demoEntity){

        return demoService.createDemo(demoEntity);

    }

    @RequestMapping("/get")
    public DemoEntity get(String id){

        return demoService.getDemoById(id);

    }

    @RequestMapping("/update")
    public String update(DemoEntity demoEntity){

        return demoService.updateDemo(demoEntity);

    }

    @RequestMapping("/delete")
    public String delete(String id){

        return demoService.deleteDemo(id);

    }

}
