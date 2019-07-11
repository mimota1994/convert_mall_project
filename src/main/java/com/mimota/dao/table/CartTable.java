package com.mimota.dao.table;

import com.mimota.pojo.Cart;
import com.mimota.util.MongoMorphiaUtil;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartTable {

    private Datastore datastore;

    public CartTable(){
        datastore = MongoMorphiaUtil.getDataStore(Cart.class);
    }

    public Cart selectCartByUserIdProductId(String userId, String productId){
        return createQuery(userId, productId).get();
    }

    public Key<Cart> insert(Cart cart){
        return datastore.save(cart);
    }

    public void updateByPrimaryKeySelective(Cart cart){

    }


    public void updateByPrimaryKey(Cart cart){

    }

    public WriteResult deleteByUserIdProductIds(String userId, List<String> productList){
        return datastore.delete(createQuery(userId, productList));
    }

    public UpdateResults checkedOrUncheckedProduct(String userId,String productId, Integer checked){
        return datastore.update(createQuery(userId, productId),createUpdate(checked), false);
    }

    public long selectCartProductCount(String userId){
        return createQuery(userId, (String)null).countAll();
    }

    public List<Cart> selectCartByUserId(String userId){
        return createQuery(userId, (String)null).asList();
    }

    public long selectCartProductCheckedStatusByUserId(String userId){
        return createQuery(userId).countAll();
    }

    private Query<Cart> createQuery(){
        return datastore.createQuery(Cart.class);
    }

    private Query<Cart> createQuery(String userId){
        Query<Cart> query = createQuery(userId, (String)null);
        query.filter("checked", 0);
        return query;
    }


    private Query<Cart> createQuery(String userId, String productId){
        Query<Cart> query = createQuery();
        Optional.ofNullable(userId).ifPresent(id -> query.filter("userId", id));
        Optional.ofNullable(productId).ifPresent(id -> query.filter("productId", id));
        return query;
    }

    private Query<Cart> createQuery(String userId, List<String> productList){
        Query<Cart> query = createQuery();
        return query.filter("userId", userId).filter("productId in", productList);
    }

    private UpdateOperations<Cart> createUpdate(Integer check){
        return datastore.createUpdateOperations(Cart.class).set("checked", check);
    }


}
