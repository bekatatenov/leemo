package com.leemo.leemo.service;

import com.leemo.leemo.entity.Candidates;
import com.leemo.leemo.repo.CandidatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CandidatesService {
    @Autowired
    CandidatesRepository candidatesRepository;

    public void createCandidates(Candidates candidates) {
        candidatesRepository.save(candidates);
    }

    public void respond(Long taskId, String email) throws Exception {
        ArrayList<Candidates> allExTasks = candidatesRepository.findCandidatesByExecutor(email);
        if (allExTasks.size() == 0) {
            Candidates candidate = new Candidates();
            candidate.setExecutor(email);
            candidate.setTaskId(taskId);
            candidatesRepository.save(candidate);
        } else {
            int count = 0;
            int prosto = 0;
            for (int i = 0; i < allExTasks.size(); i++) {
                if (allExTasks.get(i).getTaskId().equals(taskId)) {
                    count++;
                } else {
                    prosto++;
                }
            }
            if (count == 0) {
                Candidates candidate = new Candidates();
                candidate.setExecutor(email);
                candidate.setTaskId(taskId);
                candidatesRepository.save(candidate);
            }else {
                throw new Exception("Error");
            }
        }
    }

    public void deleteTaskCandidates(Integer id) {
        Integer newId = 0;
        candidatesRepository.deleteCandidates(id, newId);
    }


    public List<Candidates> getCandidates(Long id) {
        return candidatesRepository.findAllByTaskId(id);
    }

    public Candidates getCandidate(Long id) {
        return candidatesRepository.findById(id).orElse(null);
    }

    public void responded(Long taskId, String username) {
        Candidates candidates = getCandidate(taskId);
        if (candidates.getExecutor() != null) {
            if (username.equals(candidates.getExecutor())) {
            }
        }
    }
}
