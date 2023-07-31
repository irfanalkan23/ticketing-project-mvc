package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO,String> implements ProjectService {

    private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO save(ProjectDTO object) {
        if (object.getProjectStatus() == null){
            object.setProjectStatus(Status.OPEN);
        }
        return super.save(object.getProjectCode(),object);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(ProjectDTO object) {

        //since the user is not entering projectStatus in the Project Create form,
        //update() method is trying to Update the object.projectStatus with NULL information,
        //and "enum Status" is trying to assign the "value" of NULL, which gives error!
        //iot fix this, we assign the current object.projectStatus (in map) information as the projectStatus:
        if (object.getProjectStatus()==null){
            object.setProjectStatus(findById(object.getProjectCode()).getProjectStatus());
        }
        super.update(object.getProjectCode(),object);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public ProjectDTO findById(String id) {
        return super.findById(id);
    }

    @Override
    public void complete(ProjectDTO project) {
        project.setProjectStatus(Status.COMPLETE);
        //it is not enough! we need to save it:
        super.save(project.getProjectCode(),project);
    }

    @Override
    public List<ProjectDTO> findAllNonCompletedProjects() {
        return findAll().stream()
                .filter(project -> !project.getProjectStatus().equals(Status.COMPLETE))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {

        //I need to find the Projects of the manager, and convert from 7 args constructor Project object to
        //AllArgsConstructor (9 fields) including "completedTaskCounts" and "unfinishedTaskCounts" fields
        //because, we need these fields in Project Status page Project List table (Unfinished/Completed column)
        List<ProjectDTO> projectList = findAll().stream()
                .filter(project -> project.getAssignedManager().equals(manager))
                .map(project -> {
                    //build that ProjectDTO with all args constructor

                    //find all the tasks of the manager:
                    List<TaskDTO> taskList = taskService.findTasksByManager(manager);

                    //find the tasks that belong to one project (current project in stream in this map),
                    //and find the completed ones:
                    int completedTaskCounts = (int) taskList.stream()
                            .filter(t -> t.getProject().equals(project) && t.getTaskStatus() == Status.COMPLETE)
                            .count();

                    int unfinishedTaskCounts = (int) taskList.stream()
                            .filter(t -> t.getProject().equals(project) && t.getTaskStatus() != Status.COMPLETE)
                            .count();
                    project.setCompletedTaskCounts(completedTaskCounts);
                    project.setUnfinishedTaskCounts(unfinishedTaskCounts);
                    return project;
                }).collect(Collectors.toList());

        //when we have database, we will do this with SQL query triple JOIN; project table - user table - task table

        return projectList;
    }
}







