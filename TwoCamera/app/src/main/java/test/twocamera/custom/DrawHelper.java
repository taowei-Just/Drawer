package test.twocamera.custom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import test.twocamera.MainActivity;

/**
 * Created by Administrator on 2017-11-06.
 */

public class DrawHelper {


    private CustomView mCustomView;
    private Activity activity;
    private int mLineWidth;
    private int mRubberWidth;
    private int mColor;
    private int mMode;

    public DrawHelper(Activity activity, View view) {

        this.activity = activity;
        this.mCustomView = (CustomView) view;

    }


    public void setMode(int mode) {

        mMode = mode;
        if (mCustomView != null)
            mCustomView.setMode(mode);

    }


    public void setLineColor(int color) {

        mColor = color;

        if (mCustomView != null)

            mCustomView.setLineColor(color);
    }

    public void setLineWidth(int lineWidth) {

        mLineWidth = lineWidth;
        if (mCustomView != null)

            mCustomView.setLineWidth(lineWidth);

    }

    public void setRubberWidth(int rubberWidth) {

        mRubberWidth = rubberWidth;
        if (mCustomView != null)

            mCustomView.setRubberWidth(rubberWidth);

    }

    public Bitmap getDrawerBitmap() {


        Bitmap bitmap = null;

        if (mCustomView != null)
            bitmap = mCustomView.getDrawerBitmap();

        return bitmap;
    }
}
