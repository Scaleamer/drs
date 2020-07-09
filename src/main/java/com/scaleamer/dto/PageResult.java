package com.scaleamer.dto;

import java.util.List;

public class PageResult<E> {
    private List<E> list;
    private long total;
    private int pageNum;
    private int pages;
    private int pageSize;

    public void setList(List<E> list) {
        this.list = list;
    }

    public List<E> getList() {
        return list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
