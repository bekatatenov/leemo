package com.leemo.leemo.service;

import com.leemo.leemo.entity.Candidates;
import com.leemo.leemo.repo.CandidatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatesService {
    @Autowired
    CandidatesRepository candidatesRepository;

    public void createCandidates(Candidates candidates){
        candidatesRepository.save(candidates);
    }

    public void respond(Long taskId, String email){
      Candidates candidate = candidatesRepository.findByTaskId(taskId);
        candidate.setExecutor(email);
        candidatesRepository.save(candidate);
    }

    public Candidates getCandidates(Long id){
        return candidatesRepository.findByTaskId(id);
    }

    public void responded(Long taskId, String username){
        Candidates candidates = getCandidates(taskId);
        if (candidates.getExecutor()!=null){
            if (username.equals(candidates.getExecutor())) {
            }
        }
    }
}
