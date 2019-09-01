package com.service.api.presenters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("unchecked")
public class GetPresentersIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private CommonTestDaos commonTestDaos;

    @Before
    public void setUp() throws Exception {
        commonTestDaos = new CommonTestDaos(namedParameterJdbcTemplate);
    }

    @Test
    public void invalidStationIdReturnsNotFound() {
        ResponseEntity responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "internet_radio/station/53/presenters", Object.class);

        Assert.assertEquals(responseEntity.getStatusCode().value(), SC_NOT_FOUND);
    }

    @Test
    public void canRetrievePresetenters() {
        commonTestDaos.insertPresenter("foo_bar", 6);

        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "internet_radio/station/6/presenters", List.class);

        Assert.assertEquals(responseEntity.getStatusCode().value(), SC_OK);
        List<Object> presenter = responseEntity.getBody();

        Assert.assertNotNull(presenter);
        Assert.assertEquals(presenter.size(), 1);
    }
}
