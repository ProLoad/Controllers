package com.example.demo.controller;

import com.example.demo.model.Activity;
import com.example.demo.model.Users;
import com.example.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PublishController {
    @Autowired
    private ActivityService activityService;
    @GetMapping("/publish")
    public String publish(Model model){
        List<Activity> activityList = activityService.findAll();
        model.addAttribute("activities",activityList);
        model.addAttribute("activity", new Activity());

        return "/publish";
    }

    @RequestMapping(value = "/publish/edit/published/{id}")
    public String postpublish(@PathVariable("id") int id, Model model){

        Activity activity = activityService.findOne(id);
        activity.setStatus("Publish");
        activityService.save(activity);
        model.addAttribute("activity", activity);
        model.addAttribute("activities",activityService.findAll());

        return "/publish";

    }
    @RequestMapping(value = "/publish/edit/unpublished/{id}")
    public String postunpublish(@PathVariable("id") int id, Model model){

        Activity activity = activityService.findOne(id);
        activity.setStatus("Not Publish");
        activityService.save(activity);
        model.addAttribute("activity", activity);
        model.addAttribute("activities",activityService.findAll());

        return "/publish";

    }

}
