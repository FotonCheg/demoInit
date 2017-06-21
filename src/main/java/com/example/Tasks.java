package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 2017 on 08.05.2017.
 */
public interface Tasks extends CrudRepository<Task, Long> {
    List<Task> findAll();
}
