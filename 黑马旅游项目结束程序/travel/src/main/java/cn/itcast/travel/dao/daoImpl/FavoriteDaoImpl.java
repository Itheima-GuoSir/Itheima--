package cn.itcast.travel.dao.daoImpl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/31
 */
public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite isFavorite(int rid, int uid) {
        Favorite query = null;
        try {
            //查询对应的表
            String sql = " SELECT * FROM tab_favorite WHERE uid=? and rid = ?";
            query = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * 修改收藏状态的方法
     * @param uid
     * @param rid
     */
    @Override
    public void add(int uid, String rid) {
        //执行添加操作
        String sql = "INSERT INTO tab_favorite VALUES (?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }

    /**
     * 记录收藏次数的方法
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT count(*) FROM tab_favorite WHERE rid=?";
        return template.queryForObject(sql, Integer.class, rid);
    }
}
