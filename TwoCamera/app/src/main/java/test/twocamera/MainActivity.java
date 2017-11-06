package test.twocamera;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import test.twocamera.custom.CustomView;
import test.twocamera.fragment.BottomColorSlectFragment;

public class MainActivity extends Activity implements View.OnClickListener {

    private SurfaceHolder holder01;
    private SurfaceHolder holder02;
    private SurfaceView sv01;
    private SurfaceView sv02;
    private CustomView customView;
    private ImageView ivRevover;
    private ImageView ivRevication;
    private long lastTimeMillion = 0;
    private ImageView iv_more;
    private ImageView ivColorSelect;

    public static String BOTTOM_FRAGMENT_TAG = "BottomColorSlectFragment";
    private BottomColorSlectFragment bottomColorSlectFragment;
    private ImageView ivClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
//
//        sv01 =  findViewById(R.id.sv_01);
//        sv02 =   findViewById(R.id.sv_02);
//
//        holder01 = sv01.getHolder();
//        holder02 = sv02.getHolder();
//        holder01.addCallback(new SurfaceHolder.Callback() {
//            private Camera camera;
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                camera = display(holder, 1);
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//            cancelCarmera(camera);
//            }
//        });
//        
//        
//        holder02.addCallback(new SurfaceHolder.Callback() {
//
//            private Camera camera;
//
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//
//                camera = display(holder, 2);
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//
//                cancelCarmera(camera);
//                
//            }
//        });

        customView = findViewById(R.id.cv_draw_view);
        ivRevover = findViewById(R.id.iv_revover);
        ivRevication = findViewById(R.id.iv_revication_);
        iv_more = findViewById(R.id.iv_more_function);
        ivColorSelect = findViewById(R.id.iv_coror_select);
        ivClear = findViewById(R.id.iv_clear_);

        ivClear.setOnClickListener(this);
        ivColorSelect.setOnClickListener(this);
        ivRevover.setOnClickListener(this);
        ivRevication.setOnClickListener(this);
        iv_more.setOnClickListener(this);

    }

    private Camera display(SurfaceHolder holder, int index) {

        int numberOfCameras = Camera.getNumberOfCameras();
        if (index == 1) {

            if (numberOfCameras > 0) {
                try {
                    Camera camera01 = Camera.open(0);

                    Camera.Parameters parameters = camera01.getParameters();

                    parameters.setPreviewSize(sv01.getWidth(), sv01.getHeight());

                    camera01.setDisplayOrientation(90);

//                 camera01.setParameters(parameters);
                    camera01.cancelAutoFocus();

                    camera01.setPreviewDisplay(holder);

                    camera01.startPreview();


                    return camera01;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } else {


            if (numberOfCameras > 1) {
                try {

                    Camera camera02 = Camera.open(1);
                    Camera.Parameters parameters = camera02.getParameters();

                    parameters.setPreviewSize(sv01.getWidth(), sv01.getHeight());

                    camera02.setDisplayOrientation(90);

//                    camera02.setParameters(parameters);

                    camera02.cancelAutoFocus();

                    camera02.setPreviewDisplay(holder);

                    camera02.startPreview();

                    return camera02;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }


        return null;


    }

    public void cancelCarmera(Camera camera) {

        if (camera != null) {
            try {
                camera.setPreviewDisplay(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.stopPreview();
            try {
                camera.reconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.release();
            camera = null;
        }


    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (System.currentTimeMillis() - lastTimeMillion < 3 * 1000 && lastTimeMillion != 0) {

            return super.onKeyDown(keyCode, event);

        } else {

            lastTimeMillion = System.currentTimeMillis();

        }

        Toast.makeText(this, "再次点击会退出的哦，亲！", Toast.LENGTH_LONG).show();


        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_revover:
                // 上一步

                customView.lastLine();
                break;

            case R.id.iv_revication_:
                customView.recoverLine();
                break;
            
            case R.id.iv_clear_:
                customView.clear();
                break;

            case R.id.iv_more_function:
                Toast.makeText(MainActivity.this, "更多功能敬请期待！", Toast.LENGTH_SHORT).show();
//                hideColorFragment();
                break;

            case R.id.iv_coror_select:

//                Toast.makeText(MainActivity.this,"更多功能敬请期待！",Toast.LENGTH_SHORT).show();

                showColorFragment();

                break;
        }
    }

    private void showColorFragment() {


        ivColorSelect.setVisibility(View.INVISIBLE);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (bottomColorSlectFragment == null)
            bottomColorSlectFragment = new BottomColorSlectFragment();

        fragmentTransaction.replace(R.id.fl_bottom_select, bottomColorSlectFragment, BOTTOM_FRAGMENT_TAG);

        fragmentTransaction.commit();


    }

    public void hideColorFragment() {


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (bottomColorSlectFragment != null) {
            fragmentTransaction.remove(bottomColorSlectFragment);
        }
        fragmentTransaction.commit();
        bottomColorSlectFragment = null;
        ivColorSelect.setVisibility(View.VISIBLE);

    }

    public void hideInstruentView() {


        ivClear.setVisibility(View.INVISIBLE);
        iv_more.setVisibility(View.INVISIBLE);
        ivRevication.setVisibility(View.INVISIBLE);
        ivRevover.setVisibility(View.INVISIBLE);
        ivColorSelect.setVisibility(View.INVISIBLE);
   

    }
       public void showInstruentView() {


           ivClear.setVisibility(View.VISIBLE);
           iv_more.setVisibility(View.VISIBLE);
           ivRevication.setVisibility(View.VISIBLE);
           ivRevover.setVisibility(View.VISIBLE);
           ivColorSelect.setVisibility(View.VISIBLE);


       }
    
    
}
