package marblog.cn;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
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
            if (phoneNumber != null) {
                Log.d("ServicePhoneNumber", phoneNumber);
                Log.d("ServiceSmsContent", content);
                sendSms(phoneNumber, content);

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

    void sendSms(String phoneNumber, String originContent) {
        ArrayList<String> divideContents = SmsManager.getDefault().divideMessage(originContent);
        for (String content : divideContents) {
            SmsManager.getDefault().sendTextMessage(
                    phoneNumber,
                    null,
                    content,
                    null,
                    null
            );
        }
    }
}
