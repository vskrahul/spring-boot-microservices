package com.github.vskrahul.moviecatalogservice.controller;

import com.github.vskrahul.moviecatalogservice.model.CatalogItem;
import com.github.vskrahul.moviecatalogservice.service.MovieCatalogService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("catalog")
public class MovieCatalogController {

    private MovieCatalogService movieCatalogService;

    @Autowired
    public MovieCatalogController(MovieCatalogService movieCatalogService) {
        this.movieCatalogService = movieCatalogService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        return this.movieCatalogService.catalogItems(userId);
    }
}
