package com.zhoufan.express.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.gson.reflect.TypeToken;
import com.zhoufan.express.R;
import com.zhoufan.express.bean.Manager;
import com.zhoufan.express.presenter.HttpRequestServer;
import com.zhoufan.express.util.ResponseUtil;
import com.zhoufan.express.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.user_info_et)
    EditText userInfoEt;
    @BindView(R.id.rb_sex_men)
    RadioButton rbSexMen;
    @BindView(R.id.rb_sex_women)
    RadioButton rbSexWomen;
    @BindView(R.id.area_sp)
    Spinner areaSp;

    private static final String GET_USER_MANAGER_URL = "/user/userManager";
    private static final String REGISTER_URL = "/user/register";
    private ArrayList<Manager> managers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        HttpRequestServer.create(this).doGet(GET_USER_MANAGER_URL, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody, true)) {
                    managers = (ArrayList<Manager>) ResponseUtil.getByType(new TypeToken<ArrayList<Manager>>() {
                    }.getType());
                    String[] managerNames = new String[managers.size()];
                    for (int i = 0; i < managers.size(); i++) {
                        managerNames[i] = managers.get(i).getManager_name();
                    }
                    initSpinner(managerNames);
                }
            }
        });
    }

    private void initSpinner(String[] managerNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, managerNames);
        areaSp.setAdapter(adapter);
    }

    @OnClick(R.id.sign_up_btn)
    public void onViewClicked() {
        register();
    }

    private void register() {
        String name = nameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        String userInfo = userInfoEt.getText().toString().trim();
        String sexId = "";
        if (rbSexMen.isChecked()) {
            sexId = "1";
        } else {
            sexId = "2";
        }
        int index = areaSp.getSelectedItemPosition();
        String managerId = String.valueOf(managers.get(index).getManager_id());
        Map<String, String> map = new HashMap<>();
        map.put("user_name", name);
        map.put("user_password", password);
        map.put("user_phone", phone);
        map.put("user_sex", sexId);
        map.put("user_info", userInfo);
        map.put("user_manager", managerId);
        HttpRequestServer.create(this).doGetWithParams(REGISTER_URL, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.getInstance().networkError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody,false)){
                    ToastUtil.getInstance().log("注册成功");
                }else{
                    ToastUtil.getInstance().log("注册失败，手机号不允许重复");
                }
            }
        });
    }
}
