package marblog.cn;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * @author Marblog
 */
@SuppressLint("Registered")
public class SmsServiceB extends Service {

    SmsBroadcastReceiver mReceiver = new SmsBroadcastReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mReceiver, intentFilter);
        Log.e("SmsBService", "create service");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        Log.e("SmsBService", "destroy service");
    }
}
