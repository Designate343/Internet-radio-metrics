package com.service.radiodownloader.dataclasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.time.LocalDate;
import java.util.List;

@JsonComponent
public class DownloadRequest {
    private List<String> presenters;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDownload;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDownload;

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
