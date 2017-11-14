package test.twocamera.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import huanyang.gloable.gloable.utils.LogUtil;
import test.twocamera.MainActivity;

/**
 * Created by Administrator on 2017-11-03.
 */

public class CustomView extends View {


    private String TAG = getClass().getName();
    private Paint paint;
    Path mPath;
    private int mX;
    private int mY;
    private long lastTimeMillis;
    private Context context;
    private int mode = DrawWitch.draw_line;
    private Paint mEraserPaint;
    private Canvas mBmCanvas;
    private ArrayList<ViewSectionFaild> sectionFailds = new ArrayList<>();
    private ArrayList<ViewSectionFaild> cachSectionFailds = new ArrayList<>();
    private ViewSectionFaild sectionFaild;
    private Bitmap bitmap;

    int lineWidth = 5;
    int lineColor = Color.BLUE;
    int backGroundColor = Color.WHITE;
    int rubberWidth = 50;

    public void setMode(int mode) {

        if (this.mode != DrawWitch.draw_rubber && mode == DrawWitch.draw_rubber) {

            sectionFaild = createRubberPaint(rubberWidth);

        } else if (this.mode != DrawWitch.draw_line && mode == DrawWitch.draw_line) {

            sectionFaild = createPaint(lineWidth, lineColor);
        }

        this.mode = mode;
    }

