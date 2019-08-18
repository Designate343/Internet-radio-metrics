package com.service.api.presenters;

import org.springframework.web.bind.annotation.RestController;

import static com.service.api.presenters.PresentersResource.PRESENTERS_PATH;

@RestController
public class PresenterResource {
    public static final String PRESENTER_ID = "presenter_id";
    public static final String PRESENTER_PATH = PRESENTERS_PATH + "/{" + PRESENTER_ID + "}";
}
