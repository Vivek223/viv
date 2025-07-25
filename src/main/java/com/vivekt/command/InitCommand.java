package com.vivekt.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InitCommand implements Command {

    @Override
    public void execute() {
        File dataDir = new File(".data");
        if (!dataDir.exists()) {
            if (dataDir.mkdir()) {
                System.out.println("Created directory: .data");
            } else {
                System.err.println("Failed to create directory .data");
                return;
            }
        } else {
            System.out.println("Directory .data already exists.");
        }

        File listFile = new File(dataDir, "list.csv");
        if (!listFile.exists()) {
            try (FileWriter writer = new FileWriter(listFile)) {
                writer.write("id,title,status,notes\n");
                writer.write("1,Sample Task,completed,This is a sample note\n");
                writer.write("2,Sample Task,not-started,This is a sample note\n");
                writer.write("3,Sample Task,not-started,This is a sample note\n");
                writer.write("4,Sample Task,not-started,This is a sample note\n");
                writer.write("5,Sample Task,in-progress,This is a sample note\n");
                writer.write("6,Sample Task,in-progress,This is a sample note\n");
                System.out.println("Initialized list.csv with sample data.");
            } catch (IOException e) {
                System.err.println("Failed to write list.csv: " + e.getMessage());
            }
        } else {
            System.out.println("File list.csv already exists.");
        }
    }
}
