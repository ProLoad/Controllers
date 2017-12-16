package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.FacultyService;
import com.example.demo.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	@Autowired
	FacultyService facultyService;

	@GetMapping("/department")
	public String departmentRenderer(Model model){
		List<Department> departments = departmentService.findAll();

		List<Faculty> faculties = facultyService.findAll();
		List<FacultyDepartment> facultyDepartments=new ArrayList<>();
		//Map<String, String> provinceUni = new HashMap<>();
		 int number=1;

		for (Department department : departments){
			FacultyDepartment facdep=new FacultyDepartment();
			facultyDepartments.add(facdep);
			Faculty  faculty = facultyService.findOne(department.getFaculty_id());
			facdep.setId(department.getId());

			facdep.setDepartmentname(department.getDepartment());
			facdep.setFacultyname(faculty.getFaculty());
			facdep.setNumber(number);
			number++;
			//provinceUni.put(province.getCity(), university.getUniverse());

		}
		model.addAttribute("facultyDepartments", facultyDepartments);

		model.addAttribute("departments", departments);
		model.addAttribute("faculties", faculties);

		model.addAttribute("departmen", new Department());



		return "department";
	}

	@PostMapping("/department/add")
	public String addDepartment(@ModelAttribute("departmen") Department department){

		departmentService.save(department);


		return "redirect:/department";

	}
	@RequestMapping(value = "/department/edit/{id}")
	public String editDepartment(@PathVariable("id") int id, Model model){
		List<Faculty> faculties = facultyService.findAll();
		model.addAttribute("faculties", faculties);

		Department department = departmentService.findOne(id);

		model.addAttribute("departmen", department);


		return "department";

	}

	@RequestMapping(value = "/department/delete/{id}")
	public String deleteDepartment(@PathVariable("id") int id){

		departmentService.delete(id);



		return "redirect:/department";
	}
}
