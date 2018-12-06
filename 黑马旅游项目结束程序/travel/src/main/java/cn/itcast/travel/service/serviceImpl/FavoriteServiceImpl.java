package cn.itcast.travel.service.serviceImpl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.daoImpl.FavoriteDaoImpl;
import cn.itcast.travel.service.FavoriteService;


/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/31
 */
public class FavoriteServiceImpl implements FavoriteService {
   private FavoriteDao dao = new FavoriteDaoImpl();
    @Override
    public void add(int uid, String rid) {
       dao.add(uid,rid);
    }
}
