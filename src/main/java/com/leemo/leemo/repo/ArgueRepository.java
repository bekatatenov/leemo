package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Argue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArgueRepository extends JpaRepository<Argue,Long> {
}
