package com.service.api.query.presenters.programmes;

import com.service.api.query.presenters.programmes.get.GetPresenterProgrammes;
import com.service.api.stations.CheckStationExists;
import com.service.api.stations.Stations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.service.api.query.presenters.PresenterResource.PRESENTER_ID;
import static com.service.api.query.presenters.PresenterResource.PRESENTER_PATH;

import java.util.List;
import java.util.UUID;

@RestController
public class ProgrammesResource {

    public static final String PROGRAMMES = "programmes";
    public static final String PROGRAMME_ID = "programme_id";
    public static final String PRESENTER_PROGRAMMES_PATH = PRESENTER_PATH + "/" + PROGRAMMES;

    @Autowired
    private CheckStationExists checkStationExists;
    @Autowired
    private GetPresenterProgrammes getPresenterProgrammes;

    @GetMapping(PRESENTER_PROGRAMMES_PATH)
    public ResponseEntity<List<Programme>> get(@PathVariable("station_id") int station,
                                               @PathVariable(PRESENTER_ID) UUID presenterId) {
        checkStationExists.checkStationExists(station);

        return ResponseEntity.ok(getPresenterProgrammes.getProgrammes(presenterId));
    }
}
