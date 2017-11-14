package test.twocamera.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.SharedUtlis;
import test.twocamera.MainActivity;
import test.twocamera.R;
import test.twocamera.custom.DrawHelper;
import test.twocamera.custom.DrawWitch;

/**
 * Created by Administrator on 2017-11-06.
 */

public class BottomColorSlectFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {


    private View rootFragmentView;
    private ImageView ivPickUp;
    private CheckBox cbRubber;
    private MainActivity mActivity;
    private View mRootFragmentView;
    private SeekBar sbRubber;
    private SeekBar sbPainWidth;
    private DrawHelper mDrawHelper;
    private SharedUtlis mSharedUtlis;
    private CheckBox cbPaint;
    private String TAG = getClass().getName();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_layout_bottom, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initview();
        initdata();
    }

    private void initview() {
        
        mActivity = (MainActivity) getActivity();
        mRootFragmentView = getView();
        
        ivPickUp = mRootFragmentView.findViewById(R.id.iv_packup_right);
        cbRubber = mRootFragmentView.findViewById(R.id.cb_rubber);
        cbPaint = mRootFragmentView.findViewById(R.id.cb_paint);
        sbRubber = mRootFragmentView.findViewById(R.id.sb_rubber);
        sbPainWidth = mRootFragmentView.findViewById(R.id.sb_pain_width);

    }

    private void initdata() {


        mDrawHelper = mActivity.getDrawHelper();
        mSharedUtlis = mActivity.getSharedUtlis();


        ivPickUp.setOnClickListener(new View.OnClickListener() {
         
            public void onClick(View v) {

                if (mActivity != null) {
                    mActivity.hideColorFragment();
                }
            }
        });


        cbRubber.setOnCheckedChangeListener(this);
        cbPaint.setOnCheckedChangeListener(this);

        sbRubber.setOnSeekBarChangeListener(this);

        sbPainWidth.setOnSeekBarChangeListener(this);

        sbRubber.setProgress(mSharedUtlis.getInt(Shared.RubberWidthKey, 30));

        sbPainWidth.setProgress(mSharedUtlis.getInt(Shared.LineWidthKey, 5));

//        cbRubber.setChecked(mSharedUtlis.getBoolean(Shared.isRubberKey, false));
        
        cbPaint.setChecked(true);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar == sbRubber) {

            onRubberSizeChanghe(progress);

        } else if (seekBar == sbPainWidth) {

            onPaintSizeChanghe(progress);
        }

    }

    private void onPaintSizeChanghe(int progress) {

        //设置画笔宽度
        if (mSharedUtlis != null)
            mSharedUtlis.putInt(Shared.LineWidthKey, progress);
        progress = (int) (progress * 1.5);
        if (mDrawHelper != null)
            mDrawHelper.setLineWidth(progress);


    }

    private void onRubberSizeChanghe(int progress) {
        // 设置橡皮宽度
        if (mSharedUtlis != null)
            mSharedUtlis.putInt(Shared.RubberWidthKey, progress);

        progress = (int) (progress * 2.5);
        if (mDrawHelper != null)
            mDrawHelper.setRubberWidth(progress);


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        
        if (buttonView == cbRubber) {

            LogUtil.e(TAG, " cbRubber  CheckedChanged"  + String.valueOf(isChecked) ,false);
            
            if (mSharedUtlis != null)
                mSharedUtlis.putBoolean(Shared.isRubberKey, isChecked);
            
            if (isChecked ) {
                mDrawHelper.setMode(DrawWitch.draw_rubber);
            }

            cbPaint.setChecked(!isChecked);
        } 
        
        if (buttonView == cbPaint) {
            
            LogUtil.e(TAG, " cbPaint  CheckedChanged"  + String.valueOf(isChecked) ,false);
            
            if (mSharedUtlis != null)
                mSharedUtlis.putBoolean(Shared.isLineKey, isChecked);
            if (isChecked  ) {
                mDrawHelper.setMode(DrawWitch.draw_line);
            }
            cbRubber.setChecked(!isChecked);
        }
        
    }
}
