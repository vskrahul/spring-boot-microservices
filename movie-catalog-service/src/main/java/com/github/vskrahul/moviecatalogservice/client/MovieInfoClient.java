package com.github.vskrahul.moviecatalogservice.client;

import com.github.vskrahul.moviecatalogservice.model.Movie;
import com.github.vskrahul.moviecatalogservice.util.JsonUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Repository
public class MovieInfoClient {

    private RestTemplate restTemplate;

    @Autowired
    public MovieInfoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackMovie")
    public Movie getMovie(String movieId) {
        UriComponents uriComponents = UriComponentsBuilder
                                            .fromUriString("http://MOVIE-INFO-SERVICE/movies/{movieId}")
                                            .build();
        Movie movie = null;
        try {
            movie = this.restTemplate.getForObject(uriComponents.expand(movieId).toUri(), Movie.class);
            log.info("[method=getMovie] [movieId={}] [responseBody={}]",
                    movieId,
                    JsonUtil.toJsonString(movie));
        } catch(RestClientException e) {
            log.error("[method=getMovie] [movieId={}] [errorMessage={}]",
                    movieId,
                    e.getMessage());
        }

        return movie;
    }

    public Movie getFallbackMovie(String movieId) {
        return new Movie(movieId, "No Movie", "No Movie");
    }
}
