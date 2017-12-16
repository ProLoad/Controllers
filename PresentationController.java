package com.example.demo.controller;

import com.example.demo.model.Activity;
import com.example.demo.model.Users;
import java.util.Date;
import java.util.List;

import com.example.demo.service.ActivityService;
import com.example.demo.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class PresentationController {
    @Autowired
    ActivityService activityService;
    private final StorageService storageService;

    @Autowired
    public PresentationController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/presentation")
    public String researchRenderer(Model model){
        List<Activity> presentations = activityService.findAllPresentations();
        model.addAttribute("presentations",presentations);
        model.addAttribute("presentation", new Activity());


        return "addPresentation";
    }

    @PostMapping("/presentation/add")
    public String addResearch(@RequestParam("photo") MultipartFile photo,@RequestParam("file") MultipartFile file, @ModelAttribute("presentation") Activity activity
    ){

        activity.setFilename(file.getOriginalFilename());
        activity.setPhotoname(photo.getOriginalFilename());
        activity.setType("presentation");
        activity.setStatus("Not Publish");
        storageService.store(file);
        storageService.store(photo);
        activityService.save(activity);
//        storageService.init();

        return "redirect:/presentation";

    }
    @RequestMapping(value = "/presentation/delete/{id}")
    public String deleteProvince(@PathVariable("id") int id){

        activityService.delete(id);



        return "redirect:/presentation";
    }
    @RequestMapping(value = "/presentation/edit/{id}")
    public String editPresentation(@PathVariable("id") int id, Model model){

        Activity activity = activityService.findOne(id);

        model.addAttribute("presentation", activity);


        return "/addPresentation";

    }
}
