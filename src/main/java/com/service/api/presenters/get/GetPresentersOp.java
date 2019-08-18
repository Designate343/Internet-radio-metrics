package com.service.api.presenters.get;

import com.service.api.presenters.Presenter;
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
