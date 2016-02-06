package com.lucastanziano.blockbuster.domain.repository;


import com.lucastanziano.blockbuster.domain.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by Luca on 24/01/2016.
 */
public interface MovieRepository {

    Observable<Movie> getMovies();

    Observable<List<Movie>> getMovie(final String id);
}
