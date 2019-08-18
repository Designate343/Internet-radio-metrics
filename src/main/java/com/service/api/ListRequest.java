package com.service.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ListRequest {

    private final Integer offset;
    private final Integer limit;

    public ListRequest(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
        if (offset != null && limit != null) {
            if (offset > limit) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "limit cannot exceed offset");
            }
        }
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }
}
