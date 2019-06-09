package com;

import com.service.radiodownloader.database.springy.RunMigrations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private RunMigrations runMigrations;

    @Override
    public void run(String[] args) {
        runMigrations.run();
    }
}
