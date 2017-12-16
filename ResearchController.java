package com.example.demo.controller;

import com.example.demo.model.Activity;
import com.example.demo.model.Province;
import com.example.demo.model.Users;
import java.util.Date;
import java.util.List;

import com.example.demo.service.ActivityService;
import com.example.demo.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ResearchController {
	@Autowired
	ActivityService activityService;
    private final StorageService storageService;

    @Autowired
    public ResearchController(StorageService storageService) {
        this.storageService = storageService;
    }


	@GetMapping("/research")
	public String researchRenderer(Model model){
		List<Activity> researches = activityService.findAllResearches();
		model.addAttribute("researches",researches);
		model.addAttribute("research", new Activity());
//		Activity ac=new Activity();
//		Resource file = storageService.loadAsResource(ac.getFilename());
//		model.addAttribute("pho", file);


		return "addResearch";
	}

	@PostMapping("/research/add")
	public String addResearch(@RequestParam("photo") MultipartFile photo,@RequestParam("file") MultipartFile file, @ModelAttribute("research") Activity activity
							  ){

		activity.setFilename(file.getOriginalFilename());
		activity.setPhotoname(photo.getOriginalFilename());
		activity.setType("research");
		activity.setStatus("Not Publish");
        storageService.store(file);
		storageService.store(photo);
		activityService.save(activity);
		storageService.init();
		return "redirect:/research";

	}

	@RequestMapping(value = "/research/delete/{id}")
	public String deleteResearch(@PathVariable("id") int id){

		activityService.delete(id);



		return "redirect:/research";
	}
	@RequestMapping(value = "/research/edit/{id}")
	public String editResearch(@PathVariable("id") int id, Model model){

		Activity activity = activityService.findOne(id);

		model.addAttribute("research", activity);


		return "/addResearch";

	}
}
