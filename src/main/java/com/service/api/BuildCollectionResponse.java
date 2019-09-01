package com.service.api;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.service.api.CollectionResponse.*;

@Service
public class BuildCollectionResponse {
    public HttpHeaders buildResponseHeaders(CollectionResponse collectionResponse) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(PAGE_SIZE, Integer.toString(collectionResponse.getPageSize()));
        httpHeaders.add(NUMBER_PAGES, Integer.toString(collectionResponse.getNumberPages()));
        httpHeaders.add(START, Integer.toString(collectionResponse.getStart()));
        httpHeaders.add(END, Integer.toString(collectionResponse.getEnd()));
        httpHeaders.add(TOTAL_COLLECTION_SIZE, Integer.toString(collectionResponse.getTotalCollectionSize()));
        return httpHeaders;
    }
}
