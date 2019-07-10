package com.mimota.dao.table;

import com.google.common.collect.ImmutableMap;
import com.mimota.pojo.Product;
import com.mimota.util.MongoMorphiaUtil;
import com.mimota.util.common.Pair;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductTable {

    private Datastore datastore;

    public ProductTable(){
        datastore = MongoMorphiaUtil.getDataStore(Product.class);

    }

    public UpdateResults updateByPrimaryKeySelective(Product product){
        UpdateResults result = datastore.update(createQuery(product.getId()), createUpdate());
        return result;
    }

    public UpdateResults updateStatusByPrimaryKey(String productId, Integer status){
        return datastore.update(createQuery(productId), createUpdate(ImmutableMap.of("status", status)));
    }


    public Key<Product> insert(Product product){
        Key<Product> key = datastore.save(product);
        return key;
    }


    public Product selectByPrimaryKey(String productId){
        return createQuery(productId).get();
    }


    public Pair<Long, List<Product>> selectList(int offset, int pageSize){
        long totalNum = createQuery().countAll();
        return new Pair(totalNum, createQuery(offset, pageSize).asList());
    }

//    public List<Product> selectByNameAndProductId(String productName, String productId, int offset, int pageSize){
//
//        return createQuery(productName, productId, offset, pageSize).asList();
//    }
//
//    public List<Product> selectByNameAndCategoryIds(String keyword, List<String> categoryIdList, int offset, int pageSize){
//        return createQuery(keyword, categoryIdList, offset, pageSize).asList();
//    }

    Query<Product> createQuery(){
        return datastore.createQuery(Product.class);
    }

    Query<Product> createQuery(String productId){
        return createQuery().filter("_id", new ObjectId(productId));
    }

    UpdateOperations<Product> createUpdate(Map<String, Object> conditions){
        UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class);
        conditions.forEach((condition, o) -> {if(!conditions.equals("productId")) ops.set(condition, o);});
        return ops;
    }

    UpdateOperations<Product> createUpdate(){
        UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class);
        return ops;
    }

    Query<Product> createQuery(int offset, int pageSize){
        return createQuery().offset(offset).limit(pageSize);
    }

    public Pair<Long, List<Product>> selectByNameAndProductId(String productName, String productId, int offset, int pageSize){
        Query<Product> query = null;
        if(productId != null){
            query = createQuery(productId);
        }else{
            query = createQuery();
        }
        query.field("name").contains(productName);
        long totalNum = query.countAll();

        return new Pair<Long, List<Product>>(totalNum, query.offset(offset).limit(pageSize).asList());
    }

    public Pair<Long, List<Product>> selectByNameAndCategoryIds(String keyword, List<String> categoryIdList, int offset, int pageSize){
        Query<Product> query = createQuery();

        query.filter("categoryId in", categoryIdList);
        query.field("name").contains(keyword);
        long totalNum = query.countAll();

        return new Pair<Long, List<Product>>(totalNum, query.offset(offset).limit(pageSize).asList());
    }


}
