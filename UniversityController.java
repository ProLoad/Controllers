package com.example.demo.controller;

import com.example.demo.model.Activity;
import com.example.demo.model.Province;
import com.example.demo.model.ProvinceUniversity;
import com.example.demo.model.University;
import com.example.demo.service.ActivityService;
import com.example.demo.service.ProvinceService;
import com.example.demo.service.UniversityService;
import com.example.demo.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UniversityController {
	@Autowired
	UniversityService universityService;
	@Autowired
	ProvinceService provinceService;

	@GetMapping("/university")
	public String universityRenderer(Model model){
		List<University> universities = universityService.findAll();

		List<Province> provinces = provinceService.findAll();
		List<ProvinceUniversity> provinceUniversities=new ArrayList<>();
		//Map<String, String> provinceUni = new HashMap<>();
		 int number=1;

		for (University university : universities){
			ProvinceUniversity prouni=new ProvinceUniversity();
			provinceUniversities.add(prouni);
			Province  province = provinceService.findOne(university.getProvince_id());
			prouni.setId(university.getId());

			prouni.setUniversityname(university.getUniverse());
			prouni.setProvincename(province.getCity());
			prouni.setNumber(number);
			number++;
			//provinceUni.put(province.getCity(), university.getUniverse());

		}
		model.addAttribute("provinceUniversities", provinceUniversities);

		model.addAttribute("universities", universities);
		model.addAttribute("provinces", provinces);

		model.addAttribute("university", new University());



		return "university";
	}

	@PostMapping("/university/add")
	public String addUniversity(@ModelAttribute("university") University university){


		universityService.save(university);

		return "redirect:/university";

	}
	@RequestMapping(value = "/university/edit/{id}")
	public String editUniversity(@PathVariable("id") int id, Model model){
		List<Province> provinces = provinceService.findAll();
		model.addAttribute("provinces", provinces);

		University university = universityService.findOne(id);

		model.addAttribute("university", university);


		return "university";

	}

	@RequestMapping(value = "/university/delete/{id}")
	public String deleteUniversity(@PathVariable("id") int id){

		universityService.delete(id);



		return "redirect:/university";
	}
}
