package com.lucastanziano.blockbuster.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.lucastanziano.blockbuster.internal.di.components.MovieGalleryComponent;
import com.lucastanziano.blockbuster.presenter.MovieGalleryPresenter;
import com.lucastanziano.blockbuster.view.frame.MovieTabsFrame;

import javax.inject.Inject;

import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.databinding.MovieGalleryActivityBinding;


public class MovieGalleryActivity extends BaseActivity {


    @Inject
    MovieGalleryPresenter movieGalleryPresenter;

    private String title = "";
    private MovieGalleryActivityBinding mBinding;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.movie_gallery_activity);

        MovieTabsFrame tabsFrame = new MovieTabsFrame(mBinding.contentFrame);

        initialize();
        setupToolbar();


        handleIntent(getIntent());
    }

    private void initialize() {
        this.getComponent(MovieGalleryComponent.class).inject(this);
    }


    private void setupToolbar() {
        mBinding.toolbar.setTitle(this.title);
        setSupportActionBar(mBinding.toolbar);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        this.searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.filter) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    aniamateRevealHide(mBinding.filterFrame);
//
//                }
//            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            Log.d("SEARCH", "Searching: " + query);
          //  moviesInteractor.searchMovieByTitle(query);
        }else{
         //   moviesInteractor.loadPopularMovies();
        }
    }





}
