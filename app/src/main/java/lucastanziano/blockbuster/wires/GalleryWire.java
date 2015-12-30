package lucastanziano.blockbuster.wires;

import android.widget.Toast;

import common.Movie;
import common.MoviesWrapper;
import lucastanziano.gallery.model.GalleryItem;
import lucastanziano.gallery.view.Gallery;
import lucastanziano.blockbuster.Repository;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tmdb.RestMovieSource;

/**
 * Created by Luca on 06/12/2015.
 */
public class GalleryWire {

    public static void injectPopularMovies(final Gallery view) {
        RestMovieSource repo = Repository.INSTANCE.getMovieRepository();
        repo.getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MoviesWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), e.getCause().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(MoviesWrapper moviesWrapper) {
                        for (Movie movie : moviesWrapper.getResults()) {
                            view.getObservableItems().add(mapMovieToGalleryItem(movie));

                        }
                    }
                });
    }

    private static GalleryItem mapMovieToGalleryItem(Movie movie) {
        GalleryItem item = new GalleryItem();
        item.title = movie.getTitle();
        item.backgroundImgURL = movie.getPoster_path();
        item.id = movie.getId();
        return item;
    }
}
