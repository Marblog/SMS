package marblog.cn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;


/**
 * @author Marblog
 */
public class PreferenceHelper {

    private static String PREFES_PHONE_NUMBER_ID = "SMS_TRANSFER_PHONE_NUMBER";
    private SharedPreferences mSharedPreferences;
    private static PreferenceHelper instance;


    static PreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceHelper(context);
        }
        return instance;
    }

    private PreferenceHelper(Context context) {
        // SharedPreference File Name
        String prefesPhoneNumberName = "PREFES_SMS_TRANSFER_APP";
        mSharedPreferences = context.getSharedPreferences(prefesPhoneNumberName, 0);
    }

    /**
     *  保存电话号码
     */
    void writePhoneNumber(String phoneNumber) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREFES_PHONE_NUMBER_ID, phoneNumber);
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    boolean isPhoneNumberWrote() {
        return Objects.requireNonNull(mSharedPreferences.getString(PREFES_PHONE_NUMBER_ID, "")).isEmpty();
    }

    public String getPhoneNumber() {
        return mSharedPreferences.getString(PREFES_PHONE_NUMBER_ID, "");
    }
}
