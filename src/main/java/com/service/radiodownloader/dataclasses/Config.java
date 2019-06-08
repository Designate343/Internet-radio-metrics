package com.service.radiodownloader.dataclasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Config {

    private LocalDate date_since;
    private String db_type;
    private String stationName;

    public LocalDate getDate_since() {
        return date_since;
    }

    public String getDb_type() {
        return db_type;
    }

    public String getStationName() {
        return stationName;
    }

    public static Config getFromConfigFile(String pathToFile) {
        var fileAsString = new StringBuilder();
        try (Stream<String> lines = Files.lines(Paths.get(pathToFile))) {
            lines.forEachOrdered(fileAsString::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var jsConfigObj = new JSONObject(fileAsString.toString());
        var config = new Config();
        try {
            if (jsConfigObj.getString("date_since").equals("latest")) {
                config.date_since = null;
            } else {
                config.date_since = LocalDate.parse(jsConfigObj.getString("date_since"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        } catch (JSONException e) {
            Logger.getLogger(Config.class.getName()).log(Level.FINE, e.getMessage());
        }
        config.db_type = jsConfigObj.getString("db_type");
        config.stationName = jsConfigObj.getString("station");
        return config;
    }

}
