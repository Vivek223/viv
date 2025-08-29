package com.vivekt.activity.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    @GetMapping("info")
    public String info(){
        return "Rest app coming soon.. v0.2";
    }
}
