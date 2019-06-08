package com.service.radiodownloader.pagescraping.bbc;

import com.service.radiodownloader.database.springy.GetDateToStartDownloadFrom;
import com.service.radiodownloader.database.springy.StationDao;
import com.service.radiodownloader.database.springy.WriteProgrammeData;
import com.service.radiodownloader.dataclasses.Config;
import com.service.radiodownloader.dataclasses.ProgrammeData;
import com.service.radiodownloader.pagescraping.DownloadService;
import com.service.radiodownloader.stations.UrlLookup;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DownloadBBCTrackListings implements DownloadService {

    @Autowired
    private ParseBBCSchedulePage parseBBCSchedulePage;
    @Autowired
    private WriteProgrammeData writeProgrammeData;
    @Autowired
    private StationDao stationDao;
    @Autowired
    private GetDateToStartDownloadFrom getDateToStartDownloadFrom;

    @Override
    public void downloadProgrammesAndWriteToDatabase(Config config) {
        LocalDate start = getDateToStartDownloadFrom.run(config.getDate_since());
        LocalDate end = LocalDate.now();

        var baseUrl = UrlLookup.getUrl(config.getStationName());
        int stationId = stationDao.writeStationToDatabaseOrRetrieveExistingId(config.getStationName());

        start.datesUntil(end).forEach(date -> {
            Set<ProgrammeData> programmesOnDay = getProgrammesOnDay(baseUrl, date);
            for (ProgrammeData programmeData : programmesOnDay) {
                programmeData.setStationId(stationId);
                writeProgrammeData.run(programmeData);
            }
        });
    }

    private Set<ProgrammeData> getProgrammesOnDay(final String baseUrl, LocalDate date) {
        Set<ProgrammeData> programmes = new HashSet<>();
        System.out.println("downloading from date " + date.toString());
        String url = baseUrl + date.getYear() + "/" + String.format("%02d", date.getMonthValue()) + "/"
                + String.format("%02d", date.getDayOfMonth());
        try {
            Connection conn = Jsoup.connect(url);
            Document wholeDocument = conn.get();
            programmes.addAll(parseBBCSchedulePage.getAllShows(wholeDocument, date));
        } catch (IOException e) {
            Logger.getLogger(DownloadBBCTrackListings.class.getName()).log(Level.WARNING,
                    "Unable to retrieve shows on date " + date.toString() + "\n" + e.getMessage());
        }
        return programmes;
    }

}
