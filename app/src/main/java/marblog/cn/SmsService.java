package marblog.cn;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;

import java.util.ArrayList;


/**
 * @author Marblog
 */
@SuppressLint("Registered")
public class SmsService extends IntentService {

    public SmsService() {
        super("SmsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String phoneNumber = intent.getStringExtra(SmsBroadcastReceiver.INTENT_PHONE_NUMBER);
            String content = intent.getStringExtra(SmsBroadcastReceiver.INTENT_MESSAGE_CONTENT);

            String nu1 = intent.getStringExtra(SmsBroadcastReceiver.INTENT_PHONE_NUMBER);

            if (phoneNumber != null) {

                sendSms(phoneNumber, content);
                sendSms2(nu1, content);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_mail)
                        .setContentTitle(getString(R.string.notification_transfer_message))
                        .setContentText("短信内容:" + content);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                int notificationId = 100;
                notificationManager.notify(notificationId, builder.build());
            }
        }
    }

    /**
     *  发送短信
     * @param phoneNumber  手机号
     * @param originContent  内容
     */
    public void sendSms(String phoneNumber, String originContent) {

        ArrayList<String> divideContents = SmsManager.getDefault().divideMessage(originContent);

        for (String content : divideContents) {

            SmsManager.getDefault().sendTextMessage(phoneNumber, null, content, null, null);
        }
    }

    public void sendSms2(String num2, String originContent) {
        ArrayList<String> divideContents = SmsManager.getDefault().divideMessage(originContent);
        for (String content : divideContents) {

            SmsManager.getDefault().sendTextMessage(num2, null, content, null, null);
        }
    }
}
