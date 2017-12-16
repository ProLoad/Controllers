package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.FacultyService;
import com.example.demo.service.ProvinceService;
import com.example.demo.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class FacultyController {
	@Autowired
	UniversityService universityService;
	@Autowired
	FacultyService facultyService;

	@GetMapping("/faculty")
	public String facultyRenderer(Model model){
		List<University> universities = universityService.findAll();

		List<Faculty> faculties = facultyService.findAll();
		List<UniversityFaculty> universityFaculties=new ArrayList<>();
		//Map<String, String> provinceUni = new HashMap<>();
		 int number=1;

		for (Faculty faculty : faculties){
			UniversityFaculty unifac=new UniversityFaculty();
			universityFaculties.add(unifac);
			University  university = universityService.findOne(faculty.getUniversity_id());
			unifac.setId(faculty.getId());

			unifac.setUniversityname(university.getUniverse());
			unifac.setFacultyname(faculty.getFaculty());
			unifac.setNumber(number);
			number++;
			//provinceUni.put(province.getCity(), university.getUniverse());

		}
		model.addAttribute("universityFaculties", universityFaculties);

		model.addAttribute("universities", universities);
		model.addAttribute("faculties", faculties);

		model.addAttribute("facult", new Faculty());



		return "faculty";
	}

	@PostMapping("/faculty/add")
	public String addFaculty(@ModelAttribute("facult") Faculty faculty){

		facultyService.save(faculty);


		return "redirect:/faculty";

	}
	@RequestMapping(value = "/faculty/edit/{id}")
	public String editFaculty(@PathVariable("id") int id, Model model){
		List<University> universities = universityService.findAll();
		model.addAttribute("universities", universities);

		Faculty faculty = facultyService.findOne(id);

		model.addAttribute("facult", faculty);


		return "faculty";

	}

	@RequestMapping(value = "/faculty/delete/{id}")
	public String deleteFaculty(@PathVariable("id") int id){

		facultyService.delete(id);



		return "redirect:/faculty";
	}
}
