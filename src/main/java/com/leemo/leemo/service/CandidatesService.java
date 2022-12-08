package com.leemo.leemo.service;

import com.leemo.leemo.repo.CandidatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatesService {
    @Autowired
    CandidatesRepository candidatesRepository;

    public void respond(Long taskId){
        if (candidatesRepository.getCandidatesByTaskId() )
    }
}
