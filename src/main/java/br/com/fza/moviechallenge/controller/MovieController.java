package br.com.fza.moviechallenge.controller;

import br.com.fza.moviechallenge.controller.request.MovieRequest;
import br.com.fza.moviechallenge.controller.response.MovieResponse;
import br.com.fza.moviechallenge.controller.transformer.MovieRequestTransformer;
import br.com.fza.moviechallenge.controller.transformer.MovieTransformer;
import br.com.fza.moviechallenge.exception.DuplicatedMovieException;
import br.com.fza.moviechallenge.model.CensureLevel;
import br.com.fza.moviechallenge.model.Movie;
import br.com.fza.moviechallenge.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
@Api(value = "/movies", protocols = "http", tags = "movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieRequestTransformer movieRequestTransformer;
    private final MovieTransformer movieTransformer;

    @ApiOperation(value = "Find All Movies")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<MovieResponse> findAllByCensureLevel(final @RequestParam CensureLevel censureLevel,
                                                     final @RequestParam(required = false, defaultValue = "0") int page,
                                                     final @RequestParam(required = false, defaultValue = "0") int size) {
        log.info("Finding All Movies by Censure Level: {}", censureLevel);
        try {
            final Pageable pageRequest = page == 0 || size == 0
                    ? Pageable.unpaged()
                    : PageRequest.of(page - 1, size);
            final Page<Movie> moviesPage = this.movieService.findAllByCensureLevel(censureLevel, pageRequest);
            return moviesPage.map(this.movieTransformer::transform);
        } catch(final Exception e) {
            final String errorMessage = "Error finding all movies";
            log.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e);
        }
    }

    @ApiOperation(value = "Create a Movie")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponse createMovie(final @RequestBody @Valid MovieRequest movieRequest) {
        log.info("Creating a new Movie: {}", movieRequest);
        try {
            final Movie movieCandidate = this.movieRequestTransformer.transform(movieRequest);
            final Movie persistedMovie = this.movieService.createMovie(movieCandidate);
            final MovieResponse movieResponse = this.movieTransformer.transform(persistedMovie);
            log.info("Movie {} created with id: {}", persistedMovie.getName(), persistedMovie.getId());
            return movieResponse;
        } catch(final DuplicatedMovieException de) {
            log.error(de.getMessage(), de);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, de.getMessage(), de);
        } catch(final Exception e) {
            final String errorMessage = "Error creating the movie: " + movieRequest.toString();
            log.error(errorMessage, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, e);
        }
    }

}
