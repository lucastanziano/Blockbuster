package lucastanziano.blockbuster.interactor;

import android.content.Context;
import android.databinding.ObservableList;
import android.util.Log;
import android.widget.Toast;


import lucastanziano.blockbuster.data.MovieRepository;
import lucastanziano.blockbuster.model.Movie;
import lucastanziano.blockbuster.model.MoviesWrapper;
import lucastanziano.gallery.model.GalleryItem;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Luca on 30/12/2015.
 */
public class MoviesInteractor {


    MovieRepository repo;

    Context context;

    ObservableList<GalleryItem> destList;

    public MoviesInteractor(Context context, ObservableList<GalleryItem> destList) {
        this.context = context;
        this.repo = new MovieRepository(context);
        this.destList = destList;
    }

    public void loadPopularMovies() {
        Log.d("MOVIEINTERACTOR", "loadPopularMovies");
        destList.clear();
        loadMovies(repo.getPopularMovies());
    }

    public void searchMovieByTitle(String title) {
        destList.clear();
        Log.d("MOVIEINTERACTOR", "Searching movie by title " + title);
        loadMovies(repo.searchMovieByTitle(title));
    }

    public void loadMovies(final Observable<MoviesWrapper> observableMovies) {
        Log.d("MOVIEINTERACTOR", "loadMovies");
        observableMovies.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<MoviesWrapper, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(MoviesWrapper moviesWrapper) {
                        Log.d("MOVIEINTERACTOR", "Observable<Movie> call(MoviesWrapper moviesWrapper) ");
                        return Observable.from(moviesWrapper.getResults());
                    }
                })
                .concatMap(new Func1<Movie, Observable<GalleryItem>>() {
                    @Override
                    public Observable<GalleryItem> call(Movie movie) {
                        Log.d("MOVIEINTERACTOR", "Observable<GalleryItem> call(Movie movie)");
                        GalleryItem item = new GalleryItem();
                        item.title = movie.getTitle();
                        item.backgroundImgURL = movie.getPoster_path();
                        item.id = movie.getId();
                        return Observable.just(item);
                    }
                })
                .subscribe(new Subscriber<GalleryItem>() {
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
                    public void onNext(GalleryItem item) {
                        destList.add(item);
                    }
                });
    }
}
