package br.com.fza.moviechallenge.model;

@FunctionalInterface
public interface Transformer<F, T> {
    T transform(F f);
}
