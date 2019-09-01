package com.service.api.presenters.programmes.tracks.get;

import com.service.api.CollectionResponse;
import com.service.api.ListRequest;
import com.service.api.presenters.programmes.tracks.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetTracks {

    @Autowired
    private GetTracksDao getTracksDao;

    public CollectionResponse<Track> get(UUID presenterId, UUID programmeId, ListRequest listRequest) {
        //todo: check presenter and programme both exist
        return getTracksDao.get(presenterId, programmeId, listRequest);
    }

}
