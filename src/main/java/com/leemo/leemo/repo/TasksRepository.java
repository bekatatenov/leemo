package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Tasks,Long> {
}
