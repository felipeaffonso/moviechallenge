package br.com.fza.moviechallenge.controller.response;

import br.com.fza.moviechallenge.model.CensureLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse implements Serializable {

    private String id;

    private String name;

    private LocalDate launchDate;

    private CensureLevel censureLevel;

    private String director;

    private List<String> cast;

    private Long version;
}
