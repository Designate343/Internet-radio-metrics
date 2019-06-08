package com.service.radiodownloader.pagescraping;

import com.service.radiodownloader.dataclasses.Config;

public interface DownloadService {

    void downloadProgrammesAndWriteToDatabase(Config config);

}
