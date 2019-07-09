package com.mimota.dao.table;

import com.mimota.pojo.Shipping;
import com.mimota.util.MongoMorphiaUtil;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShippingTable {

    private Datastore datastore;

    public ShippingTable(){
        this.datastore = MongoMorphiaUtil.getDataStore(Shipping.class);
    }

    public Key<Shipping> insert(Shipping shipping){
        return datastore.save(shipping);
    }

    public int deleteByShippingIdUserId(String userId, String shippingId){

        WriteResult result = datastore.delete(createQuery(userId, shippingId));
        return result.getN();
    }

    public int updateByShipping(Shipping shipping){
        return 1;
    }

    public Shipping selectByShippingIdUserId(String userId, String shippingId){

        return createQuery(userId, shippingId).get();
    }

    public List<Shipping> selectByUserId(String userId){
        return createQuery(userId).asList();
    }

    public Query<Shipping> createQuery(){
        return datastore.createQuery(Shipping.class);
    }

    public Query<Shipping> createQuery(Map<String, String> conditions){
       Query<Shipping> query = createQuery();
       if(conditions != null){
           conditions.forEach((condition, str) -> query.filter(condition, str));
       }
       return query;
    }

    public Query<Shipping> createQuery(String userId, String shippingId){
        Query<Shipping> query = createQuery();
        query.filter("userId", new ObjectId(userId)).filter("_id", shippingId);
        return query;
    }
    public Query<Shipping> createQuery(String userId){
        Query<Shipping> query = createQuery();
        query.filter("userId", new ObjectId(userId));
        return query;
    }

}
