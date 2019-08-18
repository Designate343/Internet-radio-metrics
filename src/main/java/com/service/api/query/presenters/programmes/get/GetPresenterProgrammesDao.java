package com.service.api.query.presenters.programmes.get;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.service.api.query.presenters.programmes.Programme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GetPresenterProgrammesDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_PROGRAMMES_BASE_QUERY = "SELECT programme_uuid, programme_presenter_uuid, programme_date_broadcast,"
            + "       programme_ref, programme_description" + " FROM programmes"
            + " WHERE programme_presenter_uuid = :presenterId"
            + " LIMIT 25";

    public List<Programme> getProgrammes(UUID presenterId) {
        //TODO: paging, default limit
        List<Programme> programmes = jdbcTemplate.query(GET_PROGRAMMES_BASE_QUERY,
                Collections.singletonMap("presenterId", presenterId.toString()),
                (rs, i) -> new Programme(rs.getString("programme_ref"), UUID.fromString(rs.getString("programme_uuid")),
                        rs.getString("programme_description"),
                        rs.getTimestamp("programme_date_broadcast").toLocalDateTime(), presenterId));

        return programmes;
    }

}