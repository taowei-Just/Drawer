package huanyang.gloable.gloable.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import huanyang.gloable.gloable.dialog.TimeingDialog;
import huanyang.gloable.gloable.dialog.callback.DialogDismissCallback;

/**
 * Created by Administrator on 2017-11-01.
 */

public class DialogUtil {


    public static ProgressDialog showProcessDialog(Context context, String string, ProgressDialog progressDialog, boolean cancelable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
        progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setTitle(string);


        if (!cancelable) {
            progressDialog.setCancelable(false);

        }
        progressDialog.show();
        return progressDialog;
    }

    public static ProgressDialog showProcessDialog(Context context, String string, ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
        progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setTitle(string);

        progressDialog.show();
        return progressDialog;
    }

    public static ProgressDialog showProcessDialog(Context context, String string, ProgressDialog progressDialog, DialogInterface.OnCancelListener listener) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
        progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setTitle(string);

        progressDialog.setOnCancelListener(listener);

        progressDialog.show();
        return progressDialog;
    }

    public static void hideProgress(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public static AlertDialog showAlertDialog(AlertDialog alertDialog, Context context, CharSequence message, CharSequence title,
                                              DialogInterface.OnClickListener listener, String buttonText, boolean cancelAble) {
        if (alertDialog != null && alertDialog.isShowing())
            if (alertDialog != null && alertDialog.isShowing())
                alertDialog.dismiss();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);

        alertDialog = alertBuilder.show();

        return alertDialog;
    }


    public static void cancelAlertDialog(AlertDialog alertDialog) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public static TimeingDialog showTimeDialog(TimeingDialog timeingDialog,
                                               Context context,
                                               Handler handler,
                                               String title,
                                               String message,
                                               String buttonMs,
                                               DialogInterface.OnClickListener listener,
                                               DialogDismissCallback callback) {
        if (timeingDialog != null && !timeingDialog.isInterrupted()) {
            timeingDialog.interrupt();
        }
        timeingDialog = new TimeingDialog(context, handler, title, message, buttonMs, listener, callback);
        timeingDialog.show();
        return timeingDialog;
    }
}
