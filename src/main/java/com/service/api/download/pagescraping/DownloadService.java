package com.service.api.download.pagescraping;

import com.service.api.download.requests.DownloadRequest;
import com.service.api.download.requests.Request;

import java.util.UUID;

public interface DownloadService {

    void logDownloadStart(UUID tag, Request request);

    void downloadProgrammesAndWriteToDatabase(String stationName, DownloadRequest downloadRequest, UUID etag);

    void logDownloadEnd(UUID tag);
}
