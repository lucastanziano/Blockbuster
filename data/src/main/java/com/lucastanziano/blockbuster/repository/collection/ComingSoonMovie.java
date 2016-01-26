package com.lucastanziano.blockbuster.repository.collection;

import com.lucastanziano.blockbuster.entity.MoviesWrapper;
import com.lucastanziano.blockbuster.net.TMDBService;

import rx.Observable;

/**
 * Created by Luca on 24/01/2016.
 */
public class ComingSoonMovie implements  IMovieCollection{

    private TMDBService rest;
    public  ComingSoonMovie(TMDBService rest){
        this.rest = rest;
    }

    @Override
    public Observable<MoviesWrapper> getMovies(int page) {
        return rest.getUpcomingMovies(Integer.toString(page));
    }
}
