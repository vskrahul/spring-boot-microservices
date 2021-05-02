package com.github.vskrahul.moviecatalogservice.controller;

import com.github.vskrahul.moviecatalogservice.model.CatalogItems;
import com.github.vskrahul.moviecatalogservice.service.MovieCatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("catalog")
public class MovieCatalogController {

    private MovieCatalogService movieCatalogService;

    @Autowired
    public MovieCatalogController(MovieCatalogService movieCatalogService) {
        this.movieCatalogService = movieCatalogService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "{userId}")
    @ApiOperation(value = "movie catalogs for give user"
            ,notes = "user userId to fetch all his/her movie catalogs"
            ,response = CatalogItems.class)
    public CatalogItems getCatalog(String userId) {
        return this.movieCatalogService.catalogItems(userId);
    }
}
