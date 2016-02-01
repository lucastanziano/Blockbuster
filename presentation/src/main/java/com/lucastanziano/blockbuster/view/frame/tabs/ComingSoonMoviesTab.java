package com.lucastanziano.blockbuster.view.frame.tabs;

import com.lucastanziano.blockbuster.Movie;
import com.lucastanziano.blockbuster.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.view.frame.adapter.IMovieProvider;

import lucastanziano.blockbuster.R;
import rx.Observable;

/**
 * Created by Luca on 30/01/2016.
 */
public class ComingSoonMoviesTab extends BaseMovieTab {


    private IMovieProvider movieProvider;

    public ComingSoonMoviesTab(MovieDataRepository repo){
        super(repo);
    }


    @Override
    public int getTitleResId() {
        return R.string.coming_soon;
    }


    @Override
    public IMovieProvider getMovieProvider() {
        if(movieProvider==null){
            return new IMovieProvider() {
                int page = 1;
                @Override
                public Observable<Movie> getMoreMovies() {
                    return getMovies(page);
                }

                @Override
                public Observable<Movie> getMovies(int page) {
                    return getRepository().getComingSoonMovies(page);
                }
            };
        }
        return movieProvider;
    }

}
