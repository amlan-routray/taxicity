package com.appsters.igit.taxicity;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection {
    public static boolean isEnabled(Context context)
    {
        ConnectivityManager connec=(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTED
                || connec.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTING
                || connec.getNetworkInfo(1).getState()== NetworkInfo.State.CONNECTED
                || connec.getNetworkInfo(1).getState()== NetworkInfo.State.CONNECTING){
            return true;
        }
        else if (connec.getNetworkInfo(0).getState()== NetworkInfo.State.DISCONNECTED
                || connec.getNetworkInfo(0).getState()== NetworkInfo.State.DISCONNECTED )
        {
            return false;
        }
        return false;
    }
}
