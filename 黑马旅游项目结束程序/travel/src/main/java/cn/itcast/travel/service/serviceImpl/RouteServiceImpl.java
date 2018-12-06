package cn.itcast.travel.service.serviceImpl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.daoImpl.FavoriteDaoImpl;
import cn.itcast.travel.dao.daoImpl.RouteDaoImpl;
import cn.itcast.travel.dao.daoImpl.RouteImgDaoImpl;
import cn.itcast.travel.dao.daoImpl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/29
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    //使用新接口RouteImgDao获取routeImg结果集
    private SellerDao sellerdao = new SellerDaoImpl();
    //使用新接口RouteImgDao获取routeImg结果集
    private RouteImgDao routeDao = new RouteImgDaoImpl();
    //使用新接口FavoriteDao获取结果集
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> findLimit(int cid, int rows, int currentPage,String rname) {
        //利用传递来的三个参数获得PageBean类当中的所用参数,并完成封装
        PageBean<Route> page = new PageBean<>();
        page.setRows(rows);
        page.setCurrentPage(currentPage);
        //开始索引
        int start = (currentPage-1)*rows;
        //总页码需要计算,此时需要到数据库查询总记录数
        int totalCount = dao.getTotalCount(cid,rname);
        int totalPage =totalCount%rows==0 ? totalCount/rows:totalCount/rows+1;
        page.setTotalPage(totalPage);
        page.setTotalCount(totalCount);
        //将分组查询的结果封装
        List<Route> list = dao.findRouteList(start,rows,rname,cid);
        //封装集合
        page.setList(list);
        //将page对象相应给服务器

        return page;
    }

    @Override
    public Route findOne(String rid) {
        Route route = dao.findOne(Integer.parseInt(rid));
        //再使用返回的Route对象,去通过多表关系查询卖家信息表和商品表

        Seller seller =  sellerdao.findSeller(route.getSid());
        //将seller结果存入route对象
        route.setSeller(seller);

        List<RouteImg> routeImg = routeDao.findRouteImg(route.getRid());
        //将routeImg结果存入route对象
        route.setRouteImgList(routeImg);

        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }

    @Override
    public Boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.isFavorite(Integer.parseInt(rid),uid);
        return  favorite!=null;
    }


}
