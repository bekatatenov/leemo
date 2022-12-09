package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CandidatesRepository extends JpaRepository<Candidates,Long> {
    Candidates findByTaskId(Long id);
}
