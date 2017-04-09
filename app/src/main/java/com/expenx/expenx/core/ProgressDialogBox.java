package com.expenx.expenx.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by skaveesh on 2017-04-09.
 */


public class ProgressDialogBox {

    private static ProgressDialog progress;

    public static void showProgressDialog(Context fromContext, String message){
        progress=new ProgressDialog(fromContext);
        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    public static void dismissProgressDialog(){
        progress.dismiss();
    }
}
