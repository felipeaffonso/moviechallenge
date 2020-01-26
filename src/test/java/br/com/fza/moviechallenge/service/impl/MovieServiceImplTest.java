package br.com.fza.moviechallenge.service.impl;

import br.com.fza.moviechallenge.BaseTest;
import br.com.fza.moviechallenge.exception.CouldNotCreateMovieException;
import br.com.fza.moviechallenge.exception.CouldNotFindMoviesException;
import br.com.fza.moviechallenge.exception.DuplicatedMovieException;
import br.com.fza.moviechallenge.model.CensureLevel;
import br.com.fza.moviechallenge.model.Movie;
import br.com.fza.moviechallenge.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest extends BaseTest {

    public static final String VALID_CANDIDATE = "validCandidate";
    public static final String PERSISTED_CENSURADO = "persistedCensurado";
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl target;

    @Test
    public void createMovieMustWorksProperlyWithValidMovieCandidate() {
        final Movie movieCandidate = from(Movie.class).gimme(VALID_CANDIDATE);
        final Movie persistedMovie = from(Movie.class).gimme(PERSISTED_CENSURADO);

        when(this.movieRepository.existsByName(movieCandidate.getName()))
                .thenReturn(Boolean.FALSE);

        when(this.movieRepository.save(movieCandidate))
                .thenReturn(persistedMovie);

        final Movie result = this.target.createMovie(movieCandidate);

        assertThat(result).isEqualToIgnoringGivenFields(persistedMovie,"id");
        assertThat(result.getId()).isNotNull();
    }

    @Test(expected = DuplicatedMovieException.class)
    public void createMovieThrowExceptionWhenMovieCandidateNameExists() {
        final Movie movieCandidate = from(Movie.class).gimme(VALID_CANDIDATE);

        when(this.movieRepository.existsByName(movieCandidate.getName()))
                .thenReturn(Boolean.TRUE);

        this.target.createMovie(movieCandidate);
    }

    @Test(expected = CouldNotCreateMovieException.class)
    public void createMovieThrowExceptionWhenRepositoryExistsByNameThrowsException() {
        final Movie movieCandidate = from(Movie.class).gimme(VALID_CANDIDATE);

        final Throwable throwable = new RuntimeException("Some Runtime Exception");

        when(this.movieRepository.existsByName(movieCandidate.getName()))
                .thenThrow(throwable);

        this.target.createMovie(movieCandidate);
    }

    @Test(expected = CouldNotCreateMovieException.class)
    public void createMovieThrowExceptionWhenRepositorySaveThrowsException() {
        final Movie movieCandidate = from(Movie.class).gimme(VALID_CANDIDATE);

        when(this.movieRepository.existsByName(movieCandidate.getName()))
                .thenReturn(Boolean.FALSE);

        final Throwable throwable = new RuntimeException("Some Runtime Exception Saving Movie");

        when(this.movieRepository.save(movieCandidate))
                .thenThrow(throwable);

        this.target.createMovie(movieCandidate);
    }

    @Test
    public void findAllByCensureLevelMustReturnResultUnPaged() {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = Pageable.unpaged();
        final List<Movie> movieList = from(Movie.class).gimme(2, PERSISTED_CENSURADO, PERSISTED_CENSURADO);

        when(this.movieRepository.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movieList));

        final Page<Movie> result = this.target.findAllByCensureLevel(censureLevel, pageable);

        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsAll(movieList);
    }

    @Test
    public void findAllByCensureLevelMustReturnFirstPageOfOnePages() {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = PageRequest.of(0, 1);
        final List<Movie> movieList = from(Movie.class).gimme(1, PERSISTED_CENSURADO);

        when(this.movieRepository.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movieList));

        final Page<Movie> result = this.target.findAllByCensureLevel(censureLevel, pageable);

        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).containsAll(movieList);
    }

    @Test
    public void findAllByCensureLevelMustReturnFirstPageOfTwoPages() {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = PageRequest.of(0, 1);
        final List<Movie> movieList = from(Movie.class).gimme(1, PERSISTED_CENSURADO);

        when(this.movieRepository.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movieList, pageable, 2));

        final Page<Movie> result = this.target.findAllByCensureLevel(censureLevel, pageable);

        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isFalse();
        assertThat(result.hasNext()).isTrue();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).contains(movieList.get(0));
    }

    @Test
    public void findAllByCensureLevelMustReturnLastPageOfTwoPages() {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = PageRequest.of(1, 1);
        final List<Movie> movieList = from(Movie.class).gimme(1, PERSISTED_CENSURADO);

        when(this.movieRepository.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movieList, pageable, 2));

        final Page<Movie> result = this.target.findAllByCensureLevel(censureLevel, pageable);

        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.isFirst()).isFalse();
        assertThat(result.isLast()).isTrue();
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).contains(movieList.get(0));
    }

    @Test
    public void findAllByCensureLevelMustReturnLastEmptyPage() {
        final CensureLevel censureLevel = CensureLevel.SEM_CENSURA;
        final Pageable pageable = PageRequest.of(0, 10);
        final List<Movie> movieList = List.of();

        when(this.movieRepository.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movieList));

        final Page<Movie> result = this.target.findAllByCensureLevel(censureLevel, pageable);

        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getContent()).hasSize(0);
    }

    @Test(expected = CouldNotFindMoviesException.class)
    public void findAllByCensureLevelMustThrowExceptionWhenAnErrorOccurs() {
        final CensureLevel censureLevel = CensureLevel.SEM_CENSURA;
        final Pageable pageable = PageRequest.of(0, 10);

        final Throwable throwable = new RuntimeException("Some Runtime Exception Finding Movies");

        when(this.movieRepository.findAllByCensureLevel(censureLevel, pageable))
                .thenThrow(throwable);

        this.target.findAllByCensureLevel(censureLevel, pageable);
    }
}
