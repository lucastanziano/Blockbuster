package lucastanziano.blockbuster.wires;

import android.support.v7.widget.SearchView;
import android.widget.Toast;

import common.Movie;
import common.MoviesWrapper;
import lucastanziano.blockbuster.Repository;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import tmdb.RestMovieSource;

/**
 * Created by Luca on 24/11/2015.
 */
public class SearchWire {


    RestMovieSource repo;
    Subscriber<MoviesWrapper> newMovieSubscriber;

    public static void register(final SearchView searchView) {
        createSearchViewObservable(searchView)
                .concatMap(new Func1<String, Observable<MoviesWrapper>>() {
                    @Override
                    public Observable<MoviesWrapper> call(String title) {
                        return Repository.INSTANCE.getMovieRepository().searchMovieByTitle(title)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MoviesWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(searchView.getContext(), e.getCause().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(MoviesWrapper moviesWrapper) {
                        for (Movie movie : moviesWrapper.getResults()) {
                            Toast.makeText(searchView.getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    public static Observable<String> createSearchViewObservable(final SearchView searchView) {

        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(final Subscriber<? super String> subscriber) {
                SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        subscriber.onNext(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                };
                searchView.setOnQueryTextListener(listener);
            }
        });
    }

}
