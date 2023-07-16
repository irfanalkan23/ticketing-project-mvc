package com.cydeo.service;

import java.util.List;

public interface CrudService<T, ID> {
    //pass T and ID as a class parameter! Generics
    T save(T object);
    List<T> findAll();
    T findById(ID id);  //ID : unique identifier; String, Long
    void deleteById(ID id);
}
