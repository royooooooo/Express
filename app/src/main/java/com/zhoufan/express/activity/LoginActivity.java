package com.zhoufan.express.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.socks.library.KLog;
import com.zhoufan.express.R;
import com.zhoufan.express.bean.User;
import com.zhoufan.express.presenter.HttpRequestServer;
import com.zhoufan.express.util.ResponseUtil;
import com.zhoufan.express.util.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.textView)
    TextView textView;

    private final String  LOGIN_URL = "/user/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_btn, R.id.signUp_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.signUp_btn:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private void login() {
        String phone = phoneEt.getText().toString().trim();
        String password = phoneEt.getText().toString().trim();
        Map<String,String> map = new HashMap<>();
        map.put("user_phone",phone);
        map.put("user_password",password);
        HttpRequestServer.create(this).doGetWithParams(LOGIN_URL, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody,true)){
                    User user = (User) ResponseUtil.getByType(User.class);
                    UserUtil.setUser(user);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
}
