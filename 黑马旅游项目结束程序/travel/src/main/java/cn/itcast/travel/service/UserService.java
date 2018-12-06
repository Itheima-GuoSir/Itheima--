package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/27
 */
public interface UserService {
    Boolean findMethod(User user);

    User findMessage(User user);

    Boolean activeMethod(String code);

    String findTitle();
}
