package com.mimota.dao.table;

import com.google.common.collect.Lists;
import com.mimota.pojo.Shipping;
import com.mimota.util.MongoMorphiaUtil;
import com.mimota.util.common.Pair;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
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

    public int updateByShippingIdUserId(String userId, String shipping, Map<String, String> conditions){
        UpdateResults result = datastore.update(createQuery(userId, shipping), createUpdate(conditions), false);
        return result.getUpdatedCount();
    }

    public Shipping selectByShippingIdUserId(String userId, String shippingId){

        return createQuery(userId, shippingId).get();
    }

    public List<Shipping> selectByUserId(String userId){
        return createQuery(userId).asList();
    }

    public Pair<Long, List<Shipping>> search(String userId, int offset, int limit) {
        return search(createQuery(userId), offset, limit);
    }

    public Pair<Long, List<Shipping>> search(Query<Shipping> query, int offset, int limit) {
        final long countAll = query.countAll();
        if (countAll <= 0L) {
            return Pair.makePair(0L, Lists.newArrayList());
        }
        if (limit <= 0) {
            return Pair.makePair(countAll, Lists.newArrayList());
        }
        offset = offset < 0 ? 0 : offset;
        query.offset(offset).limit(limit);
        List<Shipping> shippingList = query.asList();
        return Pair.makePair(countAll, shippingList);
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
        query.filter("userId", userId).filter("_id", new ObjectId(shippingId));
        return query;
    }
    public Query<Shipping> createQuery(String userId){
        Query<Shipping> query = createQuery();
        query.filter("userId", userId);
        return query;
    }

    public UpdateOperations<Shipping> createUpdate(Map<String, String> conditions){
        UpdateOperations<Shipping> ops = datastore.createUpdateOperations(Shipping.class);
        conditions.forEach((condition, str) -> ops.set(condition, str));
        return ops;
    }

}
