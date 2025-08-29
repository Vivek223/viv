package com.vivekt.service;

import com.vivekt.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

public class TaskServiceTest {
    @Test
    void testLoadTask() {
        //Arrange
        List<Task> expectedTasks = List.of(new Task("1", "task1", "st1", "note1"),
                new Task("2", "task2", "st2", "note2"));
        TaskService service = new TaskService();
        String path = "test1.csv";

        //Act
        List<Task> actualTasks = service.loadTasks(path);

        //Assert
        assertThat(actualTasks).isEqualTo(expectedTasks);
    }
}
