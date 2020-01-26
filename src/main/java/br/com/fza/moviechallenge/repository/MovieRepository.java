package br.com.fza.moviechallenge.repository;

import br.com.fza.moviechallenge.model.CensureLevel;
import br.com.fza.moviechallenge.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MovieRepository extends MongoRepository<Movie, String> {

    boolean existsByName(String name);

    Page<Movie> findAllByCensureLevel(CensureLevel censureLevel, Pageable pageable);
}
