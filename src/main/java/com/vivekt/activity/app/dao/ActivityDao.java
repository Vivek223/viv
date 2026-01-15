package com.vivekt.activity.app.dao;

import com.vivekt.activity.app.model.Activity;

import java.util.List;

public interface ActivityDao {
    Activity createActivity(Activity activity);

    List<Activity> findAll();
}
