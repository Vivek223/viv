package com.vivekt.service;

import com.vivekt.model.Task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TaskService {

    public List<Task> loadTasks(String path){
        //Add Class Path resource here
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line =br.readLine()) != null){
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
