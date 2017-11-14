package test.twocamera.fragment;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import huanyang.gloable.gloable.dialog.TimeingDialog;
import huanyang.gloable.gloable.utils.DialogUtil;
import huanyang.gloable.gloable.utils.FileUtils;
import huanyang.gloable.gloable.utils.FragmentTools;
import huanyang.gloable.gloable.utils.ImageUtil;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.ToastTools;
import test.twocamera.MainActivity;
import test.twocamera.R;
import test.twocamera.custom.DrawHelper;

/**
 * Created by Administrator on 2017-11-06.
 */

public class TopMoreFragment extends Fragment implements AdapterView.OnItemClickListener {


    private MainActivity mActivity;
    private View mRootFragmentView;
    private ListView lvMore;

    String[] textS = new String[]{"你的镜子", "设置", "保存", "更多", "分享", "关于", "终极大惊喜！"};
    int[] picIdS = new int[]{R.mipmap.mirror, R.mipmap.setting, R.mipmap.save, R.mipmap.more, R.mipmap.share, R.mipmap.about, R.mipmap.about};
    private String TAG = getClass().getName();
    private DrawHelper mDrawHelper;

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy");

    }

    @Override
    public void onResume() {
        super.onResume();

        LogUtil.e(TAG, "onResume");

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {

        super.onPause();
        LogUtil.e(TAG, "onPause");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_layout_more, null);
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
        lvMore = mRootFragmentView.findViewById(R.id.lv_more);


    }

    private void initdata() {

        MyAdapter adapter = new MyAdapter();

        lvMore.setAdapter(adapter);
        lvMore.setOnItemClickListener(this);

        mDrawHelper = mActivity.getDrawHelper();


    }


    String mString = "";

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:

                FragmentTools.replaceFragment(mActivity, new MirrorFragment(), R.id.fl_main_full, MainActivity.MIRROR_FRAGMENT_TAG);

                break;
            case 1:

                mString = "1";
                ToastTools.showShort(mActivity, " 点击下一个试试 ！(=^ ^=)");

                break;

            case 2:
                mString += "2";

//                if (mString.equals("12")) {
//                    ToastTools.showShort(mActivity, "傻妞 继续往下点！\n╭~~~╮\n" + "(o^.^o)");
//
//
//                } else {
//
//                    ToastTools.showShort(mActivity, "妞妞 要按顺来哦！\n(▰˘◡˘▰)");
//
//                }

                if(saveImage()!=null ){
                    
                    ToastTools.showShort(mActivity,"保存成功！");
                }else {
                    ToastTools.showShort(mActivity,"保存失败！");
                    
                    
                }
                
                break;

            case 3:
                mString += "3";
                if (mString.equals("123")) {

                    ToastTools.showShort(mActivity, "傻妞 傻妞 不要停\n\\（￣︶￣）/ ！");

                } else {
                    ToastTools.showShort(mActivity, "傻妞 从头再来哦！\n(ΘωΘ)");

                }
                
                break;
            case 4:
                mString += "4";
        
                sharedQQ();
                mActivity.hideMoreFragment();

                break;

            case 5:
                mString += "5";
                if (mString.equals("12345")) {


                    TimeingDialog timeingDialog = DialogUtil.showTimeDialog(null, mActivity,
                            new Handler(),
                            "傻不傻", " 什么都没有，哈哈哈！\nㄟ( ▔, ▔ )ㄏ ", "还点我\n￣ω￣=", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }, null
                    );
                    timeingDialog.setShowTime(1000000 * 1000);
                    timeingDialog.setCancelAble(false);


                } else {

                    ToastTools.showShort(mActivity, "傻妞 一步一步来不要偷懒 ！\n(づ ●─● )づ");


                }

                break;


        }


    }

    private void showToast() {


    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return textS.length;
        }

        @Override
        public Object getItem(int position) {
            return textS[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(mActivity, R.layout.more_list_item, null);

            TextView textView = view.findViewById(R.id.tv_item_text);
            ImageView ivPic = view.findViewById(R.id.iv_pic);
            ivPic.setImageResource(picIdS[position]);
            textView.setText(textS[position]);

            return view;
        }

    }

    protected File saveImage() {

        File file =null ;
        if (mDrawHelper != null) {
            Bitmap drawerBitmap = mDrawHelper.getDrawerBitmap();
            if (drawerBitmap != null) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                        + mActivity.getPackageName() + "/image/傻妞绘画_" +
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())) + ".png";
                 file = FileUtils.saveImage(path, drawerBitmap);
         
                
            }
        }
        return  file ;
    }

    private void sharedQQ() {

        if (mDrawHelper != null) {
            File file = saveImage();
            if(file!=null) {
                Intent share = new Intent(Intent.ACTION_SEND);
                ComponentName component = new ComponentName("com.tencent.mobileqq",
                        "com.tencent.mobileqq.activity.JumpActivity");
                share.setComponent(component);

                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                share.setType("*/*");
                startActivity(Intent.createChooser(share, "发送给他"));

                ToastTools.showShort(mActivity,"已打开页面，分享给他们你的大作吧！");

            }else {
                
                ToastTools.showShort(mActivity,"分享失败呢！");
                
            }
 
        } else {

            ToastTools.showShort(mActivity, "得不到你的绘画呢！");

        }


    }

}
