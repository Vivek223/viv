package com.vivekt.activity.app.service;

import com.vivekt.activity.app.dao.ActivityDao;
import com.vivekt.activity.app.model.Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActivityServiceTest {

    private ActivityDao daoMock;
    private ActivityService service;

    @BeforeEach
    void setUp() {
        daoMock = mock(ActivityDao.class);
        service = new ActivityService(daoMock);
    }

    @Test
    void testCreateActivityConvertsToUpperCase() {
        // Original input in lowercase
        Activity input = new Activity("read book", "read fiction books");

        // We don't care what DAO returns; just mock it to return something
        when(daoMock.createActivity(any(Activity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service
        Activity result = service.createActivity(input);

        // Capture the argument passed to DAO
        ArgumentCaptor<Activity> captor = ArgumentCaptor.forClass(Activity.class);
        verify(daoMock).createActivity(captor.capture());
        Activity passedToDao = captor.getValue();

        // Assert that the service converted title and desc to uppercase
        assertEquals("READ BOOK", passedToDao.getTitle());
        assertEquals("READ FICTION BOOKS", passedToDao.getDesc());

        // Optionally assert that the service returned the same uppercased object
        assertEquals("READ BOOK", result.getTitle());
        assertEquals("READ FICTION BOOKS", result.getDesc());
    }

    @Test
    void testCreateActivityInCamelCase(){
        //Arrange
        Activity input = new Activity("play a song", "hindi music");
        Activity expected = new Activity("Play A Song", "Hindi Music");

        // We don't care what DAO returns; just mock it to return something
        when(daoMock.createActivity(any(Activity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Activity> captor = ArgumentCaptor.forClass(Activity.class);

        //Act
        Activity result = service.createActivityInCamelCase(input);

        //Assert
        verify(daoMock).createActivity(captor.capture());
        Activity passedToDao = captor.getValue();

        //Arguments passed to Dao
        assertEquals(expected.getTitle(), passedToDao.getTitle());
        assertEquals(expected.getTitle(), passedToDao.getTitle());

        //Service results
        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getTitle(), result.getTitle());


    }
}
