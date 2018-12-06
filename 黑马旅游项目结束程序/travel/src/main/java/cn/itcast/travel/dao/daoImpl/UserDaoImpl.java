package cn.itcast.travel.dao.daoImpl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/27
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findMethod(String username) {
        User user = null;
        try{
            String sql = "select * from tab_user where username=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    @Override
    public void insertMethod(User user) {
        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql

        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }

    /**
     * 校验登录信息的方法
     * @param users
     * @return
     */
    @Override
    public User FindMessage(User users) {
        User user = null;
        try{
            String sql = "select * from tab_user where username=? and password=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), users.getUsername(),users.getPassword());
        }catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    /**
     * 查看是否有此激活码对应的方法
     * @param code
     * @return
     */
    @Override
    public User findCode(String code) {

        User user = null;
        try {
            String sql = "select * from tab_user where code=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改激活状态的方法
     * @param user
     */
    @Override
    public void updateStatue(User user) {
        String sql = "update tab_user set status = 'Y' where uid=?";
        template.update(sql,user.getUid());
    }

    /**
     * 查询导航条的数据库
     * @return
     */
    @Override
    public List<Category> findTitle() {
        String sql = "select * from tab_category order by cid";

        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}

