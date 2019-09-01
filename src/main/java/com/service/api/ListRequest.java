package com.service.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.service.api.BaseResource.DEFAULT_PAGE_SIZE;

public class ListRequest {

    private final Integer offset;
    private final Integer limit;

    public ListRequest(Integer offset, Integer limit) {
        this.offset = offset == null ? 0 : offset;
        this.limit = limit == null ? DEFAULT_PAGE_SIZE : limit;
        if (this.offset > this.limit) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "limit cannot exceed offset");
        }
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }
}
