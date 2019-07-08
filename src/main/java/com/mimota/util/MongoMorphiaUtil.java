package com.mimota.util;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.MapperOptions;

import java.util.Set;

/**
 * Created by yi.dai on 2018/6/6.
 */
public class MongoMorphiaUtil
{
    private static Datastore dataStore;

    // 获取 datastore
    public static Datastore getDataStore(Set<Class<?>> classNames)
    {
        return getDataStore(MongoUtil.dbName, classNames);
    }

    public static Datastore getDataStore(Class<?> className)
    {
        return getDataStore(MongoUtil.dbName, className);
    }

    public static Datastore getDataStore(String dbName, Set<Class<?>> classNames)
    {
        MapperOptions mapperOptions = new MapperOptions();
        mapperOptions.setStoreEmpties(true);
        mapperOptions.setStoreNulls(true);
        Morphia morphia = new Morphia(new Mapper(mapperOptions));

        morphia.map(classNames.toArray(new Class[] {}));
        dataStore = morphia.createDatastore(MongoUtil.getMongoClient(), dbName);
        dataStore.ensureIndexes();

        return dataStore;
    }

    public static Datastore getDataStore(String dbName, Class<?> classNames)
    {
        MapperOptions mapperOptions = new MapperOptions();
        mapperOptions.setStoreEmpties(true);
        mapperOptions.setStoreNulls(true);
        Morphia morphia = new Morphia(new Mapper(mapperOptions));

        morphia.map(classNames);

        dataStore = morphia.createDatastore(MongoUtil.getMongoClient(), dbName);
        dataStore.ensureIndexes();

        return dataStore;
    }
}
