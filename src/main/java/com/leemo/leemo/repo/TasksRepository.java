package com.leemo.leemo.repo;


import com.leemo.leemo.entity.Tasks;

import com.leemo.leemo.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TasksRepository extends JpaRepository<Tasks,Long> {

    List<Tasks> findAllByStatus(TaskStatus taskStatus);
    List<Tasks> findAllByStatusAndCustomerId(TaskStatus taskStatus, Long id);
    Tasks findAllById (Long id);
    List<Tasks> findAllByCustomerId(Long id);
    List<Tasks> findAllByExecutorIdAndStatus(Long id, TaskStatus status);

    @Transactional
    @Modifying
    @Query(value = "Update tasks set executor_id = :executor where id = :id", nativeQuery = true)
    void updateTask(@Param(value = "executor") Long executorId,@Param(value = "id")  Long taskId);
}
