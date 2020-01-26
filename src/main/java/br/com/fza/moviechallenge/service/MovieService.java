package br.com.fza.moviechallenge.service;

import br.com.fza.moviechallenge.model.CensureLevel;
import br.com.fza.moviechallenge.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    Movie createMovie(final Movie movie);

    Page<Movie> findAllByCensureLevel(CensureLevel censureLevel, Pageable pageRequest);
}
