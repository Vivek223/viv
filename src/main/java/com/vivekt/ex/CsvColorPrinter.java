package com.vivekt.ex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvColorPrinter {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";   // for OPEN
    private static final String YELLOW = "\u001B[33m";  // for IN_PROGRESS
    private static final String RED = "\u001B[31m";     // for CLOSED

    public static void main(String[] args) {
        String fileName = "src/main/resources/color.csv"; // path to your CSV

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            boolean firstLine = true;

            System.out.printf("%-5s %-20s %-15s%n", "ID", "TITLE", "STATUS");
            System.out.println("--------------------------------------------------");

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

                String color = getColorForStatus(status);

                // Print formatted output with color
                System.out.printf("%-5s %-20s %s%-15s%s%n",
                        id, title, color, status, RESET);
            }

        } catch (IOException e) {
            e.printStackTrace();
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
