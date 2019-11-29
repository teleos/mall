package com.liang.mall.web.servlet;

import com.liang.mall.bean.Category;
import com.liang.mall.util.Page;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(urlPatterns = "/categoryServlet")
public class CategoryServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");
        try {
            List<FileItem> fileItems = upload.parseRequest(request);

            Category c = new Category();
            String name = fileItems.get(0).getString();
            c.setName(name);
            Category category = categoryDAO.add(c);
            int id = category.getId();

            String realPath = this.getServletContext().getRealPath("/img/category/");
            fileItems.get(1).write(new File(realPath+"/"+id+".jpg"));


        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "@/mall/admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {

        int id = Integer.parseInt( request.getParameter("id"));
        Category category = categoryDAO.get(id);
        int i = categoryDAO.getTotal(id,"select count(*) from product where cid = ?");
        int j = categoryDAO.getTotal(id,"select count(*) from property where cid = ?");

        if(i+j > 0){
            //todo
        }


        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {

        String i = request.getParameter("id");
        if (StringUtils.isNotBlank(i)){
            int id = Integer.parseInt(i);
            Category category = categoryDAO.get(id);
            request.setAttribute("c",category);
        }
        return "admin/editCategory.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");
        List<FileItem> fileItems = null;
        try {
            fileItems = upload.parseRequest(request);

            String name = fileItems.get(0).getString("utf-8");
            String filepath = fileItems.get(1).getName();
            String id = fileItems.get(2).getString("utf-8");

            Category c = new Category();
            c.setName(name);
            c.setId(Integer.parseInt(id) );

            Integer i = categoryDAO.update(c);
            if (i>0) {

                String realPath = getServletContext().getRealPath("/img/category/");
                fileItems.get(1).write(new File(realPath+"/"+id+".jpg"));
                return "@/mall/admin_category_list";
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "admin/editCategory.jsp";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        List<Category> categories = categoryDAO.list(page.getStart(), page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs", categories);
        request.setAttribute("page", page);

        return "admin/listCategory.jsp";
    }
}
