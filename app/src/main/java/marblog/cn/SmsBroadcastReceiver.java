package marblog.cn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.util.Log;
import java.util.Objects;


/**
 * @author Marblog
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static String INTENT_PHONE_NUMBER = "PHONE_NUMBER";
    public static String INTENT_MESSAGE_CONTENT = "MESSAGE_CONTENT";
    public static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), SMS_RECEIVED)) {
            SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            if (smsMessages.length != 0) {
                // 获取设定的转发目标
                String phoneNumber = PreferenceHelper.getInstance(context).getPhoneNumber();
                if (phoneNumber != null) {
                    for (SmsMessage smsMessage : smsMessages) {
                        String content = "发送方：" + smsMessage.getOriginatingAddress() + " "
                                + "短信内容：" + smsMessage.getMessageBody();
                        Intent smsIntent = new Intent(context, SmsService.class);
                        smsIntent.putExtra(INTENT_PHONE_NUMBER, phoneNumber);
                        smsIntent.putExtra(INTENT_MESSAGE_CONTENT, content);
                        context.startService(smsIntent);
                    }
                }
            }
        }
    }
}
