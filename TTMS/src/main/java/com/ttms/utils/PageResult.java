package com.ttms.utils;

import java.util.List;

public class PageResult<T> {
    private long total;
    private long totalPage;
    private List<T> items;

    public PageResult() {
    }

    public PageResult(long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(long total, long totalPage, List<T> itmes) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
