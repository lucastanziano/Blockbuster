package com.lucastanziano.blockbuster;

import android.app.Application;

import com.lucastanziano.blockbuster.internal.di.components.ApplicationComponent;
import com.lucastanziano.blockbuster.internal.di.components.DaggerApplicationComponent;
import com.lucastanziano.blockbuster.internal.di.modules.ApplicationModule;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    this.initializeInjector();
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }


}
