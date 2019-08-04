package com.service.api.download.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.Collections.EMPTY_LIST;

@JsonComponent
public class DownloadRequest implements Request {
    private List<String> presenters;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDownload;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDownload;

    @Override
    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> asMap = Map.of(
                "Presenters", presenters == null ? EMPTY_LIST : presenters,
                "date_download_started", startDownload.format(ISO_DATE),
                "Date to end download range", endDownload == null ?
                        LocalDate.now() : endDownload.format(ISO_DATE)
        );

        try {
            return objectMapper.writeValueAsString(asMap);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Invalid request object");
        }

    }

    public List<String> getPresenters() {
        return presenters;
    }

    public void setPresenters(List<String> presenters) {
        this.presenters = presenters;
    }

    public LocalDate getStartDownload() {
        return startDownload;
    }

    public void setStartDownload(LocalDate startDownload) {
        this.startDownload = startDownload;
    }

    public LocalDate getEndDownload() {
        return endDownload;
    }

    public void setEndDownload(LocalDate endDownload) {
        this.endDownload = endDownload;
    }
}
