package cn.itcast.travel.service.serviceImpl;


import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.daoImpl.UserDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.JedisUtil;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/27
 */
public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public Boolean findMethod(User users) {
        User user = dao.findMethod(users.getUsername());
        if (user != null) {
            //说明此时数据库中已经有了此表单提交的用户名,故注册失败
            return false;
        }
        //反之,就说明此时数据库中没此信息
        //将激活状态添加进去
        users.setStatus("N");
        //将激活码加入进去
        users.setCode(UuidUtil.getUuid());
        //再把数据传到数据库,添加进去
        dao.insertMethod(users);
        //完成发邮件的正文
        String content = "点击链接激活<a href='http://localhost/travel/Combine/activeMailMethod?code=" + users.getCode() + "'>[黑马旅游网]</a>";
        //发送邮件(邮件发送地址,正文,标题)
        MailUtils.sendMail(users.getEmail(), content, "激活验证");
        return true;
    }

    @Override
    public User findMessage(User user) {
        return dao.FindMessage(user);
    }

    /**
     * 实现邮箱激活的方法
     *
     * @param code
     * @return
     */
    @Override
    public Boolean activeMethod(String code) {
        //获取此code对应的对象
        User user = dao.findCode(code);
        if (user != null) {
            //说明此code对应的对象存在,此时到数据库将status修改成激活状态
            dao.updateStatue(user);
            //将flag设置为ture
            return true;
        }
        return false;
    }

    /**
     * 缓存单行条的方法
     *
     * @return
     */
    @Override
    public String findTitle() {
        //使用缓存技术,必须要获取jedis对象
        try {
            Jedis jedis = JedisUtil.getJedis();
            String result = jedis.get("result");
            //判断结果是否存在,如果不存在说明缓存中没有此数据
            if (result == null) {
                //接下来从数据库查找
                List<Category> user = dao.findTitle();
                //查找到之后转换为json字符串保存到redis中作为下次的缓存
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.writeValueAsString(user);
                jedis.set("result", result);
            }
                return result;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
    }
}