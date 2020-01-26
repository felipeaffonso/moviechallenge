package br.com.fza.moviechallenge.exception;

public class DuplicatedMovieException extends RuntimeException {

    public DuplicatedMovieException(final String movieName) {
        super("You cant create a new movie with name '" + movieName + "' because it already exists.");
    }
}
