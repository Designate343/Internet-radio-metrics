package com.service.api.presenters.get;

import com.service.api.presenters.Presenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class GetPresentersDao {

    private static final String SELECT_PRESENTER_NAME = "SELECT presenter_name, presenter_uuid, presenter_station_id" +
            " FROM presenters" +
            " WHERE presenter_station_id = :stationId";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Presenter> getPresenters(int stationId) {
        var params = Map.of("stationId", stationId);

        List<Presenter> presenters = namedParameterJdbcTemplate.query(SELECT_PRESENTER_NAME,
                params,
                (rs, i) -> new Presenter(rs.getString("presenter_name"),
                        UUID.fromString(rs.getString("presenter_uuid")),
                        rs.getInt("presenter_station_id")));

        return presenters.isEmpty() ? null : presenters;
    }

}
