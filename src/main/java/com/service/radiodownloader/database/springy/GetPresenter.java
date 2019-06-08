package com.service.radiodownloader.database.springy;

import com.service.radiodownloader.dataclasses.Presenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GetPresenter {

    private static final String SELECT_PRESENTER_NAME = "SELECT presenter_name, presenter_uuid, presenter_station_id" +
            " FROM presenters" +
            " WHERE presenter_name = :presenterName" +
            " AND presenter_station_id = :stationId";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Presenter run(String presenterName, int stationId) {
        var params = Map.of("stationId", stationId,
                "presenterName", presenterName);

        List<Presenter> presenters = namedParameterJdbcTemplate.query(SELECT_PRESENTER_NAME,
                params,
                (rs, i) -> new Presenter(rs.getString("presenter_name"),
                        UUID.fromString(rs.getString("presenter_uuid")),
                        rs.getInt("presenter_station_id")));

        return presenters.isEmpty() ? null : presenters.get(0);
    }

}
