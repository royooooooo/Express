package com.zhoufan.express.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

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
import okhttp3.ResponseBody;
import rx.Subscriber;

public class MyTaskActivity extends AppCompatActivity {

    private String GET_MY_TASKS = "";
    ArrayList<Task> myTasks;
    @BindView(R.id.rv_my_tasks)
    RecyclerView rvMyTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        ButterKnife.bind(this);
        if (getIntent().getIntExtra("model",0) == 1){
            GET_MY_TASKS = "/user/myOrderTask";

        }else{
            GET_MY_TASKS = "/user/myTask";
        }
        initToolbar();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(UserUtil.getCurrentUser().getUser_id()));
        HttpRequestServer.create(this).doGetWithParams(GET_MY_TASKS, map, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastUtil.getInstance().networkError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (ResponseUtil.verify(responseBody, true)) {
                    myTasks = (ArrayList<Task>) ResponseUtil.getByType(new TypeToken<ArrayList<Task>>() {
                    }.getType());
                    initView();
                }
            }
        });
    }

    private void initView() {
        rvMyTasks.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<Task> adapter = new CommonAdapter<Task>(this,R.layout.task_show,myTasks) {
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
                Intent intent = new Intent(MyTaskActivity.this,TaskActivity.class);
                intent.putExtra("task", myTasks.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvMyTasks.setAdapter(adapter);
    }

    /**
     * 初始化Toolbar和home键
     */
    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("我的任务");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
