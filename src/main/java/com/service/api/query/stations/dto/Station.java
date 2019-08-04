package com.service.api.query.stations.dto;

public class Station {

    private final String name;
    private final int id;

    public Station(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
