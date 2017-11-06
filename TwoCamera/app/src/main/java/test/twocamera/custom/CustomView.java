package test.twocamera.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.twocamera.MainActivity;

/**
 * Created by Administrator on 2017-11-03.
 */

public class CustomView extends View {

    // 画笔当前颜色


    // 当前颜色宽度


    //橡皮宽度


    // 绘制图形code


    private String TAG = getClass().getName();
    private Paint paint;

    Path mPath = new Path();
    private int mX;
    private int mY;
    private long lastTimeMillis;
    
    Context context ;

    int mode = DrawWitch.draw_line ;
    private Paint mEraserPaint;

    public void setMode(int mode) {
        this.mode = mode;
    }

    public CustomView(Context context) {
        super(context);
       this. context = context ;
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this. context = context ;
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this. context = context ;
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        
//        drawLine(canvas);
//        
       switch (mode){

           case  DrawWitch.draw_line:

               paint.setColor(Color.BLUE);
               paint.setStyle(Paint.Style.STROKE);
               paint.setStrokeWidth(5);
               paint.setAntiAlias(true);

               drawPath(canvas);
               break;
           case  DrawWitch.draw_rubber :

               drawRubber(canvas);


               break;
       }

    }

    private void drawPath(Canvas canvas) {


        if (mPsList.size() > 0) {
            for (int i = 0; i < mPsList.size(); i++) {
                Path path = mPsList.get(i);
                canvas.drawPath(path, paint);

            }

        }

        if (mPath != null)
            canvas.drawPath(mPath, paint);

    }


    private void drawRubber(Canvas canvas) {




        paint.setStrokeWidth(30);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        paint.setStrokeJoin(Paint.Join.ROUND);

            drawPath(canvas);
// 
//        
//        if (xS.size() > 0)
//            for (int j = 1; j < xS.size(); j++) {
//
//                int lx = xS.get(j - 1);
//                int ly = yS.get(j - 1);
//                int x = xS.get(j);
//                int y = yS.get(j);
//
//                // 画园点
////            canvas.drawCircle(x,y,5,paint);
//
//                //画线
//                canvas.drawLine(lx, ly, x, y, paint);
//            }
//        
//        

    }

    private void drawLine(Canvas canvas) {


//        canvas.drawColor(Color.GRAY);

        paint.setColor(Color.BLUE);

        paint.setStrokeWidth(5);

        paint.setAntiAlias(true);

        paint.setTextSize(40);

//   
//    
//        if (listX.size() > 0) {
//
//            for (int i = 0; i < listX.size(); i++) {
//
//                List<Integer> xs = listX.get(i);
//                List<Integer> ys = listY.get(i);
//
//                if (xs.size() > 0)
//                    for (int j = 1; j < xs.size(); j++) {
//
//                        int lx = xs.get(j - 1);
//                        int ly = ys.get(j - 1);
//
//                        int x = xs.get(j);
//                        int y = ys.get(j);
//
//                        // 画园点
////            canvas.drawCircle(x,y,2,paint);
//
//                        //画线
//                        canvas.drawLine(lx, ly, x, y, paint);
//                    }
//            }
//        }
//
//
//        if (xS.size() > 0)
//            for (int j = 1; j < xS.size(); j++) {
//
//                int lx = xS.get(j - 1);
//                int ly = yS.get(j - 1);
//                int x = xS.get(j);
//                int y = yS.get(j);
//
//                // 画园点
////            canvas.drawCircle(x,y,5,paint);
//
//                //画线
//                canvas.drawLine(lx, ly, x, y, paint);
//            }
    }


    private void init() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEraserPaint = new Paint();
    }

    List<Path> mPsList = new ArrayList<>();
    List<Path> mCachPS = new ArrayList<>();

    boolean isLastExcute = false;

    boolean isScroll  =false;

    public boolean onTouchEvent(MotionEvent event) {
        int x;
        int y;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                isScroll  =false;

                if (System.currentTimeMillis() - lastTimeMillis < 300 && lastTimeMillis != 0) {

                    //显示 工具图标

//                    Toast.makeText(context ,"显示工具图标 ",Toast.LENGTH_SHORT ).show();
                    
                    ((MainActivity)context).showInstruentView();
                } else {
                    lastTimeMillis = System.currentTimeMillis();
                }
                x = (int) event.getX();
                y = (int) event.getY();

                mPath = new Path();
                mPath.moveTo(x, y);

                if (isLastExcute) {
                    mCachPS = new ArrayList<>();
                    isLastExcute = false;
                }

                mX = x;
                mY = y;

                break;

            case MotionEvent.ACTION_MOVE:


                x = (int) event.getX();
                y = (int) event.getY();


                final float previousX = mX;
                final float previousY = mY;

                final float dx = Math.abs(x - previousX);
                final float dy = Math.abs(y - previousY);

                //两点之间的距离大于等于3时，生成贝塞尔绘制曲线
                if (dx >= 3 || dy >= 3) {
                    //设置贝塞尔曲线的操作点为起点和终点的一半
                    float cX = (x + previousX) / 2;
                    float cY = (y + previousY) / 2;

                    //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
                    mPath.quadTo(previousX, previousY, cX, cY);

                    //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
                    mX = x;
                    mY = y;
                }
                isScroll  =true;
                postInvalidate();
                break;

            case MotionEvent.ACTION_UP:

                if (isScroll)
                mPsList.add(mPath);
                
                if(System.currentTimeMillis() -lastTimeMillis < 100){

                    ((MainActivity)context).hideInstruentView();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    // 上一个

    public void lastLine() {

        isLastExcute = true;

        if (mPsList.size() > 0) {

            Path path = mPsList.get(mPsList.size() - 1);
            mCachPS.add(path);
            mPsList.remove(path);
        }
        mPath = new Path();
        this.postInvalidate();
    }


    // 下一个

    public void recoverLine() {


        if (mCachPS.size() > 0) {
            Path path = mCachPS.get(mCachPS.size() - 1);
            mPsList.add(path);
            mCachPS.remove(path);
        }
        this.postInvalidate();
    }

    public void clear() {

        isLastExcute = true;

        if (mPsList.size() > 0) {

            // 留一次反悔的机会
            mCachPS.addAll(mPsList);

            mPsList = new ArrayList<>();

        }

        mPath = new Path();

        this.postInvalidate();
    }
}
