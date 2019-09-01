package com.service.api.presenters.programmes.tracks.get;

import com.service.api.CollectionResponse;
import com.service.api.ListRequest;
import com.service.api.presenters.programmes.tracks.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.service.api.BaseResource.DEFAULT_PAGE_SIZE;

@Repository
public class GetTracksDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_TRACKS = "SELECT track_uuid, track_name, track_artist" +
            " FROM tracks" +
            " WHERE track_programme_origin_uuid = :programmeId" +
            " AND track_presenter_uuid = :presenterId";

    public CollectionResponse<Track> get(UUID presenterId, UUID programmeId, ListRequest listRequest) {
        int limit = listRequest.getLimit() == null ? DEFAULT_PAGE_SIZE : listRequest.getLimit();
        int offset = listRequest.getOffset() == null ? 0 : listRequest.getOffset();

        String query = GET_TRACKS +
                " LIMIT " + limit +
                " OFFSET " + offset;

        List<Track> tracks = jdbcTemplate.query(query,
                Map.of(
                        "programmeId", programmeId.toString(),
                        "presenterId", presenterId.toString()
                ),
                (rs, i) -> new Track(UUID.fromString(rs.getString("track_uuid")),
                        rs.getString("track_artist"),
                        rs.getString("track_name")));

        int collectionSize = tracks.size();
        return new CollectionResponse<>(tracks,
                DEFAULT_PAGE_SIZE,
                collectionSize / DEFAULT_PAGE_SIZE,
                limit,
                offset,
                collectionSize);
    }

}
