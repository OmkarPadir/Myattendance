package com.example.teproject.myattendance;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * Created by vasantkapare on 27-10-2017.
 */

public class InternetHandler {

    Context AppContext;

    public InternetHandler(Context appContext) {
        AppContext = appContext;
        internetErrorHandler();

    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)AppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public void showAlert()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(AppContext).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Please connect to internet!!!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AppContext.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                });
        alertDialog.show();
    }

    public void internetErrorHandler()
    {
        if(!isConnectedToInternet())
        {
            showAlert();
        }
    }


}
