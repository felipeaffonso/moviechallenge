package br.com.fza.moviechallenge.controller;

import br.com.fza.moviechallenge.BaseTest;
import br.com.fza.moviechallenge.controller.request.MovieRequest;
import br.com.fza.moviechallenge.controller.response.MovieResponse;
import br.com.fza.moviechallenge.controller.transformer.MovieRequestTransformer;
import br.com.fza.moviechallenge.controller.transformer.MovieTransformer;
import br.com.fza.moviechallenge.exception.CouldNotFindMoviesException;
import br.com.fza.moviechallenge.exception.DuplicatedMovieException;
import br.com.fza.moviechallenge.model.CensureLevel;
import br.com.fza.moviechallenge.model.Movie;
import br.com.fza.moviechallenge.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
@AutoConfigureMockMvc
public class MovieControllerTest extends BaseTest {

    public static final String PERSISTED_CENSURADO = "persistedCensurado";
    public static final String MOVIES = "movies";
    public static final String CAST_MUST_HAVE_AT_LEAST_1_AND_AT_MOST_10_ACTORS = "Cast must have at least 1 and at most 10 actors.";
    public static final String VALID_CANDIDATE = "validCandidate";
    public static final String CONTENT = "$.content";
    public static final String TOTAL_ELEMENTS = "$.totalElements";
    public static final String FIRST = "$.first";
    public static final String LAST = "$.last";
    public static final String NUMBER = "$.number";
    public static final String SIZE = "$.size";
    public static final String NUMBER_OF_ELEMENTS = "$.numberOfElements";
    public static final String TOTAL_PAGES = "$.totalPages";
    public static final String CENSURE_LEVEL = "censureLevel";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;
    @MockBean
    private MovieRequestTransformer movieRequestTransformer;
    @MockBean
    private MovieTransformer movieTransformer;

    @Test
    public void findAllByCensureLevelMustReturnMoviesUnPaged() throws Exception {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = Pageable.unpaged();

        final List<Movie> movies = from(Movie.class).gimme(2, PERSISTED_CENSURADO);

        final MovieResponse movieResponse = from(MovieResponse.class).gimme(PERSISTED_CENSURADO);

        when(this.movieService.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movies, pageable, 2));

