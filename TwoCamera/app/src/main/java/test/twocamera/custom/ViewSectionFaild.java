package test.twocamera.custom;

import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-06.
 */

public class ViewSectionFaild {


    Paint paint;
    ArrayList<Path> paths;


    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<Path> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "ViewSectionFaild{" +
                "paint=" + paint +
                ", paths=" + paths +
                '}';
    }
}
