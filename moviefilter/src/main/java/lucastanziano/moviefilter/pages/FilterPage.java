package lucastanziano.moviefilter.pages;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Luca on 10/12/2015.
 */
public interface FilterPage<T> {

    View inflate(ViewGroup container);

    void fillData(T data);

}


