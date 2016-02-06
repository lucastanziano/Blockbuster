package com.lucastanziano.blockbuster.internal.di.components;

import android.content.Context;

import com.lucastanziano.blockbuster.internal.di.modules.ApplicationModule;
import com.lucastanziano.blockbuster.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);


  //Exposed to sub-graphs.
  Context context();
}
