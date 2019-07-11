package com.mimota.dao.table;

import com.mimota.pojo.OrderItem;
import com.mimota.util.MongoMorphiaUtil;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemTable {

    private Datastore datastore;

    public OrderItemTable() {
        datastore = MongoMorphiaUtil.getDataStore(OrderItem.class);
    }

    public void batchInsert(List<OrderItem> orderItemList){
        datastore.save(orderItemList);
    }
}
