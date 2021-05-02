package com.github.vskrahul.ratingdataservice.controller;

import com.github.vskrahul.ratingdataservice.client.RatingDataClient;
import com.github.vskrahul.ratingdataservice.model.Rating;
import com.github.vskrahul.ratingdataservice.model.UserRating;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@Slf4j
@RestController
@RequestMapping("ratingdata")
public class RatingDataController {

    private RatingDataClient ratingDataClient;

    @Autowired
    public RatingDataController(RatingDataClient ratingDataClient,
                                @Value("${config}") String config) {
        this.ratingDataClient = ratingDataClient;
    }

    @GetMapping("{movieId}")
    @ApiOperation(value = "fetch rating for the Movie related to give movie id"
            ,notes = "provide movie id to fetch rating details"
            ,response = Rating.class)
    public Rating getRating(@PathVariable String movieId) {
        return this.ratingDataClient.getMovieRating(movieId);
    }

    @GetMapping("/users/{userId}")
    @ApiOperation(value = "fetch list of all movies rated by given user"
            ,notes = "provide userId to fetch user ratings"
            ,response = UserRating.class)
    public UserRating getRatings(@PathVariable String userId) {
        return this.ratingDataClient.getRatings(userId);
    }
}
