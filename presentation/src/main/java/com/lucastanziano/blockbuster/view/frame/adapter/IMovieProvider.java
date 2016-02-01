package com.lucastanziano.blockbuster.view.frame.adapter;

import com.lucastanziano.blockbuster.Movie;

import rx.Observable;

/**
 * Created by Luca on 30/01/2016.
 */
public interface IMovieProvider {
    Observable<Movie> getMoreMovies();

    Observable<Movie> getMovies(int page);
}
