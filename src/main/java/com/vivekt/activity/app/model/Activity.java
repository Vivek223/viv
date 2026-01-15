package com.vivekt.activity.app.model;

public class Activity {
    Long id;
    String title;
    String desc;
    String status;

    public Activity(Long id, String title, String desc, String status) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.status = status;
    }

    public Activity(String title, String desc, String status) {

        this.title = title;
        this.desc = desc;
        this.status = status;

    }

    public Activity(String csvRow) {
        String[] fields = csvRow.split(",");
        this.id = Long.parseLong(fields[0]);
        this.title = fields[1];
        this.desc = fields[2];
        this.status = fields[3];


    }

    public Activity() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
