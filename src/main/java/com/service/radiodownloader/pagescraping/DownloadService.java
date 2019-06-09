package com.service.radiodownloader.pagescraping;

import com.service.radiodownloader.dataclasses.DownloadRequest;

public interface DownloadService {

    void downloadProgrammesAndWriteToDatabase(String stationName, DownloadRequest downloadRequest);

}
