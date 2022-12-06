package com.leemo.leemo.repo;


import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.enums.Status;

import com.leemo.leemo.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface TasksRepository extends JpaRepository<Tasks,Long> {

    List<Tasks> findAllByStatus(TaskStatus taskStatus);
    Tasks findAllById (Long id);

}
