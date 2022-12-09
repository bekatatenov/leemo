package com.leemo.leemo.service;

import com.leemo.leemo.entity.Candidates;
import com.leemo.leemo.repo.CandidatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class CandidatesService {
    @Autowired
    CandidatesRepository candidatesRepository;

    public void createCandidates(Candidates candidates) {
        candidatesRepository.save(candidates);
    }

    public void respond(Long taskId, String email) throws Exception {
        List<Candidates> allExTasks = candidatesRepository.findCandidatesByExecutor(email);
        Candidates candidate = new Candidates();
        for (Candidates c : allExTasks) {
            if (c.getTaskId().equals(taskId)) {
                throw new Exception("Error");
            } else {
                candidate.setExecutor(email);
                candidate.setTaskId(taskId);
                candidatesRepository.save(candidate);
            }
        }
    }

    public Candidates getCandidates(Long id) {
        return candidatesRepository.findByTaskId(id);
    }

    public void responded(Long taskId, String username) {
        Candidates candidates = getCandidates(taskId);
        if (candidates.getExecutor() != null) {
            if (username.equals(candidates.getExecutor())) {
            }
        }
    }
}
