package br.com.fza.moviechallenge.controller.transformer;

import br.com.fza.moviechallenge.controller.request.MovieRequest;
import br.com.fza.moviechallenge.exception.CouldNotTransformException;
import br.com.fza.moviechallenge.model.Movie;
import br.com.fza.moviechallenge.model.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovieRequestTransformer implements Transformer<MovieRequest, Movie> {
    @Override
    public Movie transform(final MovieRequest movieRequest) {
        try {
            return Movie.builder()
                    .name(movieRequest.getName())
                    .censureLevel(movieRequest.getCensureLevel())
                    .launchDate(movieRequest.getLaunchDate())
                    .director(movieRequest.getDirector())
                    .cast(movieRequest.getCast())
                    .build();
        } catch(final Exception e) {
            throw new CouldNotTransformException("Could not Convert MovieRequest to Movie", e);
        }
    }
}
