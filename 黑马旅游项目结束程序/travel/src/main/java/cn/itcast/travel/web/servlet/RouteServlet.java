package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.serviceImpl.FavoriteServiceImpl;
import cn.itcast.travel.service.serviceImpl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/29
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    public void findRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取到当前页码,每页显示的条数,和导航条传递来的id
        String currentPageStr = request.getParameter("currentPage");
        String rowsStr = request.getParameter("rows");
        String cidStr = request.getParameter("cid");
        //第一次运行的时候需要对各个获取的值进行初始化
        String rname = request.getParameter("rname");
        //转码
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        int cid =0;
        if(cidStr != null && cidStr.length()!=0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 1;
        if(currentPageStr!=null &&currentPageStr.length()!=0){
            currentPage = Integer.parseInt(currentPageStr);
        }
        int rows = 5;
        if(rowsStr!=null&& rowsStr.length()!=0){
            rows = Integer.parseInt(rowsStr);
        }
        //调用三层结构,把数据传递到service层

        PageBean<Route> pb = service.findLimit(cid,rows,currentPage,rname);
        //响应给服务器
        //使用json的常规方法
        /* ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(pb);*/
        //response.getWriter().write(json);
        //使用BaseServlet封装的方法
        writeValue(pb,response);
    }
    /**
     * 定义多表查询,关联商品信息
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取route的rid
        String rid = request.getParameter("rid");
        Route route =  service.findOne(rid);
        //响应给浏览器
        writeValue(route,response);
    }

    /**
     * 查看是否已经被收藏的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String rid = request.getParameter("rid");
        //获取用户登录时存入到session域中的user
        User user = (User)request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            //说明此时用户为登录
            uid = 0;
        }else{
            uid = user.getUid();
        }
        Boolean flag = service.isFavorite(rid,uid);
        writeValue(flag,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid ;
        if(user==null){
            //说明用户未登录,返回
            return;
        }else{
            uid = user.getUid();
        }
        //调用三层架构,把uid和rid传递下去,进行数据库的查询
        favoriteService.add(uid,rid);
    }
}
