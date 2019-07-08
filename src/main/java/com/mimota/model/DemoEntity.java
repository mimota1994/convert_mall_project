package com.mimota.model;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

@Entity(value = "demo", noClassnameStored = true)
public class DemoEntity implements Serializable {

    @Id
    private String id;

    private String title;

    private String description;

    private String by;

    private String url;

//    public DemoEntity(){
//        System.out.println();
//
//    }
//
//    public DemoEntity(String id, String title, String description, String by, String url) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.by = by;
//        this.url = url;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
