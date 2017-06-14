package com.example1.Mapper;

import com.example1.Domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@Mapper
public interface UserMapper {
    User findUserById(Integer id);

    List<User> queryAll();

    User findUserByUser(@Param("userName") String userName, @Param("passWord") String passWord);
}
