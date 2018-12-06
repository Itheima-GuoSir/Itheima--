package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/27
 */
public interface UserDao {
    User findMethod(String username);

    void insertMethod(User users);

    User FindMessage(User user);

    User findCode(String code);

    void updateStatue(User user);

    List<Category> findTitle();
}
