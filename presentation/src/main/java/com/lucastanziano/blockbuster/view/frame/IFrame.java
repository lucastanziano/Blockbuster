package com.lucastanziano.blockbuster.view.frame;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Luca on 25/01/2016.
 */
public interface IFrame {

    public void attachTo(ViewGroup container);

    View getView();
}
