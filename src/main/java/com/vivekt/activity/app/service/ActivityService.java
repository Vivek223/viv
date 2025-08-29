package com.vivekt.activity.app.service;

import com.vivekt.activity.app.MyUtil;
import com.vivekt.activity.app.dao.ActivityDao;
import com.vivekt.activity.app.model.Activity;


public class ActivityService {

    private ActivityDao dao;

    public ActivityService(ActivityDao dao){
        this.dao = dao;
    }

    public Activity createActivity(Activity activity) {
        Activity a = new Activity(activity.getTitle().toUpperCase(), activity.getDesc().toUpperCase());
        return dao.createActivity(a);
    }

    public Activity createActivityInCamelCase(Activity activity){
        //Activity a = new Activity(activity.getTitle().)
        String title = MyUtil.capitalizeFirstLetter(activity.getTitle());
        String desc = MyUtil.capitalizeFirstLetter(activity.getDesc());
        Activity enrichedActivity = new Activity(title, desc);

        return dao.createActivity(enrichedActivity);

    }
}
