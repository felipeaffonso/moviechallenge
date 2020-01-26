package br.com.fza.moviechallenge.templates;

import br.com.fza.moviechallenge.model.CensureLevel;
import br.com.fza.moviechallenge.model.Movie;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;
import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.of;

public class MovieTemplate implements TemplateLoader {

    @Override
    public void load() {
        of(Movie.class).addTemplate("validCandidate", new Rule() {{
            add("name", "Titanic Candidate");
            add("launchDate", LocalDate.now().minusYears(10));
            add("censureLevel", CensureLevel.CENSURADO);
            add("director", "Movie Director");
            add("cast", List.of("Leonardo di Caprio", "Kate Winstlet"));
        }});

        of(Movie.class).addTemplate("persistedCensurado", new Rule() {{
            add("id", "5e2cc9e6ec7aef3c7d38a84c");
            add("name", "Titanic Censurado");
            add("launchDate", LocalDate.now().minusYears(10));
            add("censureLevel", CensureLevel.CENSURADO);
            add("director", "Movie Director");
            add("cast", List.of("Leonardo di Caprio", "Kate Winstlet"));
        }});

        of(Movie.class).addTemplate("persistedSemCensura", new Rule() {{
            add("id", "5e2cc9e6ec7aef3c7d38a84d");
            add("name", "Titanic Sem Censura");
            add("launchDate", LocalDate.now().minusYears(10));
            add("censureLevel", CensureLevel.SEM_CENSURA);
            add("director", "Movie Director");
            add("cast", List.of("Leonardo di Caprio", "Kate Winstlet"));
        }});
    }

}
