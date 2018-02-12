package com.zhoufan.express.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.zhoufan.express.R;
import com.zhoufan.express.bean.Task;
import com.zhoufan.express.presenter.HttpRequestServer;
import com.zhoufan.express.util.ResponseUtil;
import com.zhoufan.express.util.ToastUtil;
import com.zhoufan.express.util.UserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class TaskActivity extends AppCompatActivity {

    private static final String GET_TASK_URL = "/user/orderTask";
    private static final String FINISH_TASK_URL = "/user/completeTask";
    private static final String ADD_EVA_URL = "/user/addEVA";
    private static final String MORE_TASKINFO_URL = "/user/moreTask";
    private static final String ADD_TASK_URL = "/user/addTask";
    @BindView(R.id.task_time_et)
    EditText taskTimeEt;
    @BindView(R.id.task_info_et)
    EditText taskInfoEt;
    @BindView(R.id.task_money_et)
    EditText taskMoneyEt;
    @BindView(R.id.task_people_et)
    EditText taskPeopleEt;
    @BindView(R.id.task_people_phone_et)
    EditText taskPeoplePhoneEt;
    @BindView(R.id.employee_tv)
    TextView employeeTv;
    @BindView(R.id.eva_rv)
    RecyclerView evaRv;
    @BindView(R.id.get_goods_tv)
    TextView getGoodsTv;
    @BindView(R.id.add_task_btn)
    Button addTaskBtn;
    @BindView(R.id.get_task_btn)
    Button getTaskBtn;
    @BindView(R.id.employee_ll)
    LinearLayout employeeLl;
    @BindView(R.id.eva_ll)
    LinearLayout evaLl;
    @BindView(R.id.focuce_ll)
    LinearLayout focuceLl;
    @BindView(R.id.get_goods_rl)
    RelativeLayout getGoodsRl;
    @BindView(R.id.get_task_rl)
    RelativeLayout getTaskRl;
    @BindView(R.id.finish_task_btn)
    Button finishTaskBtn;
    @BindView(R.id.finish_task_rl)
    RelativeLayout finishTaskRl;
    @BindView(R.id.add_task_rl)
    RelativeLayout addTaskRl;
    @BindView(R.id.add_eva_rl)
    RelativeLayout addEvaRl;
    @BindView(R.id.eva_good_rb)
    RadioButton evaGoodRb;
    @BindView(R.id.eva_common_rb)
    RadioButton evaCommonRb;
    @BindView(R.id.eva_bad_rb)
    RadioButton evaBadRb;
    @BindView(R.id.task_get_number_et)
    EditText taskGetNumberEt;
    @BindView(R.id.task_get_number_ll)
    LinearLayout taskGetNumberLl;
    @BindView(R.id.eva_rg)
    RadioGroup evaRg;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }

    /**
     * 初始化Toolbar和home键
     */
    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("任务");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        switch (task.getTask_status()) {
            case 1:
                if (task.getUser_id() != UserUtil.getCurrentUser().getUser_id())
                    getTaskRl.setVisibility(View.VISIBLE);
                break;
            case 2:
                employeeLl.setVisibility(View.VISIBLE);
                employeeTv.setText(task.getUser_name());
                if (task.getUser_id() == UserUtil.getCurrentUser().getUser_id()) {
                    getGoodsRl.setVisibility(View.VISIBLE);
                    getGoodsTv.setText("取货码：" + task.getTask_name());
                }else if (task.getTask_user() == (UserUtil.getCurrentUser().getUser_id())){
                    finishTaskRl.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                if (task.getEva_id() == 1) {
                    getGoodsRl.setVisibility(View.VISIBLE);
                    getGoodsTv.setText("雇主评价：好评");
                } else if (task.getEva_id() == 2) {
                    getGoodsRl.setVisibility(View.VISIBLE);
                    getGoodsTv.setText("雇主评价：中评");
                } else if (task.getEva_id() == 3) {
                    getGoodsRl.setVisibility(View.VISIBLE);
                    getGoodsTv.setText("雇主评价：差评");
                } else {
                    addEvaRl.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private void initData() {
        task = (Task) getIntent().getSerializableExtra("task");
        if (task == null) {
            taskGetNumberLl.setVisibility(View.VISIBLE);
            addTaskRl.setVisibility(View.VISIBLE);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("task_id", String.valueOf(task.getTask_id()));
        HttpRequestServer.create(this).doGetWithParams(MORE_TASKINFO_URL, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody, true)) {
                    task = (Task) ResponseUtil.getByType(Task.class);
                    KLog.i(task.getEva_id());
                    setData();
                }
            }
        });
    }

    private void setData() {
        taskTimeEt.setText(task.getTask_time());
        taskTimeEt.setFocusable(false);
        taskInfoEt.setText(task.getTask_info());
        taskInfoEt.setFocusable(false);
        taskMoneyEt.setText(String.valueOf(task.getTask_money()));
        taskMoneyEt.setFocusable(false);
        taskPeopleEt.setText(task.getTask_people());
        taskPeopleEt.setFocusable(false);
        taskPeoplePhoneEt.setText(task.getTask_phone());
        taskPeoplePhoneEt.setFocusable(false);
        initView();
    }

    @OnClick({R.id.add_task_btn, R.id.get_task_btn, R.id.finish_task_btn, R.id.add_eva_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_task_btn:
                addTask();
                break;
            case R.id.get_task_btn:
                get_task();
                break;
            case R.id.finish_task_btn:
                finishTask();
                break;
            case R.id.add_eva_btn:
                addEva();
                break;
        }
    }

    private void addTask() {
        String task_name = taskGetNumberEt.getText().toString().trim();
        String task_info = taskInfoEt.getText().toString().trim();
        String task_money = taskMoneyEt.getText().toString().trim();
        String task_people = taskPeopleEt.getText().toString().trim();
        String task_phone = taskPeoplePhoneEt.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("task_name", task_name);
        map.put("task_info", task_info);
        map.put("task_money", task_money);
        map.put("task_people", task_people);
        map.put("task_phone", task_phone);
        map.put("task_user", String.valueOf(UserUtil.getCurrentUser().getUser_id()));
        map.put("task_school", String.valueOf(UserUtil.getCurrentUser().getSchool_id()));
        HttpRequestServer.create(this).doGetWithParams(ADD_TASK_URL, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody, false)) {
                    ToastUtil.getInstance().log("添加任务成功");
                    refreshAty();

                }
            }
        });
    }

    private void addEva() {
        Map<String, String> map = new HashMap<>();
        String task_eva;
        if (evaGoodRb.isChecked()) {
            task_eva = "1";
        } else if (evaCommonRb.isChecked()) {
            task_eva = "2";
        } else {
            task_eva = "3";
        }
        map.put("re_task_id", String.valueOf(task.getTask_id()));
        map.put("task_eva", task_eva);
        HttpRequestServer.create(this).doGetWithParams(ADD_EVA_URL, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody, false)) {
                    ToastUtil.getInstance().log("添加评论成功");
                    refreshAty();

                }
            }
        });
    }

    private void finishTask() {
        Map<String, String> map = new HashMap<>();
        map.put("task_id", String.valueOf(task.getTask_id()));
        HttpRequestServer.create(this).doGetWithParams(FINISH_TASK_URL, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody, false)) {
                    ToastUtil.getInstance().log("已完成任务");
                    refreshAty();

                }
            }
        });
    }

    private void get_task() {
        Map<String, String> map = new HashMap<>();
        map.put("re_task_id", String.valueOf(task.getTask_id()));
        map.put("re_user_id", String.valueOf(UserUtil.getCurrentUser().getUser_id()));
        HttpRequestServer.create(this).doGetWithParams(GET_TASK_URL, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody, false)) {
                    ToastUtil.getInstance().log("领取任务成功");
                    refreshAty();
                }
            }
        });
    }

    public void refreshAty() {
        finish();
        Intent intent = new Intent(TaskActivity.this, TaskActivity.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
