package com.service.radiodownloader.pagescraping;

import com.service.radiodownloader.requests.DownloadRequest;
import com.service.radiodownloader.requests.Request;

import java.util.UUID;

public interface DownloadService {

    void logDownloadStart(UUID tag, Request request);

    void downloadProgrammesAndWriteToDatabase(String stationName, DownloadRequest downloadRequest, UUID etag);

    void logDownloadEnd(UUID tag);
}
