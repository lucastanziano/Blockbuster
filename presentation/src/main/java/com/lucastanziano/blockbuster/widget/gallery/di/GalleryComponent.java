package com.lucastanziano.blockbuster.widget.gallery.di;

import com.lucastanziano.blockbuster.widget.gallery.view.Gallery;
import com.lucastanziano.blockbuster.widget.gallery.view.adapter.IGalleryAdapterWrapper;

import dagger.Component;


/**
 * Created by Luca on 07/11/2015.
 */
@Component(modules = GalleryModule.class)
public interface GalleryComponent {

    void inject(Gallery gallery);
    void inject(IGalleryAdapterWrapper adapter);

}
