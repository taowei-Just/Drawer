package huanyang.gloable.gloable.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;

import huanyang.gloable.gloable.dialog.callback.DialogDismissCallback;
import huanyang.gloable.gloable.utils.LogUtil;

public class TimeingDialog extends Thread implements OnDismissListener {

    String TAG = "TimeingDialog";

    long showTime = 5 * 1000;

    Context context;
    String title;
    CharSequence message;
    String buttonMs;
    OnClickListener listener;
    Handler handler;
    AlertDialog dialog = null;
    DialogDismissCallback callback;

    private String buttonS;

    private OnClickListener slistener;
    
    boolean cancelAble = true ;

    public TimeingDialog(Context context, Handler handler, String title,
                         String message, String buttonMs, OnClickListener listener, DialogDismissCallback callback) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.buttonMs = buttonMs;
        this.listener = listener;
        this.handler = handler;
        this.callback = callback;
    }

    public TimeingDialog(Context context, Handler handler, String title,
                         String message, String buttonMs, String buttonS, OnClickListener listener,
                         OnClickListener slistener, DialogDismissCallback callback) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.buttonMs = buttonMs;
        this.listener = listener;
        this.handler = handler;
        this.callback = callback;
        this.buttonS = buttonS;
        this.slistener = slistener;
    }

    public void setCancelAble(boolean cancelAble) {
        this.cancelAble = cancelAble;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    private String msg = "";

    public void setMsg(CharSequence msg) {
        this.message = msg;
    }

    public void run() {

        handler.post(new Runnable() {

            public void run() {

                if (isInterrupted()) {

                    return;
                }

                try {


                    dialog = DialogTools.showDialog(context, title, message,
                            buttonMs, listener, buttonS, slistener);

                    dialog.setOnDismissListener(TimeingDialog.this);
                    
                    dialog.setCancelable(cancelAble);

                } catch (Exception e) {
                    e.printStackTrace();

                    LogUtil.e(TAG, " 弹窗异常： " + e.toString());
                    
                }
            }
        });

        try {
            Thread.sleep(showTime);
            
            handler.post(new Runnable() {

                public void run() {

                    if (dialog != null && dialog.isShowing()) {

                        try {
                            
                            dialog.dismiss();
                            
                        } catch (Exception e) {
                            
                            e.printStackTrace();
                        
                        }

                    }

                }
            });

        } catch (Exception e) {

            e.printStackTrace();

            if (handler != null) {
                handler.post(new Runnable() {
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }

                    }
                });
            }
        }


    }

    @Override
    public void onDismiss(DialogInterface arg0) {
        // TODO Auto-generated method stub

        if (callback != null) {
            callback.onDismiss();

            handler.post(new Runnable() {

                public void run() {
                    if (callback!=null)
                    callback.onDismissRunOnUI();

                }
            });
        }

        if (!isInterrupted())
            this.interrupt();

    }

    public void show() {
        
        try{
            
            TimeingDialog.this.start();
            
        }catch (Exception e){
            
            e.printStackTrace();
        }
        
    }
    
    public void dismiss(){
        
        if (!isInterrupted()){
            interrupt();
        }
        
    }
}
