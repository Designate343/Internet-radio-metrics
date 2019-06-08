package com.service.radiodownloader.pagescraping.bbc;

import com.service.radiodownloader.dataclasses.Track;
import com.service.radiodownloader.date.DateUtilities;
import com.service.radiodownloader.dataclasses.ProgrammeData;
import com.service.radiodownloader.pagescraping.ScrapingTools;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ParseBBCSchedulePage {

    private static final String css_class_selector = "list-unstyled g-c-l";
    private static final String css_class_programme_page = "br-blocklink__link block-link__target";

    public List<ProgrammeData> getAllShows(Document wholeDocument, LocalDate date) {
        Element relevantPageSegment = wholeDocument.getElementsByClass(css_class_selector).first();
        int day = date.getDayOfMonth();

        ExecutorService service = Executors.newFixedThreadPool(4);
        List<Future<ProgrammeData>> futureList = new ArrayList<>();

        for (Element eachProgramme : relevantPageSegment.getElementsByClass("grid-wrapper")) {
            String dateTimeRaw = eachProgramme.getElementsByClass("broadcast__time gamma").attr("content");
            LocalDateTime dateTime = DateUtilities.rawBBCDateStringToLocalDateTime(dateTimeRaw.substring(0, dateTimeRaw.indexOf("+") - 3));
            Elements programme = eachProgramme.getElementsByClass(css_class_programme_page);
            String programmePageHref = programme.attr("href");
            if (dateTime.getDayOfMonth() == day) {
                Future<ProgrammeData> apiResponse = service
                        .submit(() -> {
                            Document doc = ScrapingTools.getDocument(programmePageHref);
                            String presenter = ParseBBCDJPage.getPresenter(doc);
                            String description = ParseBBCDJPage.getDescription(doc);
                            List<Track> allTracksOnProgramme = ParseBBCDJPage.getAllTracks(doc);
                            return new ProgrammeData(presenter.replace("\'", "&ap"), dateTime, description, allTracksOnProgramme);
                        });
                futureList.add(apiResponse);
            }
        }
        service.shutdown();
        return collectResults(futureList);
    }

    private List<ProgrammeData> collectResults(List<Future<ProgrammeData>> futureList) {
        List<ProgrammeData> results = new ArrayList<>(futureList.size());
        for (var ofTrackListing : futureList) {
            try {
                results.add(ofTrackListing.get());
            } catch (InterruptedException | ExecutionException e) {
                Logger.getLogger(ParseBBCSchedulePage.class.getName()).log(Level.FINE, e.getMessage(), e);
            }
        }
        return results;
    }

}
