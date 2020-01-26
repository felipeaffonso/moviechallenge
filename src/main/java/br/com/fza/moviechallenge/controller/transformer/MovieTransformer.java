package br.com.fza.moviechallenge.controller.transformer;

import br.com.fza.moviechallenge.controller.response.MovieResponse;
import br.com.fza.moviechallenge.exception.CouldNotTransformException;
import br.com.fza.moviechallenge.model.Movie;
import br.com.fza.moviechallenge.model.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovieTransformer implements Transformer<Movie, MovieResponse> {
    @Override
    public MovieResponse transform(final Movie movie) {
        try {
            return MovieResponse.builder()
                    .id(movie.getId())
                    .name(movie.getName())
                    .censureLevel(movie.getCensureLevel())
                    .launchDate(movie.getLaunchDate())
                    .director(movie.getDirector())
                    .cast(movie.getCast())
                    .build();
        } catch(final Exception e) {
            throw new CouldNotTransformException("Could not Convert Movie to MovieResponse", e);
        }
    }
}
