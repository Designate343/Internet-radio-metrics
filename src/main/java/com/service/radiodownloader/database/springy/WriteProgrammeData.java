package com.service.radiodownloader.database.springy;

import com.service.radiodownloader.dataclasses.Presenter;
import com.service.radiodownloader.dataclasses.ProgrammeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WriteProgrammeData {

    @Autowired
    private WritePresenter writePresenter;
    @Autowired
    private WriteProgramme writeProgramme;
    @Autowired
    private WriteTracks writeTracks;
    @Autowired
    private GetPresenter getPresenter;
    @Autowired
    private GetProgramme getProgramme;

    public void run(int stationId, ProgrammeData programmeData) {
        String presenterName = programmeData.getPresenterName();
        LocalDateTime date = programmeData.getDate();

        Presenter presenter = getPresenter.run(presenterName, stationId);

        UUID presenterId;
        if (presenter == null) {
            presenterId = writePresenter.insertPresenterIfUnique(presenterName, stationId);
        } else {
            presenterId = presenter.getPresenterId();
        }

        GetProgramme.Programme programme = getProgramme.run(date, presenterName);
        if (programme == null) {
            UUID programmeId = UUID.randomUUID();

            writeProgramme.insertProgramme(programmeId, presenterId, date, programmeData.getDescription());
            writeTracks.insertTracks(programmeData.getTracksPlayed(), programmeId, presenterId);
        }

    }

}
