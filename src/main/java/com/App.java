package com;

import com.service.radiodownloader.database.springy.RunMigrations;
import com.service.radiodownloader.dataclasses.Config;
import com.service.radiodownloader.pagescraping.DownloadService;
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
    @Autowired
    private DownloadService downloadService;

    @Override
    public void run(String[] args) {
        runMigrations.run();
        Config config = Config.getFromConfigFile("config.json");
        downloadService.downloadProgrammesAndWriteToDatabase(config);
    }
}
