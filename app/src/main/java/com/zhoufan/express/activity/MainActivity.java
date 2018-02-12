package com.zhoufan.express.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.zhoufan.express.R;
import com.zhoufan.express.bean.Task;
import com.zhoufan.express.presenter.HttpRequestServer;
import com.zhoufan.express.util.ResponseUtil;
import com.zhoufan.express.util.ToastUtil;
import com.zhoufan.express.util.UserUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.task_rv)
    RecyclerView taskRv;
    @BindView(R.id.null_rl)
    RelativeLayout nullRl;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public static String GET_ALL_TASK_URL = "/user/searchTask";
    private boolean isExit = false;
    private ArrayList<Task> taskList;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        //设置toolbar
        toolbar.setSubtitle(UserUtil.getCurrentUser().getUser_name());
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("user_school", String.valueOf(UserUtil.getCurrentUser().getSchool_id()));
        HttpRequestServer.create(this).doGetWithParams(GET_ALL_TASK_URL, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody, true)) {
                    taskList = (ArrayList<Task>) ResponseUtil.getByType(new TypeToken<ArrayList<Task>>() {
                    }.getType());
                    initView();
                }
            }
        });
    }

    private void initView() {
        if (taskList == null || taskList.size() == 0) {
            nullRl.setVisibility(View.VISIBLE);
        } else {
            nullRl.setVisibility(View.GONE);
        }
        taskRv.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter adapter = new CommonAdapter<Task>(this, R.layout.task_show, taskList) {
            @Override
            protected void convert(ViewHolder holder, Task task, int position) {
                //设置item显示
                holder.setText(R.id.task_date, "任务时间："+task.getTask_time());
                holder.setText(R.id.task_info, "任务描述："+task.getTask_info());
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // 点击item操作
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                intent.putExtra("task", taskList.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        taskRv.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //监听返回键，点击两次返回键退出，退出间隔为2000毫秒
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.getInstance().log("再次点击返回键退出");
            Message message = Message.obtain();
            message.what = 4;
            handler.sendMessageDelayed(message, 2000);
        } else
            finish();

    }

    @OnClick({R.id.tv_my_task, R.id.fab,R.id.tv_my_order_task})
    public void onViewClicked(View view) {
        Intent intent = new Intent(MainActivity.this,MyTaskActivity.class);

        switch (view.getId()) {
            case R.id.tv_my_task:
                intent.putExtra("model",0);
                startActivity(intent);
                break;
            case R.id.fab:
                startActivity(new Intent(MainActivity.this,TaskActivity.class));
                break;
            case R.id.tv_my_order_task:
                intent.putExtra("model",1);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
