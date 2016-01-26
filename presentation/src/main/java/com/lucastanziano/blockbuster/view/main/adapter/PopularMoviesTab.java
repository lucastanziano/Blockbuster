package com.lucastanziano.blockbuster.view.main.adapter;

import android.content.res.Resources;

import com.lucastanziano.blockbuster.Movie;
import com.lucastanziano.blockbuster.internal.di.components.MovieGalleryComponent;
import com.lucastanziano.blockbuster.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.repository.MovieRepository;

import javax.inject.Inject;

import lucastanziano.blockbuster.R;
import rx.Observable;

/**
 * Created by Luca on 25/01/2016.
 */
public class PopularMoviesTab implements IMovieTab{

    @Inject
    MovieDataRepository repo;

    private int currentPage = 1;

    public PopularMoviesTab(){
        DaggerMovieGalleryComponent.
    }

    @Override
    public String getTitle() {
        return Resources.getSystem().getString(R.string.popular);
    }

    @Override
    public Observable<Movie> getMovies() {
        return repo.getPopularMovies(currentPage++);
    }

    public void setCurrentPage(int page){
        this.currentPage = page;
    }
}
