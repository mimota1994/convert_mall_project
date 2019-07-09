package com.mimota.dao.table;

import com.google.common.collect.ImmutableMap;
import com.mimota.pojo.User;
import com.mimota.util.MongoMorphiaUtil;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.converters.ObjectIdConverter;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class UserTable {

    private Datastore datastore = null;

    public UserTable() {
        try{
            datastore = MongoMorphiaUtil.getDataStore(User.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public long checkUsername(String username){
        Query<User> query = createQuery(ImmutableMap.of("username", username), null);
        return datastore.getCount(query);
    }

    public User selectLogin(String username, String md5Password){
        Query<User> query = createQuery(ImmutableMap.of("username", username, "password", md5Password), null);
        return query.get();
    }

    public Key<User> insert(User user){
        return datastore.save(user);
    }

    public long checkEmail(String email){
        Query<User> query = createQuery(ImmutableMap.of("email", email), null);
        return datastore.getCount(query);
    }

    public String selectQuestionByUsername(String username){
        Query<User> query = createQuery(ImmutableMap.of("username", username), null);
        User user = query.get();
        return user.getQuestion();
    }

    public long checkAnswer(String username, String question, String answer){
        Query<User> query = createQuery(ImmutableMap.of("username", username, "question", question, "answer", answer), null);
        return datastore.getCount(query);
    }

    public int updatePasswordByUsername(String username, String password){
        UpdateOperations<User> ops = createUpdate(ImmutableMap.of("password", password));
        Query<User> query = createQuery(ImmutableMap.of("username", username), null);
        UpdateResults result =  datastore.update(query, ops, false);
        return result.getUpdatedCount();
    }

    public long checkPassword(String password, String userId){
        Query<User> query = createQuery(ImmutableMap.of("password", password), userId);
        return datastore.getCount(query);
    }

    public int updateByPrimaryKeySelective(User user, Map<String, String> conditions){
        UpdateOperations<User> ops = createUpdate(conditions);
        UpdateResults result = datastore.update(createQuery(null, user.getId()), ops);
        return result.getUpdatedCount();
    }

    public User selectByPrimaryKey(String userId){
        Query<User> query = createQuery(null, userId);
        return query.get();
    }

    public long checkEmailByUserId(String email, String userId){
        Query<User> query = createQuery(ImmutableMap.of("email", email), userId);
        return datastore.getCount(query);
    }

    public Query<User> createQuery() {
        return datastore.createQuery(User.class);
    }


//    public Query<User> createQuery(String condition, String str) {
//        Query<User> query = createQuery();
//        query.filter(condition, str);
//        return query;
//    }
//
//    public Query<User> createQuery(String username, String md5Password, boolean isLongin) {
//        Query<User> query = createQuery();
//        query.filter("username", username).filter("password", md5Password);
//        return query;
//    }
//
//    public Query<User> createQuery(boolean is, String userId, String md5Password) {
//        Query<User> query = createQuery();
//        query.filter("_id", new ObjectId(userId)).filter("password", md5Password);
//        return query;
//    }
//
//    public Query<User> createQuery(String username, String question, String answer) {
//        Query<User> query = createQuery();
//        query.filter("username", username).filter("question", question).filter("answer", answer);
//        return query;
//    }
//
//    public Query<User> createQuery(ObjectId objectid) {
//        Query<User> query = createQuery();
//        query.filter("_id", objectid);
//        return query;
//    }
//
//    public Query<User> createQuery(String email, int userId) {
//        Query<User> query = createQuery();
//        query.filter("email", email).filter("id", userId);
//        return query;
//    }

//    public Query<User> createQuery(Map<String, String> filters){
//        Query<User> query = createQuery();
//        filters.forEach((condition, str) -> query.filter(condition, str));
//        return query;
//    }

    public Query<User> createQuery(Map<String, String> conditions, String userId){
        Query<User> query = createQuery();
        if(conditions != null){
            conditions.forEach((condition, str) -> query.filter(condition, str));
        }

        Optional.ofNullable(userId).ifPresent(id -> query.filter("_id", new ObjectId(id)));
        return query;
    }

    private UpdateOperations<User> createUpdate(Map<String, String> conditions) {
        UpdateOperations<User> ops = datastore.createUpdateOperations(User.class);
        conditions.forEach((condition, str) -> ops.set(condition, str));
        return ops;
    }
}
