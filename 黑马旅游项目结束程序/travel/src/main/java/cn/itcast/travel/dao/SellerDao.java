package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/30
 */
public interface SellerDao {
    Seller findSeller(int sid);
}
