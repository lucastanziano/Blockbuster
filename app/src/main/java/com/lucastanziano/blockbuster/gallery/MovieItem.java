package com.lucastanziano.blockbuster.gallery;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Luca on 06/12/2015.
 */
public class MovieItem {

    public String id = "";
    public String title = "";
    public String subTitle = "";
    public String backgroundImgURL = "";
    public Map<String, String> extraProperties = new TreeMap<String, String>();
}
