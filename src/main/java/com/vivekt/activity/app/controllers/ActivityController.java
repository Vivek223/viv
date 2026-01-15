package com.vivekt.activity.app.controllers;

import com.vivekt.activity.app.model.Activity;
import com.vivekt.activity.app.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @CrossOrigin(origins = "*") // allow all origins (for dev)
    @PostMapping("save")
    public ResponseEntity<Activity> saveActivity(@RequestBody Activity activity){
        System.out.println("************** Save Activity");
        System.out.println(activity);
        return ResponseEntity.ok(activityService.createActivityInCamelCase(activity));
    }

    @CrossOrigin(origins = "*") // allow all origins (for dev)
    @GetMapping("list")
    public ResponseEntity<List<Activity>> listActivity(){
        System.out.println("************** List Activities");

        return ResponseEntity.ok(activityService.listActivities());
    }
}
