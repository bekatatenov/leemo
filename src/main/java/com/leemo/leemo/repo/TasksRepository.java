package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface TasksRepository extends JpaRepository<Tasks,Long> {

    @Query(value = "SELECT * FROM tasks t WHERE t.status = :status", nativeQuery = true)
    List<Tasks> findTasksByStatus(@Param(value = "status") String taskStatus);
}
