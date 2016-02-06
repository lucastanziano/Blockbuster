package com.lucastanziano.blockbuster.data.entity;



import com.lucastanziano.blockbuster.domain.Movie;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Luca on 24/01/2016.
 */
@Singleton
public class MovieMapper implements Func1<MoviesWrapper, Observable<Movie>> {

    @Inject
    public MovieMapper(){}

    public static Observable<Movie> transform (MoviesWrapper moviesWrapper){
        return Observable.from(moviesWrapper.getResults());
    }

    @Override
    public Observable<Movie> call(MoviesWrapper moviesWrapper) {
        return MovieMapper.transform(moviesWrapper);
    }
}
