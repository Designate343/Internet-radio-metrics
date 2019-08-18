package com.service.api.query.presenters.programmes.get;

import java.util.List;
import java.util.UUID;

import com.service.api.query.presenters.programmes.Programme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetPresenterProgrammes {

    @Autowired
    private GetPresenterProgrammesDao getPresenterProgrammesDao;

    public List<Programme> getProgrammes(UUID presenterId) {
        //todo: check presenter exists
        return getPresenterProgrammesDao.getProgrammes(presenterId);
    }

}