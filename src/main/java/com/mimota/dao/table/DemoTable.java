package com.mimota.dao.table;

import com.mimota.model.DemoEntity;
import com.mimota.util.MongoMorphiaUtil;

import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public final class DemoTable {

    private Datastore datastore;

    public DemoTable() {
        try {
            datastore = MongoMorphiaUtil.getDataStore(DemoEntity.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public boolean store(DemoEntity demoEntity) {
        return datastore.save(demoEntity) != null;
    }

    public DemoEntity getById(String id){
        Query<DemoEntity> query = createQuery();
        query.filter("_id", id);
        return query.get();
    }

    public boolean update(DemoEntity demoEntity){
        UpdateResults results = datastore.update(demoEntity, null);

        return results.getUpdatedExisting();
    }

    public boolean deleteById(String id) {
        Query<DemoEntity> query = createQuery().filter("_id", id);
        WriteResult result = datastore.delete(query);

        return result.isUpdateOfExisting();
    }

    public void batchDelete(Query<DemoEntity> query){
        datastore.delete(query);
    }

    public List<DemoEntity> batchSearch(Query<DemoEntity> query){
        return query.asList();
    }

    public Query<DemoEntity> createQuery() {
        return datastore.createQuery(DemoEntity.class);
    }

    private UpdateOperations<DemoEntity> createUpdate() {
        UpdateOperations<DemoEntity> ops = datastore.createUpdateOperations(DemoEntity.class);
        ops.set("audit_info.update_time", System.currentTimeMillis());
        return ops;
    }

}
