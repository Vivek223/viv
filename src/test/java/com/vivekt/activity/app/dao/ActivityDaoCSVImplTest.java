package com.vivekt.activity.app.dao;

import com.vivekt.activity.app.model.Activity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ActivityDaoCSVImplTest {

    private ActivityDaoCSVImpl csvActivityDao;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary file for testing
        tempFile = File.createTempFile("activities", ".csv");
        // Delete content of file (keep the file)
        Files.write(tempFile.toPath(), new byte[0]);

        // Instantiate DAO with temp file path
        csvActivityDao = new ActivityDaoCSVImpl(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        // Cleanup after each test
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testCreateActivity(){
        //Arrange
        Activity input = new Activity("watch a movie", "in tv");

        Activity result = csvActivityDao.createActivity(input);

        System.out.println(result.getId());
        System.out.println(result);


    }
}
