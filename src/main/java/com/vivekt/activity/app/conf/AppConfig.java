package com.vivekt.activity.app.conf;

import com.vivekt.activity.app.dao.ActivityDaoCSVImpl;
import com.vivekt.activity.app.service.ActivityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${csv.file.path}")
    String csvFilePath;

    @Bean
    public ActivityService activityService(){

        return new ActivityService(new ActivityDaoCSVImpl(csvFilePath));
    }
}
