package com.vivekt.command;

import com.vivekt.model.Task;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ListCommandTest {

    ListCommand listCommand;

    @BeforeEach
    void setup() {
        listCommand = new ListCommand();
    }

    @Test
    void testLoadTasks_shouldParseCsvCorrectly() throws IOException {
        Path tempFile = Files.createTempFile("tasks", ".csv");
        Files.write(tempFile, List.of(
                "id,title,status,notes",
                "1,Task A,in-progress,Work ongoing",
                "2,Task B,completed,Done",
                "3,Task C,not-started,Waiting"
        ));

        List<Task> tasks = listCommand.loadTasks(tempFile.toFile());

        assertThat(tasks).hasSize(3);
        assertThat(tasks).extracting("title").containsExactly("Task A", "Task B", "Task C");
        assertThat(tasks).extracting("status").contains("completed", "in-progress", "not-started");

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testFormatTask_shouldApplyCorrectColor() {
        Task t1 = new Task("1", "Task A", "in-progress", "Doing now");
        Task t2 = new Task("2", "Task B", "not-started", "TBD");
        Task t3 = new Task("3", "Task C", "completed", "Done");

        String f1 = listCommand.formatTask(t1);
        String f2 = listCommand.formatTask(t2);
        String f3 = listCommand.formatTask(t3);

        assertThat(f1).contains("\u001B[34m").contains("Task A");
        assertThat(f2).contains("\u001B[33m").contains("not-started");
        assertThat(f3).contains("\u001B[32m").contains("completed");
    }

    @Test
    void testSortingByStatusPriority() {
        List<Task> tasks = new ArrayList<>(List.of(
                new Task("1", "T1", "completed", ""),
                new Task("2", "T2", "not-started", ""),
                new Task("3", "T3", "in-progress", "")
        ));

        tasks.sort(Comparator.comparingInt(t -> listCommand.getStatusPriority(t.status)));

        assertThat(tasks).extracting("status")
                .containsExactly("in-progress", "not-started", "completed");
    }
}
