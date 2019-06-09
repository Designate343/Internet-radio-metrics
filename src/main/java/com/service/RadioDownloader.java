package com.service;

import com.service.radiodownloader.dataclasses.DownloadRequest;
import com.service.radiodownloader.pagescraping.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RadioDownloader {

    @Autowired
    private DownloadService downloadService;
    @Autowired
    private BackgroundThreadRunner backgroundThreadRunner;

    @PostMapping("/{station}/download")
    public ResponseEntity downloadFromDates(@PathVariable("station") String station,
                                            @RequestBody DownloadRequest downloadRequest) {
        if (downloadRequest.getStartDownload() == null) {
            return ResponseEntity.badRequest().build();
        }

        backgroundThreadRunner.process(() -> downloadService.downloadProgrammesAndWriteToDatabase(station, downloadRequest));
        return ResponseEntity.ok().build();
    }

}
