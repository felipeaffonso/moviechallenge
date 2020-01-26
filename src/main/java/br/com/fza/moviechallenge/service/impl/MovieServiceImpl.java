package br.com.fza.moviechallenge.service.impl;

import br.com.fza.moviechallenge.exception.CouldNotCreateMovieException;
import br.com.fza.moviechallenge.exception.CouldNotFindMoviesException;
import br.com.fza.moviechallenge.exception.DuplicatedMovieException;
import br.com.fza.moviechallenge.model.CensureLevel;
import br.com.fza.moviechallenge.model.Movie;
import br.com.fza.moviechallenge.repository.MovieRepository;
import br.com.fza.moviechallenge.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie createMovie(final Movie movie) {
        log.info("Trying to create a new movie: {}", movie.getName());
        try {
            if (movieRepository.existsByName(movie.getName())) {
                throw new DuplicatedMovieException(movie.getName());
            }
            return this.movieRepository.save(movie);
        } catch (final DuplicatedMovieException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (final Exception e) {
            log.error("Unexpected error creating the movie", e);
            throw new CouldNotCreateMovieException(e.getMessage(), e);
        }

    }

    @Override
    public Page<Movie> findAllByCensureLevel(final CensureLevel censureLevel, final Pageable pageRequest) {
        log.info("Trying to find all movies by censure level: {}", censureLevel);
        try {
            return this.movieRepository.findAllByCensureLevel(censureLevel, pageRequest);
        } catch (final Exception e) {
            log.error("Unexpected error finding movies by censure level: " + censureLevel, e);
            throw new CouldNotFindMoviesException(e.getMessage(), e);
        }
    }
}
