package com.vivekt.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ListCommand implements Command {

    @Override
    public void execute() {
        File listFile = new File(".data/list.csv");
        if (!listFile.exists()) {
            System.err.println("Error: .data/list.csv does not exist. Run 'init' first.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(listFile))) {
            String line;
            boolean first = true;

            while ((line = reader.readLine()) != null) {
                if (first) {
                    first = false; // skip header
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    System.out.printf("ID: %s | Title: %s | Status: %s | Notes: %s%n",
                            parts[0], parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("Malformed line: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading list.csv: " + e.getMessage());
        }
    }
}
