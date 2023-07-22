package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    RoleService roleService;
    UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createUser(Model model){
        model.addAttribute("user",new UserDTO());
        model.addAttribute("roles", roleService.findAll());      //bring me all roles from DB
        model.addAttribute("users", userService.findAll());
        return "user/create";
    }

    @PostMapping("/create")
    public String insertUser(@ModelAttribute("user") UserDTO user, Model model){
        // New version of Spring does NOT need @ModelAttribute("user")

        // I need to add the data from the form into the database (map)
        userService.save(user);

//        return "/user/create";
        return "redirect:/user/create";
    }

    @GetMapping("/update/{username}")
    public String editUser(@PathVariable("username") String username, Model model){

        //define attributes
        model.addAttribute("user",userService.findById(username));
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());

        return "/user/update";
    }

    @PostMapping("/update/{username}")
    public String updateUser(@PathVariable("username") String username, UserDTO user){
        // New version of Spring does NOT need @ModelAttribute("user")

        userService.update(user);
        return "redirect:/user/create";
    }















}
