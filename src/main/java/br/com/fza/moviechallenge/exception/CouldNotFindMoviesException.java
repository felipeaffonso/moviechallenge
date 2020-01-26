package br.com.fza.moviechallenge.exception;

public class CouldNotFindMoviesException extends RuntimeException {

    public CouldNotFindMoviesException(final String message, Throwable cause) {
        super(message, cause);
    }

}
