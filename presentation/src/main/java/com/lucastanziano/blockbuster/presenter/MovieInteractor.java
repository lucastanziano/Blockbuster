package com.lucastanziano.blockbuster.presenter;

import android.content.Context;
import android.databinding.ObservableList;
import android.util.Log;
import android.widget.Toast;

import com.lucastanziano.blockbuster.domain.Movie;
import com.lucastanziano.blockbuster.model.MovieItem;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Luca on 30/01/2016.
 */
public class MovieInteractor {


    public static void loadMovieItems(final Context context, Observable<Movie> source, final ObservableList<MovieItem> destination) {
        source.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<Movie, Observable<MovieItem>>() {
                    @Override
                    public Observable<MovieItem> call(Movie movie) {
                        Log.d("MOVIEINTERACTOR", "Observable<GalleryItem> call(Movie movie)");
                        MovieItem item = new MovieItem();
                        item.title = movie.getTitle();
                        item.backgroundImgURL = movie.getPoster_path();
                        item.id = movie.getId();
                        return Observable.just(item);
                    }
                })
                .subscribe(new Subscriber<MovieItem>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MOVIEINTERACTOR", "Subscriber<GalleryItem> onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                        Toast.makeText(context, e.getCause().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(MovieItem item) {
                        destination.add(item);
                    }
                });
    }

}
