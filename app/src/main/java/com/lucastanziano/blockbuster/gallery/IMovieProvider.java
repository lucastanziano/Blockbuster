package com.lucastanziano.blockbuster.gallery;

import com.lucastanziano.blockbuster.tmdb.Movie;

import rx.Observable;

/**
 * Created by Luca on 30/01/2016.
 */
public interface IMovieProvider {
    Observable<Movie> getMoreMovies();

    Observable<Movie> getMovies(int page);
}
