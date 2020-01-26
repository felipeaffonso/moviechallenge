package br.com.fza.moviechallenge.controller.transformer;

import br.com.fza.moviechallenge.BaseTest;
import br.com.fza.moviechallenge.controller.response.MovieResponse;
import br.com.fza.moviechallenge.exception.CouldNotTransformException;
import br.com.fza.moviechallenge.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MovieTransformerTest extends BaseTest {

    @InjectMocks
    private MovieTransformer target;

    @Test
    public void transformMustWorksProperly() {
        final Movie movie = from(Movie.class).gimme("persistedCensurado");
        final MovieResponse expectedMovieResponse = from(MovieResponse.class).gimme("persistedCensurado");

        final MovieResponse result = target.transform(movie);

        assertThat(result).isEqualToComparingFieldByField(expectedMovieResponse);
    }

    @Test(expected = CouldNotTransformException.class)
    public void transformMustThrowExceptionWithNullParameter() {
        target.transform(null);
    }
}
