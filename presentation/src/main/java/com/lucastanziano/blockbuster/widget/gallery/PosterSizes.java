package com.lucastanziano.blockbuster.widget.gallery;

/**
 * Created by Luca on 13/12/2015.
 */
public enum PosterSizes {

    VLINEAR(1, 300, 450),
    GRID_2(2, 300, 400),
    GRID_3(3, 185, 220);

    public int getElementsPerRow() {
        return elementsPerRow;
    }

    public int getWidth() {
        return width;
    }

    private final int elementsPerRow;
    private final int width;

    public int getHeight() {
        return height;
    }

    private final int height;


    PosterSizes(int elementsPerRow, int width, int height){
        this.elementsPerRow = elementsPerRow;
        this.width = width;
        this.height = height;
    }


}
