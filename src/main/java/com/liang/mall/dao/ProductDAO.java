package com.liang.mall.dao;

import com.liang.mall.bean.Category;
import com.liang.mall.bean.Product;
import com.liang.mall.bean.ProductImage;
import com.liang.mall.util.DBUtil;
import com.liang.mall.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDAO {


    public List<Product> list(int cid){

        return  list(cid,0,Integer.MAX_VALUE);
    }

    public List<Product> list(int cid, int start, int count) {

        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product p WHERE p.cid = ? ORDER BY id LIMIT ? ,?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,cid);
            stmt.setInt(2,start);
            stmt.setInt(3,count);
            try (ResultSet rs = stmt.executeQuery()) {
                CategoryDAO categoryDAO = new CategoryDAO();
                Category category = categoryDAO.get(cid);
                while (rs.next()){
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(category);
                    Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                    product.setCreateDate( createDate);

                    product.setSubTitle( rs.getString("subTitle") );
                    product.setOrignalPrice(rs.getFloat("originalPrice") );
                    product.setPromotePrice(rs.getFloat("promotePrice") );
                    product.setStock(rs.getInt("stock"));
                    setFirstProductImage(product);
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    private void setFirstProductImage(Product p) {
        List<ProductImage> pis = new ProductImageDAO().list(p, ProductImageDAO.type_single);
        if (!pis.isEmpty())
            p.setFirstProductImage(pis.get(0));
    }
}
