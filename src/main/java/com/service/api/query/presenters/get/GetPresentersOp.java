package com.service.api.query.presenters.get;

import com.service.api.download.dataclasses.Presenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPresentersOp {

    @Autowired
    private GetPresentersDao getPresentersDao;

    public List<Presenter> get(int stationId) {
        return getPresentersDao.getPresenters(stationId);
    }
}
