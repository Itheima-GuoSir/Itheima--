package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.serviceImpl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/28
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null){
            UserService service = new UserServiceImpl();
            Boolean flag = service.activeMethod(code);
            String msg = null;
            if(flag){
                msg="激活成功,请<a href='login.html'>登录</a>";
            }else{
                msg="激活失败,请联系管理!";
            }
            //将msg响应至浏览器界面
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}
