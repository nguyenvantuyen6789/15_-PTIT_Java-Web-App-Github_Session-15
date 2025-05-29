package com.data.repository;

import com.data.connection.ConnectionDB;
import com.data.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public List<Product> findAll() {
        Connection conn = null;
        conn = ConnectionDB.openConnection();

        List<Product> products = new ArrayList<>();
        try {
            CallableStatement callSt = conn.prepareCall("{CALL find_all_product()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getInt("price"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public int delete(int id) {
        Connection conn = null;
        conn = ConnectionDB.openConnection();

        int count = 0;
        try {
            CallableStatement callSt = conn.prepareCall("{CALL delete_product(?)}");
            callSt.setInt(1, id);

            count = callSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int save(Product product) {
        Connection conn = null;
        conn = ConnectionDB.openConnection();

        int count = 0;
        try {
            CallableStatement callSt = conn.prepareCall("{CALL save_product(?, ?)}");
            callSt.setString(1, product.getProductName());
            callSt.setInt(2, product.getPrice());

            count = callSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
