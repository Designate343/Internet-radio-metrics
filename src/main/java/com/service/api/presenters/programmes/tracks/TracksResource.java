package com.service.api.presenters.programmes.tracks;

import com.service.api.BuildCollectionResponse;
import com.service.api.CollectionResponse;
import com.service.api.ListRequest;
import com.service.api.presenters.programmes.tracks.get.GetTracks;
import com.service.api.stations.CheckStationExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.service.api.presenters.PresenterResource.PRESENTER_ID;
import static com.service.api.presenters.programmes.ProgrammeResource.PROGRAMME_ID;
import static com.service.api.presenters.programmes.ProgrammeResource.PROGRAMME_PATH;

@RestController
public class TracksResource {

    public static final String TRACKS = "tracks";
    public static final String TRACKS_PATH = PROGRAMME_PATH + "/" + TRACKS;

    @Autowired
    private CheckStationExists checkStationExists;
    @Autowired
    private GetTracks getTracks;
    @Autowired
    private BuildCollectionResponse buildCollectionResponse;

    @GetMapping(TRACKS_PATH)
    public ResponseEntity get(@PathVariable("station_id") int stationId,
                              @PathVariable(PRESENTER_ID) UUID presenterId,
                              @PathVariable(PROGRAMME_ID) UUID programmeId) {

        checkStationExists.checkStationExists(stationId);

        CollectionResponse tracks = getTracks.get(presenterId, programmeId, new ListRequest(null, null));

        return ResponseEntity.ok().
                headers(buildCollectionResponse.buildResponseHeaders(tracks))
                .body(tracks.getCollection());
    }

}
