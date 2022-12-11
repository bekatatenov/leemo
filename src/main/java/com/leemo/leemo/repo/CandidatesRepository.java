package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CandidatesRepository extends JpaRepository<Candidates,Long> {
    List<Candidates> findAllByTaskId(Long id);
    List<Candidates> findCandidatesByExecutor(String email);
    void deleteAllByTaskId(Long id);
}
