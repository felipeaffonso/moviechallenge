package br.com.fza.moviechallenge;

import org.junit.BeforeClass;

import static br.com.fza.moviechallenge.templates.Templates.BASE_PACKAGE;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;

public abstract class BaseTest {

    @BeforeClass
    public static void setUpClass() {
        loadTemplates(BASE_PACKAGE);
    }
}
