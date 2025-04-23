package com.os0.navigation;

import android.graphics.Path;

public class paint_Stroke {

    public int color;

    public int strokeWidth;

    public Path path;

    public paint_Stroke(int color, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
