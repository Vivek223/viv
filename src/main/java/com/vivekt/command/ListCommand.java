package com.vivekt.command;

import java.io.*;
import java.util.*;

public class ListCommand implements Command {

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    @Override
    public void execute() {
        File listFile = new File(".data/list.csv");
        if (!listFile.exists()) {
            System.err.println("Error: .data/list.csv does not exist. Run 'init' first.");
            return;
        }

        List<String[]> tasks = new ArrayList<>();

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
                    tasks.add(parts);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading list.csv: " + e.getMessage());
            return;
        }

        // Sort by status priority: in-progress > not-started > completed > others
        tasks.sort(Comparator.comparingInt(a -> getStatusPriority(a[2])));

        // Display
        for (String[] parts : tasks) {
            String id = parts[0];
            String title = parts[1];
            String status = parts[2];
            String notes = parts[3];

            String color = switch (status.toLowerCase()) {
                case "completed" -> GREEN;
                case "not-started" -> YELLOW;
                case "in-progress" -> BLUE;
                default -> RESET;
            };

            System.out.printf("%sID: %s | Title: %s | Status: %s | Notes: %s%s%n",
                    color, id, title, status, notes, RESET);
        }
    }

    private int getStatusPriority(String status) {
        return switch (status.toLowerCase()) {
            case "in-progress" -> 0;
            case "not-started" -> 1;
            case "completed" -> 2;
            default -> 3;
        };
    }
}
