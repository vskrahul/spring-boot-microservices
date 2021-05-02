package com.github.vskrahul.moviecatalogservice.service;

import java.util.List;
import java.util.stream.Collectors;

import com.github.vskrahul.moviecatalogservice.model.CatalogItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.vskrahul.moviecatalogservice.client.MovieInfoClient;
import com.github.vskrahul.moviecatalogservice.client.MovieRatingClient;
import com.github.vskrahul.moviecatalogservice.model.CatalogItem;
import com.github.vskrahul.moviecatalogservice.model.Movie;
import com.github.vskrahul.moviecatalogservice.model.UserRating;
import com.github.vskrahul.moviecatalogservice.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MovieCatalogService {

    private MovieInfoClient movieInfoClient;
    private MovieRatingClient movieRatingClient;
    
    @Autowired
    public MovieCatalogService(MovieInfoClient movieInfoClient, MovieRatingClient movieRatingClient) {
        this.movieInfoClient = movieInfoClient;
        this.movieRatingClient = movieRatingClient;
        String v = System.getProperty("sun.net.client.defaultConnectTimeout");
    }

    public CatalogItems catalogItems(String userId) {

        CatalogItems items = new CatalogItems();
        UserRating ratings = this.movieRatingClient.getRatings(userId);

        log.info("[method=catalogItems] [responseBody={}]", JsonUtil.toJsonString(ratings));
        
        List<CatalogItem> catalogs = ratings.getRatings().stream().map(r -> {
            Movie m = this.movieInfoClient.getMovie(r.getMovieId());
            return new CatalogItem(m.getName(), m.getDescription(), r.getRating());
        }).collect(Collectors.toList());
        items.setCatalogItems(catalogs);
        return items;
    }

    public Mono<UserRating> monoRatings(String userId) {
        return this.movieRatingClient.getMonoRatings(userId);
    }
}
