package com.zhoufan.express.bean;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by admin
 * Date on 2018/2/5
 * Class Describe:Task bean
 */

public class Task implements Serializable {
    /**
     * task_id : 1
     * task_name : 100
     * task_info : test
     * task_time : 2018-02-05 11:17:55
     * task_status : 1
     * task_user : 1
     * task_money : 5
     * task_school : 1
     * task_people : 飞
     * task_phone : 123456
     * status_id : 1
     * status_name : 未领取
     * user_id : 1
     * user_phone : 123456
     * user_name : 123456
     * user_sex : 1
     * user_info : 发送到发
     * user_password : e10adc3949ba59abbe56e057f20f883e
     * user_manager : 1
     * user_time : 2018-02-03 11:50:57
     * "eva_id": 2,
     * "eva_name": "中评"
     */

    private int task_id;
    private String task_name;
    private String task_info;
    private String task_time;
    private int task_status;
    private int task_user;
    private int task_money;
    private int task_school;
    private String task_people;
    private String task_phone;
    private int status_id;
    private String status_name;
    private int user_id;
    private String user_phone;
    private String user_name;
    private int user_sex;
    private String user_info;
    private String user_password;
    private int user_manager;
    private String user_time;
    private int eva_id;
    private String eva_name;

    public int getEva_id() {
        return eva_id;
    }

    public void setEva_id(int eva_id) {
        this.eva_id = eva_id;
    }

    public String getEva_name() {
        return eva_name;
    }

    public void setEva_name(String eva_name) {
        this.eva_name = eva_name;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_info() {
        return task_info;
    }

    public void setTask_info(String task_info) {
        this.task_info = task_info;
    }

    public String getTask_time() {
        return task_time;
    }

    public void setTask_time(String task_time) {
        this.task_time = task_time;
    }

    public int getTask_status() {
        return task_status;
    }

    public void setTask_status(int task_status) {
        this.task_status = task_status;
    }

    public int getTask_user() {
        return task_user;
    }

    public void setTask_user(int task_user) {
        this.task_user = task_user;
    }

    public int getTask_money() {
        return task_money;
    }

    public void setTask_money(int task_money) {
        this.task_money = task_money;
    }

    public int getTask_school() {
        return task_school;
    }

    public void setTask_school(int task_school) {
        this.task_school = task_school;
    }

    public String getTask_people() {
        return task_people;
    }

    public void setTask_people(String task_people) {
        this.task_people = task_people;
    }

    public String getTask_phone() {
        return task_phone;
    }

    public void setTask_phone(String task_phone) {
        this.task_phone = task_phone;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUser_manager() {
        return user_manager;
    }

    public void setUser_manager(int user_manager) {
        this.user_manager = user_manager;
    }

    public String getUser_time() {
        return user_time;
    }

    public void setUser_time(String user_time) {
        this.user_time = user_time;
    }

    @Override
    public String toString() {
        return user_name+"   "+user_info+"   "+task_name;
    }
}

