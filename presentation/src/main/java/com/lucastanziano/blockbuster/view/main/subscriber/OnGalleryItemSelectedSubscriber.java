package lucastanziano.blockbuster.view.main.subscriber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import lucastanziano.blockbuster.view.movie.MovieDetailsActivity;
import lucastanziano.gallery.model.GalleryItem;
import rx.Subscriber;

/**
 * Created by Luca on 23/01/2016.
 */
public class OnGalleryItemSelectedSubscriber extends Subscriber<GalleryItem> {

    private Context startActivity;

    public OnGalleryItemSelectedSubscriber(Context activity) {
        this.startActivity = activity;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(GalleryItem item) {
        Intent detailsActivity = new Intent(startActivity, MovieDetailsActivity.class);
        if (detailsActivity != null) {
            detailsActivity.putExtra(MovieDetailsActivity.MOVIE_ID_TAG, item.id);
            startActivity.startActivity(detailsActivity);
        }
    }
}