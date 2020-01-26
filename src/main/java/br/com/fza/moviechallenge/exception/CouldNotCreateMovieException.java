package br.com.fza.moviechallenge.exception;

public class CouldNotCreateMovieException extends RuntimeException {

    public CouldNotCreateMovieException(final String message, Throwable cause) {
        super(message, cause);
    }

}
