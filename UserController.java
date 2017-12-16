package com.example.demo.controller;
import com.example.demo.model.Users;

import com.example.demo.service.UsersService;


import java.util.Date;


import org.apache.tomcat.jni.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.*;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {



    @Autowired

    private UsersService userService;



    @GetMapping("/login")

    public String loginPage(Model model){

        model.addAttribute("member",new Users());

        return "login";
    }


    @PostMapping("/user/signin")

    public String signin(@ModelAttribute("member")Users user){
        Date d=new Date();
        user.setRegisterdate(d);

        userService.save(user);

        return "redirect:/login";

    }


    @PostMapping("/user/login")

    public String login(@ModelAttribute("member")Users user,Model model){


        Users temp=userService.findByUsername(user.getUsername());

        if(temp==null) {

            model.addAttribute("error","your user name is incorrect");

            return "redirect:/login";
        }


        else if(temp.getPassword().equals(user.getPassword())) {

            return "redirect:/index";
        }


        else {

            model.addAttribute("error","your password is incorrect");

            return "redirect:/login";

        }


    }


}
