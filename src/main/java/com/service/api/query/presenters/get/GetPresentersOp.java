package com.service.api.query.presenters.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.database.dataclasses.Presenter;

import java.util.List;

@Service
public class GetPresentersOp {

    @Autowired
    private GetPresentersDao getPresentersDao;

    public List<Presenter> get(int stationId) {
        return getPresentersDao.getPresenters(stationId);
    }
}
