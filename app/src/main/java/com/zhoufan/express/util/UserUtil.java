package com.zhoufan.express.util;

import com.zhoufan.express.bean.User;

/**
 * Created by admin
 * Class Describe :
 * <p> 保存当前用户
 * on 2017/10/28.
 */

public class UserUtil {
    private static User user;
    public static void setUser(User user){
        UserUtil.user = user;
    }
    public static User getCurrentUser(){
        return user;
    }
}