    public CustomView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backGroundColor);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        
    }

    private ViewSectionFaild createRubberPaint(int width) {

        Paint paint = new Paint();
        ViewSectionFaild sectionFaild = new ViewSectionFaild();
        paint.setStrokeWidth(width);
        paint.setAlpha(0);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        sectionFaild.setPaint(paint);
        return sectionFaild;
    }

    private ViewSectionFaild createPaint(int width, int color) {

        Paint paint = new Paint();

        ViewSectionFaild sectionFaild = new ViewSectionFaild();

        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(width);
        paint.setAntiAlias(true);

        sectionFaild.setPaint(paint);

        return sectionFaild;
    }

    private void drawPath(Canvas canvas, Paint paint) {

        if (mPsList.size() > 0) {
            for (int i = 0; i < mPsList.size(); i++) {
                Path path = mPsList.get(i);
                canvas.drawPath(path, this.paint);

            }

        }

        if (mPath != null)
            canvas.drawPath(mPath, paint);

    }


    private void drawRubber(Canvas canvas, Paint paint) {

        drawPath(canvas, paint);

    }

    private void drawLine(Canvas canvas) {


//        canvas.drawColor(Color.GRAY);

        paint.setColor(Color.BLUE);

        paint.setStrokeWidth(5);

        paint.setAntiAlias(true);


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

        sectionFailds = new ArrayList<>();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        initBitmap();
        sectionFaild = createPaint(lineWidth, lineColor);
    }

    private void initBitmap() {


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        LogUtil.e(TAG , " 初始化bitmap "  , false);

        bitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        mBmCanvas = new Canvas(bitmap);
        
      
    }

    List<Path> mPsList = new ArrayList<>();
    List<Path> mCachPS = new ArrayList<>();

    boolean isLastExcute = false;

    boolean isScroll = false;

    public boolean onTouchEvent(MotionEvent event) {
        int x;
        int y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isScroll = false;
             

                if (System.currentTimeMillis() - lastTimeMillis < 200 && lastTimeMillis != 0) {
                    //显示 工具图标
                    LogUtil.e(TAG , "  双击用时 " +( System.currentTimeMillis() - lastTimeMillis) );

//                    Toast.makeText(context ,"显示工具图标 ",Toast.LENGTH_SHORT ).show();
                    
                    ((MainActivity) context).showInstruentView();
                } else {
                    lastTimeMillis = System.currentTimeMillis();
                }
                x = (int) event.getX();
                y = (int) event.getY();

                mPath = new Path();
                mPath.moveTo(x, y);


                mX = x;
                mY = y;

                break;

            case MotionEvent.ACTION_MOVE:

                isScroll = true;

                if (isLastExcute) {
                    LogUtil.e(TAG , "回退了或者清理了  清理缓存 " + sectionFailds.size());
                  
                    cachSectionFailds = new ArrayList<>();
                    sectionFaild.setPaths(new ArrayList<Path>());
                    isLastExcute = false;
                }
                
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


                draweSameThing();

                break;

            case MotionEvent.ACTION_UP:
 
                x = (int) event.getX();
                y = (int) event.getY();

                mPath.lineTo(x, y);

                draweSameThing();

                if (isScroll) {
                    if (sectionFaild != null) {
                        if (sectionFaild.getPaths() == null) {
                            ArrayList<Path> paths = new ArrayList<>();
                            sectionFaild.setPaths(paths);
                        }

                        sectionFaild.getPaths().add(mPath);
                        
                        if (!sectionFailds.contains(sectionFaild)) {
                            sectionFailds.add(sectionFaild);
                        }
                        
                    }
                }

                if (System.currentTimeMillis() - lastTimeMillis < 80) {
                    LogUtil.e(TAG , "  单击用时 " + (System.currentTimeMillis() - lastTimeMillis ));

                    ((MainActivity) context).hideInstruentView();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void draweSameThing() {

        //绘制历史线条

        // 绘制当前线条

        switch (mode) {

            case DrawWitch.draw_line:

                mBmCanvas.drawPath(mPath, sectionFaild.getPaint());
                break;
            case DrawWitch.draw_rubber:

                mBmCanvas.drawPath(mPath, sectionFaild.getPaint());

                break;
        }

        super.invalidate();
    }

    private void drawHistory() {
        
        if (sectionFailds != null && sectionFailds.size() > 0) {
            
            LogUtil.e(TAG , " sectionFailds " + sectionFailds .size() , false);
            
            for (ViewSectionFaild sectionFaild : sectionFailds) {

                if (sectionFaild != null && sectionFaild.getPaths() != null)

                    for (Path path : sectionFaild.getPaths()) {
                        mBmCanvas.drawPath(path, sectionFaild.getPaint());
                    }
            }
        }
    }

    // 上一个

    public void lastLine() {

        isLastExcute = true;

        if (sectionFailds != null && sectionFailds.size() > 0) {

            LogUtil.e(TAG, "撤销 " + sectionFailds.size(), false);

            if (cachSectionFailds == null)
                cachSectionFailds = new ArrayList<>();

            LogUtil.e(TAG, "不同画笔数量" + sectionFailds.size(), false);

            for (int i = sectionFailds.size() - 1; i >= 0; i--) {

                ViewSectionFaild sectionFaild = sectionFailds.get(i);
                ArrayList<Path> paths = null;
                if (sectionFaild != null)
                    paths = sectionFaild.getPaths();

                if (paths != null && paths.size() > 0) {

                    LogUtil.e(TAG, "  最后一组段数量 " + paths.size(), false);

                    Path path = paths.get(paths.size() - 1);
                    ViewSectionFaild cachFild = null;

                    if (cachSectionFailds.size() > 0) {

                        cachFild = cachSectionFailds.get(cachSectionFailds.size() - 1);
                        
                        LogUtil.e(TAG, " 获取缓存 最后一组数据 "   , false);
                        
                    }

                    ArrayList<Path> cachPaths ;
                    
                    if (cachFild != null && cachFild.getPaint() != null && cachFild.getPaint() == sectionFaild.getPaint()) {
  
                        cachPaths = cachFild.getPaths();
                        
                        if (cachPaths == null)
                            cachPaths = new ArrayList<>();

                        cachPaths.add(path);
                        
                        cachFild.setPaths(cachPaths);
                        LogUtil.e(TAG, " 相同画笔  缓存线段大小" + cachPaths.size()  , false);
                        
                        
                    } else {
                        
                      
                        
                        cachFild = new ViewSectionFaild();
                        
                        cachFild.setPaint(sectionFaild.getPaint());
                        
                          cachPaths = new ArrayList<>();

                        cachPaths.add(path);
                        
                        cachFild.setPaths(cachPaths);

                        LogUtil.e(TAG, " 使用不同的画笔  缓存线段大小 " +cachPaths.size(), false);
                    }
                   

                    if (!cachSectionFailds.contains(cachFild))
                        cachSectionFailds.add(cachFild);

                    paths.remove(path);

                    break;

                } else {

                    LogUtil.e(TAG, " 移除为空的段落  ", false);
                    
                    sectionFailds.remove(sectionFaild);
                  
                }

            }

        }

        initBitmap();
        drawHistory();
        invalidate();
    }


    // 下一个

    public void recoverLine() {

        if (cachSectionFailds != null && cachSectionFailds.size() > 0) {

            LogUtil.e(TAG, "恢复 " + cachSectionFailds.size(), false);


            for (int i = cachSectionFailds.size() - 1; i >= 0; i--) {
                
                ViewSectionFaild cachFild = cachSectionFailds.get(i);

                if (cachFild != null && cachFild.getPaths() != null && cachFild.getPaths().size() > 0) {

                    ArrayList<Path> cachPaths = cachFild.getPaths();
                    Path cachPath = cachPaths.get(cachPaths.size() - 1);
                    Paint cachPaint = cachFild.getPaint();

                    ViewSectionFaild sectionFaild = null;
                    ArrayList<Path> paths;
                    
                    if (sectionFailds.size() > 0) {
                        sectionFaild = sectionFailds.get(sectionFailds.size() - 1);
                    }
                    if (sectionFaild != null && sectionFaild.getPaint() != null && sectionFaild.getPaint() == cachPaint) {

                        LogUtil.e(TAG, "恢复段落画笔与最后绘制画笔相同 ", false);

                        paths = sectionFaild.getPaths();
                        if (paths == null)
                            paths = new ArrayList<>();
                    } else {
                        LogUtil.e(TAG, "恢复段落画笔与最后绘制画笔不同 ", false);
                        sectionFaild = new ViewSectionFaild();
                        sectionFaild.setPaint(cachPaint);
                         paths = new ArrayList<>();
                    }
             
                    paths.add(cachPath);
                    sectionFaild.setPaths(paths);

                    LogUtil.e(TAG, " 恢复后数据大小" + paths.size(), false);

                    if (!sectionFailds.contains(sectionFaild))
                        sectionFailds.add(sectionFaild);
                    cachPaths.remove(cachPath);
                    
                    break;

                } else {
                    LogUtil.e(TAG, " 移除为空的段落  ", false);
                    cachSectionFailds.remove(cachFild);
                }

            }

        }

        initBitmap();
        drawHistory();
        invalidate();
    }

    public void clear() {

        LogUtil.e(TAG, "清理 " + sectionFailds.toString(), false);
        isLastExcute = true;
        if (sectionFailds != null && sectionFailds.size() > 0) {
            // 留一次反悔的机会
            if (cachSectionFailds==null)
                cachSectionFailds =new ArrayList<>();
            
            for (int i =sectionFailds.size()-1 ; i>=0; i--) {
                
                cachSectionFailds.add(sectionFailds.get(i));
                
            }
            
            sectionFailds = new ArrayList<>();
        }else {
            cachSectionFailds =new ArrayList<>();
        }

      
        mPath = new Path();
        
        initBitmap();
        drawHistory();
        invalidate();
    }


 

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        
        if (mode==DrawWitch.draw_line){
            
       sectionFaild = createPaint(lineWidth,lineColor);
        
        }
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void setRubberWidth(int rubberWidth) {
        this.rubberWidth = rubberWidth;

        if (mode==DrawWitch.draw_rubber)
        {
            sectionFaild =createRubberPaint(rubberWidth);
            
        }
    }

    public Bitmap getDrawerBitmap() {
        //获取自定义view图片的大小
        Bitmap temBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        //使用Canvas，调用自定义view控件的onDraw方法，绘制图片
        Canvas canvas = new Canvas(temBitmap);
        draw(canvas);
     
        return  temBitmap ;
    }
}
