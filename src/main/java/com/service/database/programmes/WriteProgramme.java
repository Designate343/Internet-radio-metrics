package com.service.database.programmes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WriteProgramme {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertProgramme(UUID programmeId, UUID presenterId, LocalDateTime date, String description) {
        jdbcTemplate.update("INSERT INTO PROGRAMMES" +
                " (programme_uuid, programme_presenter_uuid, programme_date_broadcast, programme_description)" +
                " VALUES " +
                "(?,?,?,?)", programmeId.toString(), presenterId.toString(), Timestamp.valueOf(date), description);
    }
}
