package com.service.database.log;

import com.service.api.download.requests.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Collections.singletonMap;

@Repository
public class LogDownload {

    private static final String INSERT_DOWNLOAD_EVENT = "INSERT INTO download_log" +
            " (download_tag, download_date_started, download_request)" +
            " VALUES" +
            " (:tag, :downloadStart, :request)";

    private static final String DOWNLOAD_ENDED = "UPDATE download_log" +
            " SET download_end = :downloadEnd," +
            "     download_finished = :state" +
            " WHERE download_tag = :tag";

    private static final String DOWNLOAD_IN_PROGRESS = "SELECT EXISTS (" +
            " SELECT 1" +
            " FROM download_log" +
            " WHERE download_tag = :tag" +
            " AND download_finished = 'f'" +
            ")";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void logDownloadStart(UUID tag, Request request) {
        var paramaters = Map.of(
                "tag", tag.toString(),
                "downloadStart", Timestamp.valueOf(LocalDateTime.now()),
                "request", request.toJsonString()
        );

        namedParameterJdbcTemplate.update(INSERT_DOWNLOAD_EVENT, paramaters);
    }

    public void logDownloadEnd(UUID tag) {
        var paramaters = Map.of(
                "tag", tag.toString(),
                "downloadEnd", Timestamp.valueOf(LocalDateTime.now()),
                "download_finished", "TRUE"
        );
        namedParameterJdbcTemplate.update(DOWNLOAD_ENDED, paramaters);
    }

    public boolean downloadInProgress(UUID tag) {
        List<Boolean> result = namedParameterJdbcTemplate
                .query(DOWNLOAD_IN_PROGRESS,
                        singletonMap("tag", tag.toString()),
                        (rs, i) -> "t".equals(rs.getString(1)));

        return result.isEmpty() ? false : result.get(0);
    }
}
