package com.vivekt.activity.app.service;

import com.vivekt.activity.app.MyUtil;
import com.vivekt.activity.app.dao.ActivityDao;
import com.vivekt.activity.app.model.Activity;
import org.springframework.http.ResponseEntity;

import java.util.List;


public class ActivityService {

    private ActivityDao dao;

    public ActivityService(ActivityDao dao){
        this.dao = dao;
    }

    public Activity createActivity(Activity activity) {
        Activity a = new Activity(activity.getTitle().toUpperCase(), activity.getDesc().toUpperCase(), activity.getStatus().toUpperCase());
        return dao.createActivity(a);
    }

    public Activity createActivityInCamelCase(Activity activity){
        //Activity a = new Activity(activity.getTitle().)
        String title = MyUtil.capitalizeFirstLetter(activity.getTitle());
        String desc = MyUtil.capitalizeFirstLetter(activity.getDesc());
        String status = activity.getStatus();

        Activity enrichedActivity = new Activity(title, desc, status.toUpperCase());

        return dao.createActivity(enrichedActivity);

    }

    public List<Activity> listActivities(){
        return dao.findAll();
    }
}
