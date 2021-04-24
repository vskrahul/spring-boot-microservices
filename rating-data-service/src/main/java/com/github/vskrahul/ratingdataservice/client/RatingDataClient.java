package com.github.vskrahul.ratingdataservice.client;

import com.github.vskrahul.ratingdataservice.model.Rating;
import com.github.vskrahul.ratingdataservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Repository
public class RatingDataClient {

    @HystrixCommand(fallbackMethod = "getRatingsFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
    })
    public UserRating getRatings(String userId) {
        return new UserRating(userId, Arrays.asList(
                new Rating("100", 2),
                new Rating("200", 3)
        ));
    }

    @HystrixCommand(fallbackMethod = "getMovieRatingFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
    })
    public Rating getMovieRating(String movieId) {
        return new Rating(movieId, 4);
    }

    public Rating getMovieRatingFallback(String movieId) {
        return new Rating(movieId, 4);
    }

    public UserRating getRatingsFallback(String userId) {
        return new UserRating(userId, Arrays.asList(
                new Rating("100", 2),
                new Rating("200", 3)
        ));
    }
}