package com.github.vskrahul.moviecatalogservice.service;

import com.github.vskrahul.moviecatalogservice.client.MovieInfoClient;
import com.github.vskrahul.moviecatalogservice.client.MovieRatingClient;
import com.github.vskrahul.moviecatalogservice.model.CatalogItem;
import com.github.vskrahul.moviecatalogservice.model.Movie;
import com.github.vskrahul.moviecatalogservice.model.Rating;
import com.github.vskrahul.moviecatalogservice.model.UserRating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieCatalogService {

    private MovieInfoClient movieInfoClient;
    private MovieRatingClient movieRatingClient;

    @Autowired
    public MovieCatalogService(MovieInfoClient movieInfoClient, MovieRatingClient movieRatingClient) {
        this.movieInfoClient = movieInfoClient;
        this.movieRatingClient = movieRatingClient;
    }

    public List<CatalogItem> catalogItems(String userId) {

        UserRating ratings = this.movieRatingClient.getRatings(userId);

        List<CatalogItem> catalogs = ratings.getRatings().stream().map(r -> {
            Movie m = this.movieInfoClient.getMovie(r.getMovieId());
            return new CatalogItem(m.getName(), m.getDescription(), r.getRating());
        }).collect(Collectors.toList());

        return catalogs;
    }

    public Mono<UserRating> monoRatings(String userId) {
        return this.movieRatingClient.getMonoRatings(userId);
    }
}
