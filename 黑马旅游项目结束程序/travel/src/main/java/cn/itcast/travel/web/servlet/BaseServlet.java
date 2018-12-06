package cn.itcast.travel.web.servlet;



import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author 郭雪虎
 * @email gxh15155541564@163.com
 * @date 2018/10/28
 */
public class BaseServlet extends HttpServlet {
    //重写service方法
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取URI
        String uri = request.getRequestURI();
        //使用字符串的相关方法提取出方法名
        String method = uri.substring(uri.lastIndexOf('/') + 1);
        //使用反射机制获取方法
        try {
            Method methodResult = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            //执行此方法
            methodResult.invoke(this, request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Jackson解析器的writeValue解析方法
     * @param object
     * @param response
     */
    public void writeValue(Object object,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),object);
    }

    /**
     * Jackson解析器的writeValueAsString解析方法
     *
     * @param object
     * @throws IOException
     */
    public void writeValueAsString(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(object);
    }
}
