package com.leemo.leemo.service;

import com.leemo.leemo.entity.Candidates;
import com.leemo.leemo.repo.CandidatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(allExTasks.size() == 0){
            Candidates candidate = new Candidates();
            candidate.setExecutor(email);
            candidate.setTaskId(taskId);
            candidatesRepository.save(candidate);
        }
        else {
        for (Candidates c : allExTasks) {
            if (c.getTaskId().equals(taskId) && c.getExecutor().equals(email)) {

                throw new Exception("Error");
            } else {
                Candidates candidate = new Candidates();
                candidate.setExecutor(email);
                candidate.setTaskId(taskId);
                candidatesRepository.save(candidate);
            }
        }
        }

    }
    public void deleteTaskCandidates(Integer id){
        Integer newId = 0;
        candidatesRepository.deleteCandidates(id,newId);
    }


    public List<Candidates>getCandidates(Long id){
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
