// model/Task.java
package com.vivekt.model;

public class Task {
    public final String id;
    public final String title;
    public final String status;
    public final String notes;

    public Task(String id, String title, String status, String notes) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.notes = notes;
    }
}
