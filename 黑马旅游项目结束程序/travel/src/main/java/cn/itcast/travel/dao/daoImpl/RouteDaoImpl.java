package cn.itcast.travel.dao.daoImpl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/29
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int getTotalCount(int cid,String rname) {

        //这里需要对查询方式使用模糊查询,也正是因为用户可能会直接搜索,所以选择传入ranma来查询
        //String sql = "select count(*) from tab_route where cid = ?";
        //因为需要进行字符串拼接,所以必须留好合适的空格
        String sql = "select count(*) from tab_route where 1=1 ";
        //创建一个集合,用于存放查询条件
        List params = new ArrayList<>();
        //创建一个StringBuilder来进行查询条件的拼接
        StringBuilder sb = new StringBuilder(sql);
        if(cid!=0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname!=null&&rname.length()!=0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        Integer object = template.queryForObject(sql, Integer.class, params.toArray());
        return object;
    }

    @Override
    public List<Route> findRouteList(int start, int rows,String rname,int cid) {
        //String sql = "select * from tab_route limit ?,?";
        String sql = "select * from tab_route where 1 = 1 ";
        List params = new ArrayList<>();
        //创建一个StringBuilder来进行查询条件的拼接
        StringBuilder sb = new StringBuilder(sql);
        if(cid!=0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname!=null&&rname.length()>0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");
        sql = sb.toString();
        //将rows和start加入条件集合
        params.add(start);
        params.add(rows);

        return  template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "SELECT * FROM tab_route WHERE rid=?";
       //返回的是一个对象,所以只需要使用queryForObject
        return  template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
