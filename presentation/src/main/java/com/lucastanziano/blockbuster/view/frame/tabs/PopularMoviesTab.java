package com.lucastanziano.blockbuster.view.frame.tabs;

import com.lucastanziano.blockbuster.Movie;
import com.lucastanziano.blockbuster.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.view.frame.adapter.IMovieProvider;

import lucastanziano.blockbuster.R;
import rx.Observable;

/**
 * Created by Luca on 25/01/2016.
 */
public class PopularMoviesTab extends BaseMovieTab{

    private IMovieProvider movieProvider;

    public PopularMoviesTab(MovieDataRepository repo){
        super(repo);
    }

    public static IMovieTab newInstance(MovieDataRepository movieRepository){
        return new PopularMoviesTab(movieRepository);
    }

    @Override
    public int getTitleResId() {
        return R.string.popular;
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
                    return getRepository().getPopularMovies(page);
                }
            };
        }
        return movieProvider;
    }


}
