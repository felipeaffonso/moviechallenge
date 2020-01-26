package br.com.fza.moviechallenge.templates;

import br.com.fza.moviechallenge.controller.request.MovieRequest;
import br.com.fza.moviechallenge.model.CensureLevel;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static br.com.six2six.fixturefactory.Fixture.of;

public class MovieRequestTemplate implements TemplateLoader {

    @Override
    public void load() {
        of(MovieRequest.class).addTemplate("validCandidate", new Rule() {{
            add("name", "Titanic Candidate");
            add("launchDate", LocalDate.now().minusYears(10));
            add("censureLevel", CensureLevel.CENSURADO);
            add("director", "Movie Director");
            add("cast", List.of("Leonardo di Caprio", "Kate Winstlet"));
        }});

        of(MovieRequest.class).addTemplate("emptyName").inherits("validCandidate", new Rule() {{
            add("name", "");
        }});

        of(MovieRequest.class).addTemplate("nullName").inherits("validCandidate", new Rule() {{
            add("name", null);
        }});

        of(MovieRequest.class).addTemplate("nullLaunchDate").inherits("validCandidate", new Rule() {{
            add("launchDate", null);
        }});

        of(MovieRequest.class).addTemplate("nullCensureLevel").inherits("validCandidate", new Rule() {{
            add("censureLevel", null);
        }});

        of(MovieRequest.class).addTemplate("emptyDirector").inherits("validCandidate", new Rule() {{
            add("director", "");
        }});

        of(MovieRequest.class).addTemplate("nullDirector").inherits("validCandidate", new Rule() {{
            add("director", null);
        }});

        of(MovieRequest.class).addTemplate("emptyCast").inherits("validCandidate", new Rule() {{
            add("cast", List.of());
        }});

        of(MovieRequest.class).addTemplate("nullCast").inherits("validCandidate", new Rule() {{
            add("cast", null);
        }});

        of(MovieRequest.class).addTemplate("invalidCast").inherits("validCandidate", new Rule() {{
            add("cast", IntStream.range(1, 12).boxed().map(i -> "Actor " + i).collect(Collectors.toList()));
        }});

    }

}
