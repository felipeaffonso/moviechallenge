package br.com.fza.moviechallenge.controller.transformer;

import br.com.fza.moviechallenge.BaseTest;
import br.com.fza.moviechallenge.controller.request.MovieRequest;
import br.com.fza.moviechallenge.exception.CouldNotTransformException;
import br.com.fza.moviechallenge.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MovieRequestTransformerTest extends BaseTest {

    @InjectMocks
    private MovieRequestTransformer target;

    @Test
    public void transformMustWorksProperly() {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("validCandidate");
        final Movie expectedMovie = from(Movie.class).gimme("validCandidate");

        final Movie result = target.transform(movieRequest);

        assertThat(result).isEqualToComparingFieldByField(expectedMovie);
    }

    @Test(expected = CouldNotTransformException.class)
    public void transformMustThrowExceptionWithNullParameter() {
        target.transform(null);
    }
}
