package com.github.vskrahul.moviecatalogservice.client;

import com.github.vskrahul.moviecatalogservice.model.Rating;
import com.github.vskrahul.moviecatalogservice.model.UserRating;
import com.github.vskrahul.moviecatalogservice.util.JsonUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
public class MovieRatingClient {

    private RestTemplate restTemplate;
    private WebClient.Builder webClientBuilder;

    @Autowired
    public MovieRatingClient(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    public Rating getRating(String movieId) {
        UriComponents uriComponents = UriComponentsBuilder
                                                .fromUriString("http://RATING-DATA-SERVICE/ratingdata/{movieId}")
                                                .build();
        Rating rating = null;
        try {
            rating = restTemplate.getForObject(uriComponents.expand(movieId).toUri(), Rating.class);
            /*rating = webClientBuilder.build()
                            .get()
                            .uri(uriComponents.expand(movieId).toUri())
                            .retrieve()
                            .bodyToMono(Rating.class)
                            .block();*/

            log.info("[method=getRating] [movieId={}] [responseBody={}]",
                    movieId,
                    JsonUtil.toJsonString(rating));
        } catch(RestClientException e) {
            log.error("[method=getRating] [errorMessage={}]", e.getMessage());
        }
        return rating;
    }

    @HystrixCommand(fallbackMethod = "getFallbackRatings")
    public UserRating getRatings(String userId) {
        UriComponents uriComponents = UriComponentsBuilder
                                        .fromUriString("http://RATING-DATA-SERVICE/ratingdata/users/{userId}")
                                        .build();
        UserRating rating = null;
        try {
            rating = restTemplate.getForObject(uriComponents.expand(userId).toUri(), UserRating.class);
            /*rating = webClientBuilder.build()
                            .get()
                            .uri(uriComponents.expand(userId).toUri())
                            .retrieve()
                            .bodyToMono(Rating.class)
                            .block();*/

            log.info("[method=getRating] [userId={}] [responseBody={}]",
                    userId,
                    JsonUtil.toJsonString(rating));
        } catch(RestClientException e) {
            log.error("[method=getRating] [errorMessage={}]", e.getMessage());
        }
        return rating;
    }

    public UserRating getFallbackRatings(String userId) {
        return new UserRating(userId, Arrays.asList(
                new Rating("0", 0)
        ));
    }

    public Mono<UserRating> getMonoRatings(String userId) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("http://RATING-DATA-SERVICE/ratingdata/users/{userId}")
                .build();
        Mono<UserRating> rating = null;
        try {
            rating = webClientBuilder.build()
                            .get()
                            .uri(uriComponents.expand(userId).toUri())
                            .retrieve()
                            .bodyToMono(UserRating.class);

            log.info("[method=getRating] [userId={}] [responseBody={}]",
                    userId,
                    JsonUtil.toJsonString(rating));
        } catch(RestClientException e) {
            log.error("[method=getRating] [errorMessage={}]", e.getMessage());
        }
        return rating;
    }
}
