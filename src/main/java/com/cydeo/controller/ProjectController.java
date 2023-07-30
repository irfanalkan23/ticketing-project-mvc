package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createProject(Model model){
        //I need attributes: empty project object, Managers, Projects
        model.addAttribute("project",new ProjectDTO());
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("managers",userService.findManagers());

        return "/project/create";
    }

    @PostMapping("/create")
    public String insertProject(ProjectDTO project){
        // we don't need to add @ModelAttribute("project")
        projectService.save(project);
        return "redirect:/project/create";
    }

    @GetMapping("/delete/{projectcode}")
    public String deleteProject(@PathVariable("projectcode") String projectcode){
        projectService.deleteById(projectcode);

        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectcode}")
    public String completeProject(@PathVariable("projectcode") String projectcode){
        projectService.complete(projectService.findById(projectcode));

        return "redirect:/project/create";
    }

    @GetMapping("/update/{projectcode}")
    public String editProject(@PathVariable("projectcode") String projectcode, Model model){
        model.addAttribute("project",projectService.findById(projectcode));
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("managers",userService.findManagers());

        return "/project/update";
    }

    @PostMapping("/update")
    public String updateProject(ProjectDTO project){
        projectService.update(project);

        return "redirect:/project/create";
    }




}








