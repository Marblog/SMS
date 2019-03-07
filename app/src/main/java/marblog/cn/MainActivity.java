package marblog.cn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * @author Marblog
 */
public class MainActivity extends AppCompatActivity {

    EditText mTransferPhoneNumber;
    Button mSetButton;
    ConstraintLayout rootView;
    TextView mShowTransferPhoneNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTransferPhoneNumber = findViewById(R.id.transfer_phone_number);
        mSetButton = findViewById(R.id.transfer_start_button);
        rootView = findViewById(R.id.root_view);
        mShowTransferPhoneNumber = findViewById(R.id.transfer_phone_number_textview);

        mShowTransferPhoneNumber.setText(String.format("当前转发手机号：%s", PreferenceHelper.getInstance(MainActivity.this).getPhoneNumber()));
        SmsServiceB smsBService = new SmsServiceB();

        mSetButton.setOnClickListener(view -> {
            // check phone number
            if (checkPhoneNumber(mTransferPhoneNumber.getText().toString())) {
                PreferenceHelper.getInstance(MainActivity.this).writePhoneNumber(mTransferPhoneNumber.getText().toString());
                mShowTransferPhoneNumber.setText(String.format("当前转发手机号：%s", PreferenceHelper.getInstance(MainActivity.this).getPhoneNumber()));
            } else {
                showSnackBar(R.string.error_input_phone_number);
            }
        });

        Intent intent = new Intent(this, SmsServiceB.class);
        startService(intent);


    }

    private boolean checkPhoneNumber(String text) {
        return ((text != null) && (text.length() == 11));
    }

    private void showSnackBar(int textId) {
        Snackbar.make(rootView, textId, Snackbar.LENGTH_LONG).show();
    }

}
