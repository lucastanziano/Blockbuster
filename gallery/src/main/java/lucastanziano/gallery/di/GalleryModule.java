package lucastanziano.gallery.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import lucastanziano.gallery.view.adapter.IGalleryAdapterWrapper;
import lucastanziano.gallery.view.adapter.MovieGalleryAdapter;
import lucastanziano.gallery.PosterSizes;

/**
 * Created by Luca on 07/11/2015.
 */
@Module
public class GalleryModule {

    private final Context context;

    public GalleryModule(Context context){
      this.context = context;
    }

    @Provides
    IGalleryAdapterWrapper providesAdapter() {
        return new MovieGalleryAdapter(context, PosterSizes.GRID_2);
    }


}
