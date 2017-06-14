package com.example1.Dao;

import com.example1.Domain.User;
import com.example1.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * @component 在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释，而用 @Component 对那些比较中立的类进行注释
   这里就是说把这个类交给Spring管理，重新起个名字叫userManager，由于不好说这个类属于哪个层面，就用@Component
 */
@Component
public class UserDao {
    @Autowired
    private UserMapper userMapper;
    public User findUserById(Integer id) throws Exception {
        return userMapper.findUserById(id);
    }

    public List<User> queryAll() throws Exception {
        return userMapper.queryAll();
    }

    public User findUserByUser(String userName,String passWord) throws Exception {
        return userMapper.findUserByUser(userName,passWord);
    }
}
