package com.vivekt.command;

import com.vivekt.model.Task;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ListCommand implements Command {

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    @Override
    public void execute(String[] args) {
        Map<String, Integer> limitFlags = parseFlags(args);

        List<Task> tasks = loadTasks(new File(".data/list.csv"));
        if (tasks == null) return;

        // Sort by status priority
        tasks.sort(Comparator.comparingInt(t -> getStatusPriority(t.status)));

        // Group by status
        Map<String, List<Task>> grouped = tasks.stream()
                .collect(Collectors.groupingBy(t -> t.status.toLowerCase()));

        // Order we want to print in
        List<String> order = List.of("in-progress", "not-started", "completed");

        for (String status : order) {
            List<Task> group = grouped.getOrDefault(status, Collections.emptyList());

            int limit = limitFlags.getOrDefault(status, Integer.MAX_VALUE);
            List<Task> toDisplay = group.stream().limit(limit).toList();

            for (Task task : toDisplay) {
//                if (!toDisplay.isEmpty()) {
//                    System.out.println("\n-- " + status.toUpperCase() + " --");
//                }

                System.out.println(formatTask(task));
            }
        }
    }

    public Map<String, Integer> parseFlags(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("--") && arg.contains("=")) {
                String[] parts = arg.substring(2).split("=", 2);
                String status = parts[0].toLowerCase();
                try {
                    int count = Integer.parseInt(parts[1]);
                    map.put(status, count);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return map;
    }

    public List<Task> loadTasks(File csvFile) {
        if (!csvFile.exists()) {
            System.err.println("Error: " + csvFile + " does not exist.");
            return null;
        }

        List<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean first = true;

            while ((line = reader.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    tasks.add(new Task(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }

        return tasks;
    }

    public String formatTask(Task task) {
        String color = switch (task.status.toLowerCase()) {
            case "completed" -> GREEN;
            case "not-started" -> YELLOW;
            case "in-progress" -> BLUE;
            default -> RESET;
        };

        return String.format("%sID: %s | Title: %s | Status: %s | Notes: %s%s",
                color, task.id, task.title, task.status, task.notes, RESET);
    }

    public int getStatusPriority(String status) {
        return switch (status.toLowerCase()) {
            case "in-progress" -> 0;
            case "not-started" -> 1;
            case "completed" -> 2;
            default -> 3;
        };
    }
}
