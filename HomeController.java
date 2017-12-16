package com.example.demo.controller;

import com.example.demo.model.Activity;
import com.example.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ActivityService activityService;

    @GetMapping("/home")
    public String index(Model model)
    {
        List<Activity> researches = activityService.findResearchesAllByTypeAndStatus();
        List<Activity> presentations = activityService.findAllPresentationsByTypeAndStatus();

        model.addAttribute("publishresearches",researches);
        model.addAttribute("publishpresentations",presentations);

        model.addAttribute("research", new Activity());
        return "/index";
    }
}
