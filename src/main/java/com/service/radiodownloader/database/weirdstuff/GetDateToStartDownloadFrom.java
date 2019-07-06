package com.service.radiodownloader.database.weirdstuff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class GetDateToStartDownloadFrom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LocalDate run(LocalDate configStart) {
        if (configStart == null) {
            String query = "SELECT MAX(programme_date_broadcast) AS date FROM programmes LIMIT 1";
            List<Timestamp> date = jdbcTemplate.query(query, (resultSet, i) -> resultSet.getTimestamp("date"));

            return date.isEmpty() ? LocalDate.now().minusMonths(3) :
                    LocalDate.ofInstant(date.get(0).toInstant(), ZoneId.systemDefault());
        }

        return configStart;
    }

}
