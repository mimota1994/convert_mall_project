package com.mimota.util.common;

import java.io.Serializable;

public class PageInfo<T> implements Serializable {

    int pageNum;
    int pageSize;
    long totalRecords;

    T data;

    public PageInfo(int pageNum, int pageSize, long totalRecords, T data){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        this.data = data;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
