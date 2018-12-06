package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/27
 */
@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}
