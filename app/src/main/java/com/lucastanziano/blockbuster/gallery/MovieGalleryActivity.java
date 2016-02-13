package com.lucastanziano.blockbuster.gallery;

import android.animation.Animator;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import com.lucastanziano.blockbuster.internal.di.Repository;
import com.lucastanziano.blockbuster.data.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import com.lucastanziano.blockbuster.R;
import com.lucastanziano.blockbuster.databinding.ActivityMovieGalleryBinding;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MovieGalleryActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener {


    private static final String CLOSE = "X";
    private static final String TRENDING = "Trending";
    private static final String TOP_RATED = "Top Rated";
    private static final String COMING_SOON = "Coming Soon";
    private String title = "";
    private ActivityMovieGalleryBinding mBinding;
    private MovieDataRepository movieDataRepository = Repository.getDefaultMovieRepository();
    private ViewAnimator viewAnimator;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ActionBarDrawerToggle toggle;
    private GalleryView galleryFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_gallery);
        galleryFrame = new GalleryView(mBinding.contentFrame, MovieProviderFactory.getTrendingMoviesProvider());
        setupToolbar();
        setupSideMenu();
    }


    private void setupToolbar() {
        mBinding.toolbar.setTitle(R.string.popular);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(
                this, mBinding.drawerLayout, mBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mBinding.leftDrawer.removeAllViews();
                mBinding.leftDrawer.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && mBinding.leftDrawer.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mBinding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void setupSideMenu() {
        createMenuList();
        mBinding.leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.drawerLayout.closeDrawers();
            }
        });
        viewAnimator = new ViewAnimator<>(this, list, galleryFrame, mBinding.drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.ic_clear_white_24dp);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(TRENDING, R.drawable.ic_trending_up_white_24dp);
        list.add(menuItem);
        SlideMenuItem menuItem1 = new SlideMenuItem(TOP_RATED, R.drawable.ic_equalizer_white_24dp);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem(COMING_SOON, R.drawable.ic_theaters_white_18dp);
        list.add(menuItem2);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = mBinding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }






    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, String name) {
        //   this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = mBinding.contentFrame;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        view.setVisibility(View.VISIBLE);
        mBinding.contentOverlay.setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        mBinding.contentFrame.removeAllViews();

        IMovieProvider movieProvider;
        Log.d("TOPPOSITION", name + "");
        mBinding.toolbar.setTitle(name);

        switch (name) {
            case TRENDING:
                movieProvider = MovieProviderFactory.getTrendingMoviesProvider();
                break;
            case TOP_RATED:
                movieProvider = MovieProviderFactory.getTopRatedMoviesProvider();
                break;
            case COMING_SOON:
                movieProvider = MovieProviderFactory.getUpcomingMoviesProvider();
                break;
            default:
                movieProvider = MovieProviderFactory.getTrendingMoviesProvider();
                break;
        }


        galleryFrame = new GalleryView(mBinding.contentFrame,movieProvider);
        TransitionManager.beginDelayedTransition(mBinding.contentFrame, new Fade());
        return galleryFrame;
    }



    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case CLOSE:
                return screenShotable;
            default:
                return replaceFragment(screenShotable, position, slideMenuItem.getName());
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        mBinding.drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        mBinding.leftDrawer.addView(view);
    }
}
