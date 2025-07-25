package com.vivekt.command;

import com.vivekt.model.Task;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class ListCommandTest {

    ListCommand listCommand;

    @BeforeEach
    void setup() {
        listCommand = new ListCommand();
    }

    @Test
    void testParseFlags_shouldExtractCorrectStatusLimits() {
        String[] args = {"--in-progress=3", "--completed=2", "--not-started=1", "--invalid"};

        Map<String, Integer> flags = listCommand.parseFlags(args);

        assertThat(flags)
                .containsEntry("in-progress", 3)
                .containsEntry("completed", 2)
                .containsEntry("not-started", 1)
                .doesNotContainKey("invalid");
    }

    @Test
    void testLoadTasks_shouldParseTasksCorrectly() throws IOException {
        Path file = createTempCsv(List.of(
                "id,title,status,notes",
                "1,Task A,in-progress,Ongoing",
                "2,Task B,completed,Done",
                "3,Task C,not-started,Waiting"
        ));

        List<Task> tasks = listCommand.loadTasks(file.toFile());

        assertThat(tasks).hasSize(3);
        assertThat(tasks).extracting("title").contains("Task A", "Task B", "Task C");

        Files.deleteIfExists(file);
    }

    @Test
    void testLimitTasksPerStatus_usingFlags() {
        List<Task> mockTasks = List.of(
                new Task("1", "A", "in-progress", ""),
                new Task("2", "B", "in-progress", ""),
                new Task("3", "C", "not-started", ""),
                new Task("4", "D", "completed", ""),
                new Task("5", "E", "completed", "")
        );

        Map<String, List<Task>> grouped = groupAndLimitTasks(mockTasks, Map.of(
                "in-progress", 1,
                "completed", 1
        ));

        assertThat(grouped.get("in-progress")).hasSize(1);
        assertThat(grouped.get("completed")).hasSize(1);
        assertThat(grouped.getOrDefault("not-started", List.of())).hasSize(0);
    }

    @Test
    void testFormatTask_shouldContainColorCode() {
        Task t1 = new Task("1", "X", "in-progress", "Working");
        Task t2 = new Task("2", "Y", "not-started", "TBD");
        Task t3 = new Task("3", "Z", "completed", "Done");

        assertThat(listCommand.formatTask(t1)).contains("\u001B[34m");
        assertThat(listCommand.formatTask(t2)).contains("\u001B[33m");
        assertThat(listCommand.formatTask(t3)).contains("\u001B[32m");
    }

    // Helper method simulating the grouping/limiting logic from ListCommand
    private Map<String, List<Task>> groupAndLimitTasks(List<Task> tasks, Map<String, Integer> flags) {
        Map<String, List<Task>> grouped = new HashMap<>();

        tasks.stream()
                .collect(Collectors.groupingBy(t -> t.status.toLowerCase()))
                .forEach((status, taskList) -> {
                    int limit = flags.getOrDefault(status, 0);
                    grouped.put(status, taskList.stream().limit(limit).toList());
                });

        return grouped;
    }

    private Path createTempCsv(List<String> lines) throws IOException {
        Path tempFile = Files.createTempFile("tasks", ".csv");
        Files.write(tempFile, lines);
        return tempFile;
    }
}
