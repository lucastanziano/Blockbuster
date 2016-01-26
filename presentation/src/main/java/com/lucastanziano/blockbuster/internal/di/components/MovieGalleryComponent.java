package com.lucastanziano.blockbuster.internal.di.components;

import com.lucastanziano.blockbuster.internal.di.PerActivity;
import com.lucastanziano.blockbuster.internal.di.modules.ActivityModule;
import com.lucastanziano.blockbuster.internal.di.modules.MovieGalleryModule;
import com.lucastanziano.blockbuster.view.activity.MovieGalleryActivity;

import dagger.Component;

/**
 * Created by Luca on 25/01/2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MovieGalleryModule.class})
public interface MovieGalleryComponent {
    void inject(MovieGalleryActivity movieGalleryActivity);
}
