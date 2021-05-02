package com.github.vskrahul.movieinfoservice.controller;

import com.github.vskrahul.movieinfoservice.models.Movie;
import com.github.vskrahul.movieinfoservice.models.MovieSummary;
import com.github.vskrahul.movieinfoservice.service.MovieInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/movies")
public class MovieInfoController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private MovieInfoService movieInfoService;

    @GetMapping("/{movieId}")
    @ApiOperation(value = "fetch movie details by movie id"
                    ,notes = "provide movie id to fetch movie details"
                    ,response = Movie.class)
    public Movie getMovieInfo(
            @ApiParam(value = "movie id value for the movie you want to retrieve", required = true)
                    @PathVariable String movieId) {
        MovieSummary movieSummary = this.movieInfoService.movieInfo(movieId);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

}
