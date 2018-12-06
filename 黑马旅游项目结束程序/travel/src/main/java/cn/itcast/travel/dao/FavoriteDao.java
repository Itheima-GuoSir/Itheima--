package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/31
 */
public interface FavoriteDao {
    Favorite isFavorite(int i, int uid);

    void add(int uid, String rid);

    int findCountByRid(int rid);
}
