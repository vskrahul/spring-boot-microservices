package com.github.vskrahul.moviecatalogservice.controller;

import com.github.vskrahul.moviecatalogservice.model.UserRating;
import com.github.vskrahul.moviecatalogservice.service.MovieCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive")
public class ReactiveController {

    private MovieCatalogService movieCatalogService;

    @Autowired
    public ReactiveController(MovieCatalogService movieCatalogService) {
        this.movieCatalogService = movieCatalogService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "{userId}")
    public Mono<UserRating> getCatalogs(@PathVariable("userId") String userId) {
        return this.movieCatalogService.monoRatings(userId);
    }
}