        when(this.movieTransformer.transform(any(Movie.class)))
                .thenReturn(movieResponse)
                .thenReturn(movieResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/" + MOVIES)
                        .param(CENSURE_LEVEL, censureLevel.toString())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath(CONTENT).value(hasSize(2)))
                .andExpect(jsonPath(TOTAL_ELEMENTS).value(2))
                .andExpect(jsonPath(FIRST).value(true))
                .andExpect(jsonPath(LAST).value(true))
                .andExpect(jsonPath(NUMBER).value(0))
                .andExpect(jsonPath(SIZE).value(2))
                .andExpect(jsonPath(NUMBER_OF_ELEMENTS).value(2))
                .andExpect(jsonPath(TOTAL_PAGES).value(1));
    }

    @Test
    public void findAllByCensureLevelMustReturnMoviesUnPagedWithPageZero() throws Exception {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = Pageable.unpaged();

        final List<Movie> movies = from(Movie.class).gimme(2, PERSISTED_CENSURADO);

        final MovieResponse movieResponse = from(MovieResponse.class).gimme(PERSISTED_CENSURADO);

        when(this.movieService.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movies, pageable, 2));

        when(this.movieTransformer.transform(any(Movie.class)))
                .thenReturn(movieResponse)
                .thenReturn(movieResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/" + MOVIES)
                        .param("page", "0")
                        .param("size", "10")
                        .param(CENSURE_LEVEL, censureLevel.toString())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath(CONTENT).value(hasSize(2)))
                .andExpect(jsonPath(TOTAL_ELEMENTS).value(2))
                .andExpect(jsonPath(FIRST).value(true))
                .andExpect(jsonPath(LAST).value(true))
                .andExpect(jsonPath(NUMBER).value(0))
                .andExpect(jsonPath(SIZE).value(2))
                .andExpect(jsonPath(NUMBER_OF_ELEMENTS).value(2))
                .andExpect(jsonPath(TOTAL_PAGES).value(1));
    }

    @Test
    public void findAllByCensureLevelMustReturnMoviesUnPagedWithSizeZero() throws Exception {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = Pageable.unpaged();

        final List<Movie> movies = from(Movie.class).gimme(2, PERSISTED_CENSURADO);

        final MovieResponse movieResponse = from(MovieResponse.class).gimme(PERSISTED_CENSURADO);

        when(this.movieService.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movies, pageable, 2));

        when(this.movieTransformer.transform(any(Movie.class)))
                .thenReturn(movieResponse)
                .thenReturn(movieResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/" + MOVIES)
                        .param("page", "10")
                        .param("size", "0")
                        .param(CENSURE_LEVEL, censureLevel.toString())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath(CONTENT).value(hasSize(2)))
                .andExpect(jsonPath(TOTAL_ELEMENTS).value(2))
                .andExpect(jsonPath(FIRST).value(true))
                .andExpect(jsonPath(LAST).value(true))
                .andExpect(jsonPath(NUMBER).value(0))
                .andExpect(jsonPath(SIZE).value(2))
                .andExpect(jsonPath(NUMBER_OF_ELEMENTS).value(2))
                .andExpect(jsonPath(TOTAL_PAGES).value(1));
    }

    @Test
    public void findAllByCensureLevelMustReturnMoviesFirstPageOfOnePage() throws Exception {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = PageRequest.of(0, 2);

        final List<Movie> movies = from(Movie.class).gimme(2, PERSISTED_CENSURADO);

        final MovieResponse movieResponse = from(MovieResponse.class).gimme(PERSISTED_CENSURADO);

        when(this.movieService.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movies, pageable, 2));

        when(this.movieTransformer.transform(any(Movie.class)))
                .thenReturn(movieResponse)
                .thenReturn(movieResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/" + MOVIES)
                        .param(CENSURE_LEVEL, censureLevel.toString())
                        .param("page", "1")
                        .param("size", "2")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath(CONTENT).value(hasSize(2)))
                .andExpect(jsonPath(TOTAL_ELEMENTS).value(2))
                .andExpect(jsonPath(FIRST).value(true))
                .andExpect(jsonPath(LAST).value(true))
                .andExpect(jsonPath(NUMBER).value(0))
                .andExpect(jsonPath(SIZE).value(2))
                .andExpect(jsonPath(NUMBER_OF_ELEMENTS).value(2))
                .andExpect(jsonPath(TOTAL_PAGES).value(1));
    }

    @Test
    public void findAllByCensureLevelMustReturnMoviesFirstPageOfTwoPages() throws Exception {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = PageRequest.of(0, 1);

        final List<Movie> movies = from(Movie.class).gimme(1, PERSISTED_CENSURADO);

        final MovieResponse movieResponse = from(MovieResponse.class).gimme(PERSISTED_CENSURADO);

        when(this.movieService.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movies, pageable, 2));

        when(this.movieTransformer.transform(any(Movie.class)))
                .thenReturn(movieResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/" + MOVIES)
                        .param(CENSURE_LEVEL, censureLevel.toString())
                        .param("page", "1")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath(CONTENT).value(hasSize(1)))
                .andExpect(jsonPath(TOTAL_ELEMENTS).value(2))
                .andExpect(jsonPath(FIRST).value(true))
                .andExpect(jsonPath(LAST).value(false))
                .andExpect(jsonPath(NUMBER).value(0))
                .andExpect(jsonPath(SIZE).value(1))
                .andExpect(jsonPath(NUMBER_OF_ELEMENTS).value(1))
                .andExpect(jsonPath(TOTAL_PAGES).value(2));
    }

    @Test
    public void findAllByCensureLevelMustReturnMoviesSecondPageOfTwoPages() throws Exception {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = PageRequest.of(1, 1);

        final List<Movie> movies = from(Movie.class).gimme(1, PERSISTED_CENSURADO);

        final MovieResponse movieResponse = from(MovieResponse.class).gimme(PERSISTED_CENSURADO);

        when(this.movieService.findAllByCensureLevel(censureLevel, pageable))
                .thenReturn(new PageImpl<>(movies, pageable, 2));

        when(this.movieTransformer.transform(any(Movie.class)))
                .thenReturn(movieResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/" + MOVIES)
                        .param(CENSURE_LEVEL, censureLevel.toString())
                        .param("page", "2")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath(CONTENT).value(hasSize(1)))
                .andExpect(jsonPath(TOTAL_ELEMENTS).value(2))
                .andExpect(jsonPath(FIRST).value(false))
                .andExpect(jsonPath(LAST).value(true))
                .andExpect(jsonPath(NUMBER).value(1))
                .andExpect(jsonPath(SIZE).value(1))
                .andExpect(jsonPath(NUMBER_OF_ELEMENTS).value(1))
                .andExpect(jsonPath(TOTAL_PAGES).value(2));
    }

    @Test
    public void findAllByCensureLevelMustReturnErrorMessageWhenAnExceptionOccurs() throws Exception {
        final CensureLevel censureLevel = CensureLevel.CENSURADO;
        final Pageable pageable = PageRequest.of(0, 10);

        final Throwable throwable = new CouldNotFindMoviesException("Could not find movies for some reason", null);

        when(this.movieService.findAllByCensureLevel(censureLevel, pageable))
                .thenThrow(throwable);

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/" + MOVIES)
                        .param(CENSURE_LEVEL, censureLevel.toString())
                        .param("page", "1")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

        final String errorMessage = mvcResult.getResponse().getErrorMessage();

        assertThat(errorMessage)
                .startsWith("Error finding all movies");
        verify(this.movieTransformer, never()).transform(any(Movie.class));
    }

    @Test
    public void createMovieMustWorksWithValidMovieCandidate() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(VALID_CANDIDATE);
        final Movie movieCandidate = from(Movie.class).gimme(VALID_CANDIDATE);
        final Movie persistedMovie = from(Movie.class).gimme(PERSISTED_CENSURADO);
        final MovieResponse movieResponse = from(MovieResponse.class).gimme(PERSISTED_CENSURADO);

        when(this.movieRequestTransformer.transform(movieRequest))
                .thenReturn(movieCandidate);

        when(this.movieService.createMovie(movieCandidate))
                .thenReturn(persistedMovie);

        when(this.movieTransformer.transform(persistedMovie))
                .thenReturn(movieResponse);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(persistedMovie.getId()));
    }

    @Test
    public void createMovieMustReturnBadRequestWithDuplicatedMovieName() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(VALID_CANDIDATE);
        final Movie movieCandidate = from(Movie.class).gimme(VALID_CANDIDATE);

        when(this.movieRequestTransformer.transform(movieRequest))
                .thenReturn(movieCandidate);

        final Throwable throwable = new DuplicatedMovieException(movieCandidate.getName());

        when(this.movieService.createMovie(movieCandidate))
                .thenThrow(throwable);

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        final String errorMessage = mvcResult.getResponse().getErrorMessage();

        assertThat(errorMessage)
                .isEqualTo("You cant create a new movie with name 'Titanic Candidate' because it already exists.");

        verify(this.movieTransformer, never()).transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnInternalServerErrorWhenUnexpectedExceptionOccurs() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(VALID_CANDIDATE);
        final Movie movieCandidate = from(Movie.class).gimme(VALID_CANDIDATE);

        when(this.movieRequestTransformer.transform(movieRequest))
                .thenReturn(movieCandidate);

        final Throwable throwable = new RuntimeException("Unexpected Exception Creating Movie");

        when(this.movieService.createMovie(movieCandidate))
                .thenThrow(throwable);

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
        final String errorMessage = mvcResult.getResponse().getErrorMessage();

        assertThat(errorMessage)
                .startsWith("Error creating the movie: ");

        verify(this.movieTransformer, never()).transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithEmptyMovieName() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("emptyName");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains("Movie name cannot be blank");

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithNullMovieName() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("nullName");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains("Movie name is required");

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithNullMovieLaunchDate() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("nullLaunchDate");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains("Movie launch date is required");

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithNullMovieCensureLevel() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("nullCensureLevel");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains("Movie Censure Level is Required. Allowed Values: CENSURADO, SEM_CENSURA");

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithEmptyMovieDirector() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("emptyDirector");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains("Movie director cannot be blank");

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithNullMovieDirector() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("nullDirector");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains("Movie director is required");

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithEmptyMovieCast() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("emptyCast");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains(CAST_MUST_HAVE_AT_LEAST_1_AND_AT_MOST_10_ACTORS);

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithNullMovieCast() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("nullCast");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains(CAST_MUST_HAVE_AT_LEAST_1_AND_AT_MOST_10_ACTORS);

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }

    @Test
    public void createMovieMustReturnBadRequestWithInvalidMovieCast() throws Exception {
        final MovieRequest movieRequest = from(MovieRequest.class).gimme("invalidCast");

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + MOVIES)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        final String errorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        assertThat(errorMessage)
                .contains(CAST_MUST_HAVE_AT_LEAST_1_AND_AT_MOST_10_ACTORS);

        verify(this.movieRequestTransformer, never())
                .transform(any(MovieRequest.class));

        verify(this.movieService, never())
                .createMovie(any(Movie.class));

        verify(this.movieTransformer, never())
                .transform(any(Movie.class));
    }
}
