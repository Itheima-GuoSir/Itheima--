package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/29
 */
public interface RouteService {
    PageBean<Route> findLimit(int cid, int rows, int currentPage,String rname);

    Route findOne(String rid);

    Boolean isFavorite(String rid, int uid);
}
