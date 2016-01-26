package com.lucastanziano.blockbuster.repository;

import com.lucastanziano.blockbuster.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by Luca on 24/01/2016.
 */
public interface MovieRepository {

    Observable<Movie> getMovies();

    Observable<List<Movie>> getMovie(final String id);
}
