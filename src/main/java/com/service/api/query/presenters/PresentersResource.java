package com.service.api.query.presenters;

import com.service.api.download.dataclasses.Presenter;
import com.service.api.query.presenters.get.GetPresentersOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.service.api.query.BaseResource.BASE_PATH;
import static com.service.api.query.stations.StationsResource.STATION;
import static com.service.api.query.stations.StationsResource.STATION_ID;

@RestController
public class PresentersResource {

    public static final String PRESENTERS = "presenters";

    @Autowired
    private GetPresentersOp getPresentersOp;

    @GetMapping(BASE_PATH + "/" + STATION + "/{" + STATION_ID + "}/" + PRESENTERS)
    public ResponseEntity<List<Presenter>> run(@PathVariable("station_id") int station) {
        List<Presenter> presenters = getPresentersOp.get(station);
        if (presenters == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(getPresentersOp.get(station));
    }

}
