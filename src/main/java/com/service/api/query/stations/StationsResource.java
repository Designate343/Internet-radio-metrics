package com.service.api.query.stations;

import com.service.api.query.stations.dto.Station;
import com.service.api.query.stations.get.GetStationsOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.service.api.query.BaseResource.BASE_PATH;

@RestController
public class StationsResource {

    public static final String STATION = "station";
    public static final String STATIONS = "stations";
    public static final String STATION_ID = "station_id";

    @Autowired
    private GetStationsOp getStationsOp;

    @GetMapping(BASE_PATH + "/" + STATIONS)
    public ResponseEntity<List<Station>> get() {
        List<Station> stations = getStationsOp.getStations();
        if (stations == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(stations);
    }

}
