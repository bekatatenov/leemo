package com.leemo.leemo.controller;

import com.leemo.leemo.entity.Tasks;
import com.leemo.leemo.enums.TaskStatus;
import com.leemo.leemo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RatingController {
    @Autowired
    RatingService ratingService;

    @RequestMapping(value = "/vote-to-user")
    public ModelAndView voteToUser(Long id, Double rate) {
        ModelAndView modelAndView = new ModelAndView("userRating");
        ratingService.updateRating(rate,id);
        return modelAndView;
    }
}
