package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatesRepository extends JpaRepository<Candidates,Long> {
    Candidates getCandidatesByTaskId(Long id);
}
