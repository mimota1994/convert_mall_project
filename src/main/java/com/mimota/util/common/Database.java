package com.mimota.util.common;

public enum Database {

    LOCAL_MONGO_URL("mongodb://localhost:27017"),
    Aliyun_MONGO_URL("mongodb://101.132.75.94:27017");

    private String url;

    Database(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
