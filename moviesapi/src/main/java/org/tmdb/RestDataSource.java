package org.tmdb;


import common.MediaDataSource;
import common.MoviesWrapper;
import rx.Observable;

/**
 * Created by saulmm on 25/02/15.
 */
public interface RestDataSource extends MediaDataSource {

    public Observable<MoviesWrapper> getMoviesByPage(int page);
}
