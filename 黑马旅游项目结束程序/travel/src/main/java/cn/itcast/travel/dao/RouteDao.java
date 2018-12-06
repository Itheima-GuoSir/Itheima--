package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/29
 */
public interface RouteDao {
    int getTotalCount(int cid,String rname);

    List<Route> findRouteList(int start, int rows,String rname,int cid);

    Route findOne(int rid);
}
