package com.service.api.presenters.programmes.get;

import com.service.api.ListRequest;
import com.service.api.CollectionResponse;
import com.service.api.presenters.programmes.Programme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.service.api.BaseResource.DEFAULT_PAGE_SIZE;
import static java.util.Collections.singletonMap;

@Repository
public class GetPresenterProgrammesDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_PROGRAMME = "SELECT programme_uuid, programme_presenter_uuid, " +
            "   programme_date_broadcast, programme_ref, programme_description";
    private static final String GET_PROGRAMMES_FROM_WHERE = " FROM programmes" +
            " WHERE programme_presenter_uuid = :presenterId";

    public CollectionResponse<Programme> getProgrammes(UUID presenterId, ListRequest listRequest) {
        var params = singletonMap("presenterId", presenterId.toString());

        Integer collectionTotalSize = jdbcTemplate.queryForObject("SELECT COUNT(*) " + GET_PROGRAMMES_FROM_WHERE,
                params,
                Integer.class);
        if (collectionTotalSize == null) {
            collectionTotalSize = -1;
        }

        String query = SELECT_PROGRAMME +
                GET_PROGRAMMES_FROM_WHERE +
                " LIMIT " + listRequest.getLimit() +
                " OFFSET " + listRequest.getOffset();

        List<Programme> programmes = jdbcTemplate.query(query,
                params,
                (rs, i) -> new Programme(
                        rs.getString("programme_ref"),
                        UUID.fromString(rs.getString("programme_uuid")),
                        rs.getString("programme_description"),
                        rs.getTimestamp("programme_date_broadcast").toLocalDateTime(),
                        presenterId)
        );

        return new CollectionResponse<>(programmes,
                DEFAULT_PAGE_SIZE,
                collectionTotalSize / DEFAULT_PAGE_SIZE,
                listRequest.getOffset(),
                listRequest.getLimit(),
                collectionTotalSize);
    }

}