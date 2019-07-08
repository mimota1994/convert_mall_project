package com.mimota.service.demo;

import com.mimota.model.DemoEntity;

public interface IDemoService {

    String createDemo(DemoEntity demoEntity);

    DemoEntity getDemoById(String id);

    String updateDemo(DemoEntity demoEntity);

    String deleteDemo(String id);
}
