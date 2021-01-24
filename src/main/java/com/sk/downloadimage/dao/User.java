package com.sk.downloadimage.dao;

import com.sk.downloadimage.utils.LogUtils;
import com.sk.downloadimage.utils.MyBatisUtils;

public class User {
    private String userId;
    private String userName;
    private String userPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }

    public static void getUser(){
        /* 根据——key查找 */
        String statement = "com.springdemo.mapping.userMapper.getUser";
        //映射sql的标识字符串，getUser与映射文件中配置select标签id一致
        //执行查询返回一个唯一user对象的sql
        User user = MyBatisUtils.getInstance().selectOne(statement,2);
        LogUtils.info(user.toString());
    }
}
