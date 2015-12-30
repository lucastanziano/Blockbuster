package lucastanziano.blockbuster.view.main;

import android.content.Intent;

import lucastanziano.gallery.model.GalleryItem;
import lucastanziano.blockbuster.Consts;
import lucastanziano.blockbuster.MovieDetailsActivity;
import rx.Subscriber;


public class MainPresenter {

    private MainActivity mainActivity;


    public MainPresenter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        initGallery();
    }

    private void initGallery(){
        mainActivity.gallery.registerSubscriberOnGalleryItemSelected(new Subscriber<GalleryItem>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
            @Override
            public void onNext(GalleryItem item) {
                Intent detailsActivity = new Intent(mainActivity, MovieDetailsActivity.class);
                if (detailsActivity != null) {
                    detailsActivity.putExtra(Consts.MOVIEID, item.id);
                    mainActivity.startActivity(detailsActivity);
                }
            }
        });
    }






}
