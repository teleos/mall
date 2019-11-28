package com.liang.mall.web.servlet;

import com.liang.mall.dao.CategoryDAO;
import com.liang.mall.dao.ProductDAO;
import com.liang.mall.util.Page;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public abstract class BaseBackServlet extends HttpServlet {

    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);


    protected CategoryDAO categoryDAO = new CategoryDAO();
    protected ProductDAO productDAO = new ProductDAO();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //利用反射
        String method = (String) request.getAttribute("method");
        try {
            int start = 0;
            int count = 5;

            if(!StringUtils.isBlank(request.getParameter("page.start"))){
                start = Integer.parseInt(request.getParameter("page.start"));
            }
            if (!StringUtils.isBlank(request.getParameter("page.count"))){
                count = Integer.parseInt(request.getParameter("page.count"));
            }
            Page page = new Page(start, count);
            request.setAttribute("page",page);
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, Page.class);

            String  result = (String) m.invoke(this, request, response, page);
            if (result.startsWith("@")){
                response.sendRedirect(result.substring(1));
            }else if (result.startsWith("%")){
                response.getWriter().print(result.substring(1));//输出字符串
            }else {
                request.getRequestDispatcher(result).forward(request,response);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
