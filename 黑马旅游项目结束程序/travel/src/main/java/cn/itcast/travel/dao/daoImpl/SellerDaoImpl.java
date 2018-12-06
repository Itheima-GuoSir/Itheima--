package cn.itcast.travel.dao.daoImpl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/30
 */
public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 利用route表的rid查询seller表
     * @param sid
     * @return
     */
    @Override
    public Seller findSeller(int sid) {
        //根据rid查询全表
        String sql = "select * from tab_seller where sid = ? ";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}

