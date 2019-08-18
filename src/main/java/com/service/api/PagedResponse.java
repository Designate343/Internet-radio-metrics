package com.service.api;

import java.util.List;

public class PagedResponse<T> {

    private final List<T> collection;
    private final int pageSize;
    private final int numberPages;
    private final int start;
    private final int end;
    private final int totalCollectionSize;

    public PagedResponse(List<T> collection, int pageSize, int numberPages, int start, int end, int totalCollectionSize) {
        this.collection = collection;
        this.pageSize = pageSize;
        this.numberPages = numberPages;
        this.start = start;
        this.end = end;
        this.totalCollectionSize = totalCollectionSize;
    }

    public List<T> getCollection() {
        return collection;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getTotalCollectionSize() {
        return totalCollectionSize;
    }
}
