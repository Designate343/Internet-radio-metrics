package com.service.api.query.presenters;

import com.service.database.dataclasses.Presenter;
import com.service.api.stations.CheckStationExists;
import com.service.api.query.presenters.get.GetPresentersOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.service.api.query.BaseResource.BASE_PATH;
import static com.service.api.query.BaseResource.STATION;
import static com.service.api.query.BaseResource.STATION_ID;

@RestController
public class PresentersResource {

    public static final String PRESENTERS = "presenters";
    public static final String PRESENTERS_PATH = BASE_PATH + "/" + STATION + "/{" + STATION_ID + "}/" + PRESENTERS;

    @Autowired
    private GetPresentersOp getPresentersOp;
    @Autowired
    private CheckStationExists checkStationExists;

    @GetMapping(PRESENTERS_PATH)
    public ResponseEntity<List<Presenter>> run(@PathVariable("station_id") int station) {
        var stationData = checkStationExists.checkStationExists(station);

        List<Presenter> presenters = getPresentersOp.get(stationData.getStationId());
        if (presenters == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(presenters);
    }

}
