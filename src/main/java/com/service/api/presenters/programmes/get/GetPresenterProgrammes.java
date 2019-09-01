package com.service.api.presenters.programmes.get;

import com.service.api.ListRequest;
import com.service.api.CollectionResponse;
import com.service.api.presenters.programmes.Programme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetPresenterProgrammes {

    @Autowired
    private GetPresenterProgrammesDao getPresenterProgrammesDao;

    public CollectionResponse<Programme> getProgrammes(UUID presenterId, ListRequest listRequest) {
        //todo: check presenter exists
        return getPresenterProgrammesDao.getProgrammes(presenterId, listRequest);
    }

}