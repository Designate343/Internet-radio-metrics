package com.service.api.presenters.programmes;

import com.service.api.BuildCollectionResponse;
import com.service.api.ListRequest;
import com.service.api.CollectionResponse;
import com.service.api.presenters.programmes.get.GetPresenterProgrammes;
import com.service.api.stations.CheckStationExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.service.api.presenters.PresenterResource.PRESENTER_ID;
import static com.service.api.presenters.PresenterResource.PRESENTER_PATH;

@RestController
public class ProgrammesResource {

    public static final String PROGRAMMES = "programmes";
    public static final String PROGRAMME_ID = "programme_id";
    public static final String PRESENTER_PROGRAMMES_PATH = PRESENTER_PATH + "/" + PROGRAMMES;

    @Autowired
    private CheckStationExists checkStationExists;
    @Autowired
    private GetPresenterProgrammes getPresenterProgrammes;
    @Autowired
    private BuildCollectionResponse buildCollectionResponse;

    @GetMapping(PRESENTER_PROGRAMMES_PATH)
    public ResponseEntity<List<Programme>> get(@PathVariable("station_id") int station,
                                               @PathVariable(PRESENTER_ID) UUID presenterId) {
        checkStationExists.checkStationExists(station);

        CollectionResponse<Programme> programmes = getPresenterProgrammes.getProgrammes(presenterId,
                new ListRequest(null, null));

        HttpHeaders httpHeaders = buildCollectionResponse.buildResponseHeaders(programmes);

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(programmes.getCollection());
    }
}
