package com.mimota.dao.table;

import com.mimota.pojo.Order;
import com.mimota.util.MongoMorphiaUtil;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.stereotype.Service;

@Service
public class OrderTable {

    private Datastore datastore;

    public OrderTable() {

        datastore = MongoMorphiaUtil.getDataStore(Order.class);
    }

    public Key<Order> insert(Order order){
        return datastore.save(order);
    }
}
