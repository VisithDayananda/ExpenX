package com.expenx.expenx.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.support.design.widget.Snackbar;

import com.expenx.expenx.R;

/**
 * Created by skaveesh on 2017-04-09.
 */


public class MessageOutput {

    private static ProgressDialog progress;

    public static void showProgressDialog(Context fromContext, String message){
        Drawable drawable = new ProgressBar(fromContext).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(fromContext, R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);

        progress=new ProgressDialog(fromContext);
        progress.setMessage(message);
        progress.setIndeterminate(true);
        progress.setIndeterminateDrawable(drawable);
        progress.setCanceledOnTouchOutside(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    public static void dismissProgressDialog(){
        progress.dismiss();
    }

    public static void showSnackbarLongDuration(Activity fromActivity, String message){
        Snackbar snackbar = Snackbar
                .make(fromActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
