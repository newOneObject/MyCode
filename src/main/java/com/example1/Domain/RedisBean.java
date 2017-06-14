package com.example1.Domain;

/**
 * Created by Administrator on 2017/5/25.
 */
public class RedisBean {
    private String userName;
    private Integer age;

    public RedisBean() {
    }

    public RedisBean(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
