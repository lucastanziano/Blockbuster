package com.lucastanziano.blockbuster.view.activity;

import android.animation.Animator;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;

import com.lucastanziano.blockbuster.Movie;
import com.lucastanziano.blockbuster.internal.di.Repository;
import com.lucastanziano.blockbuster.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.view.frame.MovieGalleryFrame;
import com.lucastanziano.blockbuster.view.frame.MovieTabsFrame;
import com.lucastanziano.blockbuster.view.frame.adapter.IMovieProvider;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.databinding.ActivityMovieGalleryBinding;
import lucastanziano.blockbuster.databinding.MovieGalleryActivityBinding;
import rx.Observable;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MovieGalleryActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener {


    private static final String CLOSE = "X";
    private static final String TRENDING = "Trending";
    private String title = "";
    private ActivityMovieGalleryBinding mBinding;
    //    private SearchView searchView;
    private MovieDataRepository movieDataRepository = Repository.getDefaultMovieRepository();
    private ViewAnimator viewAnimator;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ActionBarDrawerToggle toggle;
    private MovieGalleryFrame galleryFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_gallery);
        galleryFrame = new MovieGalleryFrame(mBinding.contentFrame, new IMovieProvider() {
            int page = 1;

            @Override
            public Observable<Movie> getMoreMovies() {
                return getMovies(page++);
            }

            @Override
            public Observable<Movie> getMovies(int page) {
                return movieDataRepository.getPopularMovies(page);
            }
        });
        setupToolbar();
        handleIntent(getIntent());

        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, galleryFrame, mBinding.drawerLayout, this);

        toggle.syncState();
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

        mBinding.leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.drawerLayout.closeDrawers();
            }
        });
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.ic_menu_gallery);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(TRENDING, R.drawable.ic_menu_send);
        list.add(menuItem);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        this.searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.nav_trending) {
            mBinding.toolbar.setTitle(R.string.popular);

            return true;
        }
        if (id == R.id.nav_top_rated) {
            mBinding.toolbar.setTitle(R.string.top_rated);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            final String query = intent.getStringExtra(SearchManager.QUERY);
//            //use the query to search your data somehow
//            Log.d("SEARCH", "Searching: " + query);
//            //  moviesInteractor.searchMovieByTitle(query);
//        } else {
//            //   moviesInteractor.loadPopularMovies();
//        }
    }


    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
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
        galleryFrame = new MovieGalleryFrame(mBinding.contentFrame, new IMovieProvider() {
            int page = 1;

            @Override
            public Observable<Movie> getMoreMovies() {
                return movieDataRepository.getComingSoonMovies(page++);
            }

            @Override
            public Observable<Movie> getMovies(int page) {
                return movieDataRepository.getComingSoonMovies(page);
            }
        });
        //galleryFrame.getView().setPadding();
        TransitionManager.beginDelayedTransition(mBinding.contentFrame, new Fade());
        return galleryFrame;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case CLOSE:
                return screenShotable;
            default:
                return replaceFragment(screenShotable, position);
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
