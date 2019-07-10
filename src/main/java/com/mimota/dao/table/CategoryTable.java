package com.mimota.dao.table;

import com.google.common.collect.ImmutableMap;
import com.mimota.pojo.Category;
import com.mimota.util.MongoMorphiaUtil;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryTable {

    private Datastore datastore;

    public CategoryTable(){
        datastore = MongoMorphiaUtil.getDataStore(Category.class);
    }

    public Key<Category> insert(Category category){
        Key<Category> key = datastore.save(category);
        return key;
    }

    public int updateByPrimaryKeySelective(String categoryId, String categoryName){
        UpdateResults result = datastore.update(createQuery(categoryId), createUpdate(ImmutableMap.of("name", categoryName)), false);
        return result.getUpdatedCount();
    }

    public List<Category> selectCategoryChildrenByParentId(String categoryId){
        return createQuery(categoryId, true).asList();
    }

    public Category selectByPrimaryKey(String categoryId){
        return createQuery(categoryId).get();
    }

    public UpdateOperations<Category> createUpdate(Map<String, String> conditions){
        UpdateOperations<Category> ops = datastore.createUpdateOperations(Category.class);
        conditions.forEach((condition, str) -> ops.set(condition, str));
        return ops;
    }

    public Query<Category> createQuery(){
        return datastore.createQuery(Category.class);
    }

    public Query<Category> createQuery(String categoryId){
        Query<Category> query = createQuery();
        if(categoryId.length() != 24){
            query.filter("_id", categoryId);
        }else{
            query.filter("_id", new ObjectId(categoryId));
        }
        return query;
    }

    public Query<Category> createQuery(String parentId, boolean isParent){
        Query<Category> query = createQuery();
        query.filter("parentId", parentId);
        return query;
    }
}
