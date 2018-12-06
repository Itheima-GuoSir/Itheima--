package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.serviceImpl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/29
 */
@WebServlet("/Category/*")
public class CategoryServlet extends BaseServlet {
    /**
     * 实现表单导航栏显示的功能
     */
   public void findTitle(HttpServletRequest request, HttpServletResponse response) throws IOException {
       UserService service = new UserServiceImpl();
       String user = service.findTitle();
       //获取到查询数据,封装给浏览器
       response.setContentType("application/json;charset=utf-8");
       response.getWriter().write(user);
   }

}
