package com.leemo.leemo.repo;

import com.leemo.leemo.entity.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository

public interface CandidatesRepository extends JpaRepository<Candidates,Long> {

    List<Candidates> findAllByTaskId(Long id);
    List<Candidates> findCandidatesByExecutor(String email);

    @Transactional
    @Modifying
    @Query(value = "Update candidates set task_id = :id where task_id = :taskId", nativeQuery = true)
    void deleteCandidates(@Param(value = "taskId")  Integer taskId, @Param(value = "id") Integer id );


}