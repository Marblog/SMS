package marblog.cn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * @author Marblog
 * @date 2019.3.6
 */
public class MainActivity extends AppCompatActivity {

    public EditText mTransferPhoneNumber;
    public Button mSetButton;
    public ConstraintLayout rootView;
    public TextView mShowTransferPhoneNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        init();

        mSetButton.setOnClickListener(view -> {
            // 检查电话号码
            if (checkPhoneNumber(mTransferPhoneNumber.getText().toString())) {
                PreferenceHelper.getInstance(MainActivity.this).writePhoneNumber(mTransferPhoneNumber.getText().toString());
                mShowTransferPhoneNumber.setText(String.format("当前转发手机号：%s", PreferenceHelper.getInstance(MainActivity.this).getPhoneNumber()));
            } else {
                showSnackBar();
            }
        });

        Intent intent = new Intent(this, SmsServiceB.class);
        startService(intent);


    }

    private void init() {
        mTransferPhoneNumber = findViewById(R.id.transfer_phone_number);
        mSetButton = findViewById(R.id.transfer_start_button);
        rootView = findViewById(R.id.root_view);
        mShowTransferPhoneNumber = findViewById(R.id.transfer_phone_number_textview);
        mShowTransferPhoneNumber.setText(String.format("当前转发手机号：%s", PreferenceHelper.getInstance(MainActivity.this).getPhoneNumber()));
    }

    private boolean checkPhoneNumber(String text) {
        return ((text != null) && (text.length() == 11));
    }

    private void showSnackBar() {
        Snackbar.make(rootView, R.string.error_input_phone_number, Snackbar.LENGTH_LONG).show();
    }

}
