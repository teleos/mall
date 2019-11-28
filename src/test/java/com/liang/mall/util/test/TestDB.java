package com.liang.mall.util.test;


import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static com.liang.mall.util.DBUtil.*;

public class TestDB {



    @Test
    public void   testConn(){

        try (Connection connection = getConnection()) {
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
