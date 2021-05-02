package com.github.vskrahul.movieinfoservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.github.vskrahul.movieinfoservice.models.MovieSummary;
import com.github.vskrahul.movieinfoservice.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
public class MovieInfoService {

    private RestTemplate restTemplate;
    private String apiKey;

    @Autowired
    public MovieInfoService(RestTemplate restTemplate
                            ,@Value("${api.key}")  String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @HystrixCommand(fallbackMethod = "movieInfoFallback",
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
    })
    public MovieSummary movieInfo(String movieId) {
        MovieSummary movieSummary = null;
        LocalDateTime startTime = LocalDateTime.now();
        log.info("[method=movieInfo] [time={}] [message=trying to make connection]", startTime);
        try {
            movieSummary = restTemplate.getForObject(
                        "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey
                        , MovieSummary.class);
            log.info("[method=movieInfo] [movieId={}] [duration={}] [responseBody={}]"
                            ,movieId
                            ,Duration.between(startTime, LocalDateTime.now())
                            ,JsonUtil.toJsonString(movieSummary));
        } catch(RestClientException e) {
            log.error("[method=movieInfo] [duration={}] [errorMessage={}]"
                    ,Duration.between(startTime, LocalDateTime.now())
                    ,e.getMessage());
        }
        return movieSummary;
    }

    public MovieSummary movieInfoFallback(String movieId) {
        return new MovieSummary("1", "No Movie", "No Movie");
    }
}
