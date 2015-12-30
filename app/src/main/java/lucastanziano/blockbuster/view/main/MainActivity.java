package lucastanziano.blockbuster.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;


import lucastanziano.gallery.view.Gallery;
import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.databinding.ActivityNavigationBinding;
import lucastanziano.blockbuster.di.DaggerNavigationComponent;
import lucastanziano.blockbuster.di.NavigationComponent;
import lucastanziano.blockbuster.view.util.AnimationUtils;
import lucastanziano.moviefilter.MovieFilterView;

public class MainActivity extends AppCompatActivity {

    private String title = "";

    private MainPresenter presenter;

    ActivityNavigationBinding mBinding;

    Gallery gallery;

    SearchView searchView;

    MoviesInteractor moviesInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);
        Log.d("MAINACITIVTY", "ACTIVITY CREATED!|!!!!!");
        injectDepedencies();
        setupToolbar();
        initFilterFrame();
        initContentFrame();
        initFabButton();

        moviesInteractor = new MoviesInteractor(this, gallery.getObservableItems());
        presenter = new MainPresenter(this);

        handleIntent(getIntent());
    }

    private void initContentFrame() {
        gallery = new Gallery(mBinding.contentFrame);

    }

    private void initFabButton() {
        mBinding.fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        MovieFilterView filterFrame = new MovieFilterView();
                        aniamateReveal(mBinding.filterFrame, filterFrame.inflate(mBinding.filterFrame));
                    }
                });
            }
        });
    }

    private void setupToolbar() {
        mBinding.toolbar.setTitle(this.title);
        setSupportActionBar(mBinding.toolbar);
    }

    private void initFilterFrame() {
        mBinding.filterFrame.removeAllViews();
        mBinding.filterFrame.bringToFront();
        mBinding.filterFrame.setVisibility(View.INVISIBLE);

    }

    private void injectDepedencies() {
        NavigationComponent navigationComponent = DaggerNavigationComponent.builder().build();
        navigationComponent.inject(this);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    aniamateRevealHide(mBinding.filterFrame);

                }
            });
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
            moviesInteractor.searchMovieByTitle(query);
        }else{
            moviesInteractor.loadPopularMovies();
        }
    }


    private void aniamateReveal(final View myView, final View nestedView) {
        // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

// get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

// create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
        anim.setDuration(600);
        anim.setStartDelay(600);
        anim.setInterpolator(new FastOutLinearInInterpolator());
// make the view visible and start the animation

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                myView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                nestedView.bringToFront();
            }
        });
        anim.start();
    }

    private void aniamateRevealHide(final View myView) {
        // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

// get the initial radius for the clipping circle
        int initialRadius = myView.getWidth();

// create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
//anim.setDuration(5000);
// make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                initFilterFrame();
            }
        });

// start the animation
        anim.start();
    }

    private void animateRevealShow(final View viewRoot) {
        int cx = viewRoot.getWidth() / 2;
        int cy = viewRoot.getHeight() / 2;

        Log.d("ANIMATION", "animateRevealShow" + cx + " " + cy + " " + viewRoot.toString());
        AnimationUtils.animateRevealShow(this, viewRoot, mBinding.fabButton.getWidth() / 2, R.color.colorAccent,
                cx, cy, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        Log.d("ANIMATION", System.currentTimeMillis() + " start");
                        //   viewRoot.bringToFront();
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Log.d("ANIMATION", System.currentTimeMillis() + " end");

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }


}
