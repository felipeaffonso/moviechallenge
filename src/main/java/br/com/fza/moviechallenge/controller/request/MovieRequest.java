package br.com.fza.moviechallenge.controller.request;

import br.com.fza.moviechallenge.model.CensureLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest implements Serializable {

    @NotNull(message = "Movie name is required")
    @NotBlank(message = "Movie name cannot be blank")
    private String name;

    @NotNull(message = "Movie launch date is required")
    private LocalDate launchDate;

    @NotNull(message = "Movie Censure Level is Required. Allowed Values: CENSURADO, SEM_CENSURA")
    private CensureLevel censureLevel;

    @NotNull(message = "Movie director is required")
    @NotBlank(message = "Movie director cannot be blank")
    private String director;

    @NotNull(message = "Cast must have at least 1 and at most 10 actors.")
    @Size(min = 1, max = 10, message = "Cast must have at least {min} and at most {max} actors.")
    private List<String> cast;

}
