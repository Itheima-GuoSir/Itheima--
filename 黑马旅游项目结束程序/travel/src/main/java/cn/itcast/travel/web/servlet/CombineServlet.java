package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/28
 */
@WebServlet("/Combine/*")
public class CombineServlet extends BaseServlet {
    /**
     * 注册页面的方法
     */
    public void registerMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //验证码的校验
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        Object checkcode_server = session.getAttribute("CHECKCODE_SERVER");
        //保证验证码的一次性
        session.removeAttribute("CHECKCODE_SERVER");
        //比较验证码
        if(check==null||!check.equalsIgnoreCase((String)checkcode_server)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误!");
            ObjectMapper object = new ObjectMapper();
            String json = object.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            //响应到浏览器
            response.getWriter().write(json);
            return;
        }
        //获取表单提交的所有的参数
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        //封装
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //使用三层架构将数据传到底层
        UserService service = new UserServiceImpl();
        Boolean flag = service.findMethod(user);
        //创建info对象是为了提交数据时更方便
        ResultInfo info = new ResultInfo();
        if(flag){
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        //使用json,将java对象转换为json字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        //响应给服务器
        response.getWriter().write(json);
    }
    /**
     * 登录验证的方法
     */
    public void loginMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //验证码的校验
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        Object checkcode_server = session.getAttribute("CHECKCODE_SERVER");
        //保证验证码的一次性
        session.removeAttribute("CHECKCODE_SERVER");
        //比较验证码
        if(check==null||!check.equalsIgnoreCase((String)checkcode_server)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误!");
            ObjectMapper object = new ObjectMapper();
            String json = object.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            //响应到浏览器
            response.getWriter().write(json);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        User user1 = service.findMessage(user);
        ResultInfo info = new ResultInfo();
        if(user1==null){
            //说明用户名或者密码不存在
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码不正确!");
        }
        if(user1!=null&&!"Y".equals(user1.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("用户未激活!");
        }
        if(user1!=null&&"Y".equals(user1.getStatus())){
            request.getSession().setAttribute("user",user1);
            info.setFlag(true);
        }
        //使用json解析器将info转换为json字符串
        ObjectMapper mapper = new ObjectMapper();
        //设置格式,并相应给浏览器
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }
    /**
     * 显示登录用户的方法
     */
    public void findUserMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }
    /**
     * 退出登陆的方法
     *
     */
    public void exitMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //获取session,删除code
        request.getSession().invalidate();
        //资源跳转
        response.sendRedirect(request.getContextPath()+"/index.html");
    }
    /**
     * 邮箱验证激活的方法
     */
    public void activeMailMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null){
            UserService service = new UserServiceImpl();
            Boolean flag = service.activeMethod(code);
            String msg = null;
            if(flag){
                msg="激活成功,请<a href='http://localhost/travel/login.html'>登录</a>";
            }else{
                msg="激活失败,请联系管理!";
            }
            //将msg响应至浏览器界面
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

}
