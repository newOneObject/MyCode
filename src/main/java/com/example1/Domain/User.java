package com.example1.Domain;

/**
 * Created by Administrator on 2017/5/9.
 */
public class User {
    private Integer userId;
    private String userName;
    private String passWord;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {

        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "userName="+userName+","+"passWord="+passWord;
    }
}
