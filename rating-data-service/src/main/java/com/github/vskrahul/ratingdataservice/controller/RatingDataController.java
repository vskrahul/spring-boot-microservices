package com.github.vskrahul.ratingdataservice.controller;

import com.github.vskrahul.ratingdataservice.client.RatingDataClient;
import com.github.vskrahul.ratingdataservice.model.Rating;
import com.github.vskrahul.ratingdataservice.model.UserRating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("ratingdata")
public class RatingDataController {

    private RatingDataClient ratingDataClient;

    @Autowired
    public RatingDataController(RatingDataClient ratingDataClient) {
        this.ratingDataClient = ratingDataClient;
    }

    @RequestMapping("{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return this.ratingDataClient.getMovieRating(movieId);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getRatings(@PathVariable("userId") String userId) {
        return this.ratingDataClient.getRatings(userId);
    }
}
