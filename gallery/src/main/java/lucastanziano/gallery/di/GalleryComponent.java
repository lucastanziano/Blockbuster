package lucastanziano.gallery.di;

import dagger.Component;
import lucastanziano.gallery.view.Gallery;
import lucastanziano.gallery.view.adapter.IGalleryAdapterWrapper;

/**
 * Created by Luca on 07/11/2015.
 */
@Component(modules = GalleryModule.class)
public interface GalleryComponent {

    void inject(Gallery gallery);
    void inject(IGalleryAdapterWrapper adapter);

}
