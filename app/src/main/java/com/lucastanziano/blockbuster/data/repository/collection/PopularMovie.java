package com.lucastanziano.blockbuster.data.repository.collection;

import com.lucastanziano.blockbuster.data.entity.MoviesWrapper;
import com.lucastanziano.blockbuster.data.net.TMDBService;

import rx.Observable;

/**
 * Created by Luca on 24/01/2016.
 */
public class PopularMovie  implements  IMovieCollection{

    private TMDBService rest;
    public  PopularMovie(TMDBService rest){
        this.rest = rest;
    }

    @Override
    public Observable<MoviesWrapper> getMovies(int page) {
        return rest.getPopularMovies(Integer.toString(page));
    }
}