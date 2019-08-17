package com.example.boostcourseaceproject4.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkStatusHelper {
    static final String TAG = "NetworkStatusHelperT";
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 3;

    public static boolean getConnectivityStatus(Context context){ //해당 context의 서비스를 사용하기위해서 context객체를 받는다.
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null){
            int type = networkInfo.getType();
            if(type == ConnectivityManager.TYPE_MOBILE){//쓰리지나 LTE로 연결된것(모바일을 뜻한다.)
                Log.d(TAG, "쓰리지나 LTE 연결상태");
                return true;
            }else if(type == ConnectivityManager.TYPE_WIFI){//와이파이 연결된것
                Log.d(TAG, "와이파이 연결상태");
                return true;
            }
        }
        Log.d(TAG, "네트워크 연결 안된상태");
        return false;  //연결이 되지않은 상태
    }
}
