package com.vivekt.activity.app.controllers;

import com.vivekt.activity.app.model.Activity;
import com.vivekt.activity.app.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @GetMapping("info")
    public String info(){
        return "Rest app coming soon.. v0.2";
    }

    @PostMapping("save")
    public ResponseEntity<Activity> saveActivity(@RequestBody Activity activity){
        System.out.println("**************");
        System.out.println(activity);
        return ResponseEntity.ok(activityService.createActivityInCamelCase(activity));
    }
}
