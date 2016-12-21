package utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by HL_TH on 12/16/2016.
 */

public class common {
    Activity activity;
    public static ProgressDialog progressDialog;
    public common(Activity activity) {
        this.activity = activity;
    }
    public static void initProgress(Activity activity)
    {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Map loading");
        progressDialog.setMessage("Please wait ........");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    public static void dismissProgress()
    {
        progressDialog.dismiss();
    }
}
