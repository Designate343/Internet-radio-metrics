package com.service.radiodownloader.database.springy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class WritePresenter {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID insertPresenterIfUnique(String presenter, int stationId) {
        final UUID presenterId = UUID.randomUUID();

        namedParameterJdbcTemplate.getJdbcTemplate().update(
                "INSERT INTO PRESENTERS " +
                        "(presenter_name, presenter_uuid, presenter_station_id) " +
                        "VALUES " +
                        "(?, ?, ?)", presenter, presenterId.toString(), stationId);

        return presenterId;
    }

}
