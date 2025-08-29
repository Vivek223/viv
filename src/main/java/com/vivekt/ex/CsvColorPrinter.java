package com.vivekt.ex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvColorPrinter {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";   // for OPEN
    private static final String YELLOW = "\u001B[33m";  // for IN_PROGRESS
    private static final String RED = "\u001B[31m";     // for CLOSED

    public static void main(String[] args) {
        String fileName = "src/main/resources/color.csv"; // path to your CSV

        // Map<Status, List<Rows>>
        Map<String, List<String[]>> groupedData = new HashMap<>();
        groupedData.put("OPEN", new ArrayList<>());
        groupedData.put("IN_PROGRESS", new ArrayList<>());
        groupedData.put("CLOSED", new ArrayList<>());

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { // skip header
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String id = parts[0].trim();
                String title = parts[1].trim();
                String status = parts[2].trim().toUpperCase();

                groupedData.getOrDefault(status, new ArrayList<>()).add(new String[]{id, title, status});
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print tables side by side
        printTables(groupedData);
    }

    private static void printTables(Map<String, List<String[]>> groupedData) {
        String[] statuses = {"OPEN", "IN_PROGRESS", "CLOSED"};

        // Table headers
        for (String status : statuses) {
            String color = getColorForStatus(status);
            System.out.printf("%-5s %-20s %-15s    ", "ID", "TITLE", color + status + RESET);
        }
        System.out.println();

        // Separator line
        for (int i = 0; i < statuses.length; i++) {
            System.out.print("------------------------------------------    ");
        }
        System.out.println();

        // Find max number of rows among statuses
        int maxRows = groupedData.values().stream().mapToInt(List::size).max().orElse(0);

        // Print row by row across tables
        for (int i = 0; i < maxRows; i++) {
            for (String status : statuses) {
                List<String[]> rows = groupedData.get(status);
                if (i < rows.size()) {
                    String[] row = rows.get(i);
                    String color = getColorForStatus(status);
                    System.out.printf("%-5s %-20s %s%-15s%s    ",
                            row[0], row[1], color, row[2], RESET);
                } else {
                    // Empty space if no row
                    System.out.printf("%-5s %-20s %-15s    ", "", "", "");
                }
            }
            System.out.println();
        }
    }

    private static String getColorForStatus(String status) {
        switch (status) {
            case "OPEN":
                return GREEN;
            case "IN_PROGRESS":
                return YELLOW;
            case "CLOSED":
                return RED;
            default:
                return RESET;
        }
    }
}
