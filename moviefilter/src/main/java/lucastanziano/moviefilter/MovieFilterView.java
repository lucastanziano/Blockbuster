package lucastanziano.moviefilter;

import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lucastanziano.moviefilter.databinding.MovieFilterLayoutBinding;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by Luca on 08/12/2015.
 */
public class MovieFilterView {

   private MovieFilterLayoutBinding mBinding;

    private MovieFilterPagerAdapter pagerAdapter;

    public MovieFilterView(){

    }

    public View inflate(ViewGroup container) {
       mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
                R.layout.movie_filter_layout, container, true);
        pagerAdapter = new MovieFilterPagerAdapter(container.getContext());
        mBinding.pager.setAdapter(pagerAdapter);
        return mBinding.getRoot();
    }

    public Observable<String> createObserver(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        subscriber.onNext(pagerAdapter.getFilterData().releaseYearStart + " " +pagerAdapter.getFilterData().releaseYearEnd );
                    }
                };
                mBinding.button.setOnClickListener(listener);
            }
        });
    }
}
