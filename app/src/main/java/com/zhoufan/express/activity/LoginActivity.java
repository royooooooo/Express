package com.zhoufan.express.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhoufan.express.R;
import com.zhoufan.express.presenter.HttpRequestServer;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.textView)
    TextView textView;

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
                break;
        }
    }

    private void login() {
        String phone = phoneEt.getText().toString().trim();
        String password = phoneEt.getText().toString().trim();
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("password",password);
//        HttpRequestServer.create(this).doGetWithParams();
    }
}
