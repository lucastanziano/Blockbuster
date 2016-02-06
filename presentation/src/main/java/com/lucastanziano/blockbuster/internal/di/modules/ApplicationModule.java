package com.lucastanziano.blockbuster.internal.di.modules;

import android.content.Context;

import com.lucastanziano.blockbuster.AndroidApplication;
import com.lucastanziano.blockbuster.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton
  Navigator provideNavigator() {
    return new Navigator();
  }


}
