package com.lucastanziano.blockbuster.tmdb.repository;


import com.lucastanziano.blockbuster.tmdb.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by Luca on 24/01/2016.
 */
public interface MovieRepository {

    Observable<Movie> getMovies();

    Observable<List<Movie>> getMovie(final String id);
}
