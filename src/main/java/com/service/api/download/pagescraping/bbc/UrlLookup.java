package com.service.api.download.pagescraping.bbc;

import java.util.Map;

public class UrlLookup {

    private static final Map<String, String> stationUrlMapping = Map.of(
            "bbc6Music", "https://www.bbc.co.uk/schedules/p00fzl65/"
    );

    public static String getUrl(String stationName) {
        return stationUrlMapping.getOrDefault(stationName, "");
    }
}
