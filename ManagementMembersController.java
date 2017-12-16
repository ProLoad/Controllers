package com.example.demo.controller;

import com.example.demo.model.Activity;
import com.example.demo.model.Users;
import com.example.demo.service.UsersService;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ManagementMembersController {
    @Autowired
    UsersService usersService;
    @GetMapping("/managementmembers")
    public String userRenderer(Model model){
        List<Users> users = usersService.findAll();
        model.addAttribute("users",users);
        model.addAttribute("user", new Users());

        return "managementMembers";
    }

    @RequestMapping(value = "/managementmembers/edit/user/{id}")
    public String admintouser(@PathVariable("id") int id, Model model){

        Users user = usersService.findOne(id);
        user.setType("User");
        usersService.save(user);
        model.addAttribute("user", user);
        model.addAttribute("users",usersService.findAll());

        return "/managementmembers";

    }

    @RequestMapping(value = "/managementmembers/edit/admin/{id}")
    public String usertoadmin(@PathVariable("id") int id, Model model){

        Users user = usersService.findOne(id);
        user.setType("Admin");
        usersService.save(user);

        model.addAttribute("user", user);
        model.addAttribute("users",usersService.findAll());

        return "/managementmembers";

    }

    @RequestMapping(value = "/managementmembers/edit/notapproved/{id}")
    public String memberNotApproved(@PathVariable("id") int id, Model model){

        Users user = usersService.findOne(id);
        user.setStatus("Not Approved");
        usersService.save(user);

        model.addAttribute("user", user);
        model.addAttribute("users",usersService.findAll());

        return "/managementmembers";

    }
    @RequestMapping(value = "/managementmembers/edit/approved/{id}")
    public String memberApproved(@PathVariable("id") int id, Model model){

        Users user = usersService.findOne(id);
        user.setStatus("Approved");
        usersService.save(user);

        model.addAttribute("user", user);
        model.addAttribute("users",usersService.findAll());

        return "/managementmembers";

    }
}
