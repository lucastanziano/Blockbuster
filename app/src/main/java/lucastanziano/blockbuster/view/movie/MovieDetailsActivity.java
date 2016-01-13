package lucastanziano.blockbuster.view.movie;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.data.MovieRepository;
import lucastanziano.blockbuster.databinding.ActivityMovieDetailsBinding;
import lucastanziano.blockbuster.model.MovieDetail;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_ID_TAG = "movie_id";
    private ActivityMovieDetailsBinding mBinding;
    private MovieRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        Fresco.initialize(this);
        repo = new MovieRepository(this);
        String movieID = getIntent().getStringExtra(MOVIE_ID_TAG);
        if(movieID!=null){
            initDetails(movieID);
        }

        mBinding.toolbar.setTitle("");
        setSupportActionBar(mBinding.toolbar);

        mBinding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));


    }

    private void initDetails(final String movieID) {
        Log.d("MOVIE_DETAILS_ACTIVITY",  " " + movieID);
       Observable<MovieDetail> details = repo.getMovieDetails(movieID);
        details.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieDetail>() {
            @Override
            public void onCompleted() {
                Log.d("MOVIE_DETAILS_ACTIVITY",  "COMPLETED " + movieID);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MOVIE_DETAILS_ACTIVITY",  "ERROR " + movieID);
                e.printStackTrace();
            }

            @Override
            public void onNext(MovieDetail movieDetail) {
                Log.d("MOVIE_DETAILS_ACTIVITY", movieDetail.getId()+ " " + movieID);
                mBinding.title.setText(movieDetail.getTitle());
                mBinding.description.setText(movieDetail.getOverview());
                String address = "http://image.tmdb.org/t/p/w" + "300" + movieDetail.getPoster_path();
                Uri uri = Uri.parse(address);
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setProgressiveRenderingEnabled(true)
                        .setPostprocessor(new BasePostprocessor() {
                            @Override
                            public void process(Bitmap bitmap) {
                                super.process(bitmap);
                                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        int primaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
                                        int primary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
                                        mBinding.collapsingToolbar.setContentScrimColor(palette.getMutedColor(primary));
                                        mBinding.collapsingToolbar.setStatusBarScrimColor(palette.getDarkVibrantColor(primaryDark));
                                    }
                                });
                            }
                        })
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(mBinding.picture.getController())
                        .build();
                Log.d("FRESCOLOADING", address);
                mBinding.picture.setController(controller);

                mBinding.picture.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(lucastanziano.gallery.R.dimen.poster_grid2_height)));
            }
        });
    }
}
