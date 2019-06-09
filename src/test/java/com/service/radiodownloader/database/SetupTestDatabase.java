package com.service.radiodownloader.database;

import com.service.radiodownloader.database.springy.RunMigrations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JdbcTest
@TestComponent
public class SetupTestDatabase {

    @Test
    public void doThing() {
        var runMigrations = new RunMigrations();
        runMigrations.run();
    }

}
