package com.lucastanziano.blockbuster.data.repository.collection;

import com.lucastanziano.blockbuster.data.entity.MoviesWrapper;

import rx.Observable;

/**
 * Created by Luca on 24/01/2016.
 */
public interface IMovieCollection {

    Observable<MoviesWrapper>  getMovies(int page) ;
}
