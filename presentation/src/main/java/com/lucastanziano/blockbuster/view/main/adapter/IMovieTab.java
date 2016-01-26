package com.lucastanziano.blockbuster.view.main.adapter;

import com.lucastanziano.blockbuster.Movie;

import rx.Observable;

/**
 * Created by Luca on 25/01/2016.
 */
public interface IMovieTab {

    String getTitle();
    Observable<Movie> getMovies();

}
