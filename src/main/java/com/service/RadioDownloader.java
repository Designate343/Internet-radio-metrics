package com.service;

import com.service.radiodownloader.database.log.LogDownload;
import com.service.radiodownloader.requests.DownloadRequest;
import com.service.radiodownloader.pagescraping.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class RadioDownloader {

    private static final String DOWNLOAD_REQUESTED = "x-download-requested";

    @Autowired
    private DownloadService downloadService;
    @Autowired
    private BackgroundThreadRunner backgroundThreadRunner;
    @Autowired
    private LogDownload logDownload;

    @PostMapping("/{station}/downloads")
    public ResponseEntity downloadFromDates(@PathVariable("station") String station,
                                            @RequestBody DownloadRequest downloadRequest,
                                            @RequestHeader(DOWNLOAD_REQUESTED) @Nullable String downloadRequested) {
        if (downloadRequest.getStartDownload() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (downloadRequested != null) {
            UUID tag = UUID.fromString(downloadRequested);

            if (logDownload.downloadInProgress(tag)) {
                return ResponseEntity
                        .noContent()
                        .build();
            }
        }

        final var etag = UUID.randomUUID();

        backgroundThreadRunner
                .process(() -> downloadService.downloadProgrammesAndWriteToDatabase(station, downloadRequest, etag));

        return ResponseEntity
                .ok()
                .eTag(etag.toString())
                .build();
    }

}
