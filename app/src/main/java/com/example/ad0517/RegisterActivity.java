package com.example.ad0517;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private EditText returnCode;
    private Button btnSendMessage;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toast.makeText(RegisterActivity.this, "需要注册方能评论", Toast.LENGTH_SHORT).show();
        phoneNumber = findViewById(R.id.phone_number);
        returnCode = findViewById(R.id.return_code);
        btnSendMessage = findViewById(R.id.btn_send_message);
        btnRegister = findViewById(R.id.btn_register);


        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAndLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yanzhengReturnCode();
            }
        });
    }

    private void yanzhengReturnCode() {
//        BmobSMS.verifySmsCode(phoneNumber.getText().toString(), returnCode.getText().toString(), new UpdateListener() {
//            @Override
//            public void done(BmobException ex) {
//                if (ex == null) {//短信验证码已验证成功
//                    Log.e("smile", "验证通过");
//                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.e("smile", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
//                }
//            }
//        });
        BmobUser.signOrLoginByMobilePhone(phoneNumber.getText().toString(), returnCode.getText().toString(), new LogInListener<BmobUser>() {

            @Override
            public void done(BmobUser user, BmobException e) {
                if(user!=null){
                    Log.e("smile","用户登陆成功");
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Log.e("e",e.toString());
                }
            }
        });
    }

    private void signAndlogin() {
    }

    private void RegisterAndLogin() {
//            发送验证码。
        BmobSMS.requestSMSCode(phoneNumber.getText().toString(), "yanzhengma", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {//验证码发送成功
                    Log.e("smile", "短信id：" + smsId);
                    Toast.makeText(RegisterActivity.this, "请查看手机信息。", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterActivity.this, "请检查您的手机号是否正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
