package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

public interface ProjectService extends CrudService<ProjectDTO,String> {
    //<ProjectDTO,String> : String, because unique identifier is projectCode
}
