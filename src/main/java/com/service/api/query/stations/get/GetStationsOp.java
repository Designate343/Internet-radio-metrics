package com.service.api.query.stations.get;

import com.service.api.query.stations.dto.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetStationsOp {

    @Autowired
    private GetStationsDao getStationsDao;

    public List<Station> getStations() {
        return getStationsDao.get();
    }

}
