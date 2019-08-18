package com.service.api.presenters;

import java.util.UUID;

public class Presenter {
    private final String presenterName;
    private final UUID presenterUuid;
    private final int stationId;

    public Presenter(String presenterName, UUID presenterUuid, int stationId) {
        this.presenterName = presenterName;
        this.presenterUuid = presenterUuid;
        this.stationId = stationId;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public UUID getPresenterUuid() {
        return presenterUuid;
    }

    public int getStationId() {
        return stationId;
    }
}
