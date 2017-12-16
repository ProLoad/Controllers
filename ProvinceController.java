package com.example.demo.controller;

import com.example.demo.model.Province;
import com.example.demo.service.ActivityService;
import com.example.demo.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ProvinceController {

	@Autowired
	ProvinceService provinceService;

	@GetMapping("/province")
	public String provinceRenderer(Model model){
		List<Province> provinces = provinceService.findAll();
		model.addAttribute("provinces", provinces);

		model.addAttribute("province", new Province());


		return "province";
	}

	@PostMapping("/province/add")
	public String addProvince(@ModelAttribute("province") Province province){


		provinceService.save(province);

		return "redirect:/province/list";

	}

	@RequestMapping(value = {"/province/list", "/"}, method = RequestMethod.GET)
	public String listProvinces(Model model){


		List<Province> provinces = provinceService.findAll();
		model.addAttribute("province", new Province());

		model.addAttribute("provinces", provinces);

		return "/province";
	}


	@RequestMapping(value = "/province/edit/{id}")
	public String editProvince(@PathVariable("id") int id, Model model){

		Province province = provinceService.findOne(id);

		model.addAttribute("province", province);


		return "province";

	}

	@RequestMapping(value = "/province/delete/{id}")
	public String deleteProvince(@PathVariable("id") int id){

		provinceService.delete(id);



		return "redirect:/province/list";
	}
}
