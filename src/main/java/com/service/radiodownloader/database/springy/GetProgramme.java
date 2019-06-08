package com.service.radiodownloader.database.springy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GetProgramme {

    private static final String SELECT_PROGRAMME = "SELECT programme_uuid, programme_presenter_uuid, " +
            " programme_date_broadcast, programme_description" +
            " FROM programmes" +
            " JOIN presenters ON presenter_uuid = programme_presenter_uuid" +
            " WHERE presenter_name = :presenterName" +
            " AND programme_date_broadcast = :dateBroadcast";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Programme run(LocalDateTime date, String presenterName) {
        var params = Map.of("presenterName", presenterName,
                "dateBroadcast", Timestamp.valueOf(date));

        List<Programme> programmes = namedParameterJdbcTemplate.query(SELECT_PROGRAMME, params, (rs, i) ->
                new Programme(
                        UUID.fromString(rs.getString("programme_uuid")),
                        rs.getString("programme_presenter_uuid"),
                        rs.getTimestamp("programme_date_broadcast").toLocalDateTime(),
                        rs.getString("programme_description")
                ));
        return programmes.isEmpty() ? null : programmes.get(0);
    }

    public class Programme {
        private final UUID programmeId;
        private final String presenterId;
        private final LocalDateTime dateBroadcast;
        private final String description;

        public Programme(UUID programmeId, String presenterId, LocalDateTime dateBroadcast, String description) {
            this.programmeId = programmeId;
            this.presenterId = presenterId;
            this.dateBroadcast = dateBroadcast;
            this.description = description;
        }

        public UUID getProgrammeId() {
            return programmeId;
        }

        public String getPresenterId() {
            return presenterId;
        }

        public LocalDateTime getDateBroadcast() {
            return dateBroadcast;
        }

        public String getDescription() {
            return description;
        }
    }

}
