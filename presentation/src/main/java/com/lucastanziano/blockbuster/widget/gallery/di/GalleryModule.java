package com.lucastanziano.blockbuster.widget.gallery.di;

import android.content.Context;

import com.lucastanziano.blockbuster.widget.gallery.PosterSizes;
import com.lucastanziano.blockbuster.widget.gallery.view.adapter.IGalleryAdapterWrapper;
import com.lucastanziano.blockbuster.widget.gallery.view.adapter.MovieGalleryAdapter;

import dagger.Module;
import dagger.Provides;


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
