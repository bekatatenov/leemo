package com.leemo.leemo.service;

import com.leemo.leemo.entity.Rating;
import com.leemo.leemo.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public void updateRating(Double rate,Long id){
        Rating rating = getRating(id);
        rating.setRates(rating.getRates()+1);
        ratingRepository.save(rating);
        ratingRepository.updateRate(rate,id);
    }
    public Rating getRating(Long id){
   return ratingRepository.findFirstById(id);
    }

}
