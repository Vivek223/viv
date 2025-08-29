package com.vivekt.activity.app.dao;

import com.vivekt.activity.app.model.Activity;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ActivityDaoCSVImpl implements ActivityDao{

    private final String pathToCSV;

    public ActivityDaoCSVImpl(String pathToCSV){
        this.pathToCSV = pathToCSV;
    }

    @Override
    public Activity createActivity(Activity activity) {
        File file = new File(pathToCSV);
        boolean fileExists = file.exists();

        // Generate ID
        long nextId = getNextId(file);
        activity.setId(nextId);

        try (FileWriter writer = new FileWriter(file, true)) {
            // If file doesn't exist, add header
            if (!fileExists) {
                writer.write("id,title,desc,status\n");
            }



            String record = String.format("%d,%s,%s,%s\n",
                    activity.getId(),
                    escape(activity.getTitle()),
                    escape(activity.getDesc()),
                    escape(activity.getStatus()));

            writer.write(record);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to CSV file", e);
        }
        return activity;
    }

    /**
     * Finds the latest activity ID from the CSV and increments it.
     * Returns 1 if file does not exist or contains no records.
     */
    private long getNextId(File file) {
        if (!file.exists()) {
            return 1L;
        }

        try {
            return Files.lines(Paths.get(pathToCSV))
                    .skip(1) // skip header
                    .filter(line -> !line.isBlank())
                    .map(line -> line.split(",")[0]) // take first column (id)
                    .mapToLong(Long::parseLong)
                    .max()
                    .orElse(0L) + 1;
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file for ID generation", e);
        }
    }

    private String escape(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }
}
