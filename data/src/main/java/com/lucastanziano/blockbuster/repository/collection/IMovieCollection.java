package com.lucastanziano.blockbuster.repository.collection;

import com.lucastanziano.blockbuster.entity.MoviesWrapper;

import rx.Observable;

/**
 * Created by Luca on 24/01/2016.
 */
public interface IMovieCollection {

    Observable<MoviesWrapper>  getMovies(int page) ;
}
