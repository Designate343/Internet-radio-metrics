package com.service.api.stations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CheckStationExists {

    public Stations checkStationExists(int stationId) {
        var station = Stations.getFromId(stationId);
        if (station == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The station is not configured");
        }
        return station;
    }
}