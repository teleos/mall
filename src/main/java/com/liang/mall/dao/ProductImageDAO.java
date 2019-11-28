package com.liang.mall.dao;

import com.liang.mall.bean.Product;
import com.liang.mall.bean.ProductImage;
import com.liang.mall.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAO {
    final static String type_single = "type_single";
    final static String type_detail = "type_detail";



    public List<ProductImage> list(Product p, String type){
        return  list(p,type,0,Short.MAX_VALUE);
    }

    public List<ProductImage> list(Product p, String type, int start, int count) {

        ArrayList<ProductImage> list = new ArrayList<>();
        String sql = "SELECT * FROM `productimage` WHERE pid=?  AND type=? ORDER BY id LIMIT ?,?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1,p.getId());
            stmt.setString(2,type);
            stmt.setInt(3,start);
            stmt.setInt(4,count);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()){
                    ProductImage image = new ProductImage();
                    image.setId(rs.getInt("id"));
                    image.setType(rs.getString("type"));
                    image.setProduct(p);
                    list.add(image);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
