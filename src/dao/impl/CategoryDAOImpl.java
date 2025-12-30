/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

/**
 *
 * @author LENOVO
 */
import dao.CategoryDAO;
import entity.Category;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CategoryDAOImpl implements CategoryDAO {

   @Override
    public boolean add(Category c) throws Exception {
        String sql = "INSERT INTO categories (name, base_price_factor, weekend_multiplier, late_fee_per_day) VALUES (?,?,?,?)";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, c.getName());
        pst.setDouble(2, c.getBasePriceFactor());
        pst.setDouble(3, c.getWeekendMultiplier());
        pst.setDouble(4, c.getLateFeePerDay());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean update(Category c) throws Exception {
        String sql = "UPDATE categories SET name=?, base_price_factor=?, weekend_multiplier=?, late_fee_per_day=? WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, c.getName());
        pst.setDouble(2, c.getBasePriceFactor());
        pst.setDouble(3, c.getWeekendMultiplier());
        pst.setDouble(4, c.getLateFeePerDay());
        pst.setInt(5, c.getId());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM categories WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);
        return pst.executeUpdate() > 0;
    }

    @Override
    public Category getById(int id) throws Exception {
        String sql = "SELECT * FROM categories WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("base_price_factor"),
                    rs.getDouble("weekend_multiplier"),
                    rs.getDouble("late_fee_per_day")
            );
        }
        return null;
    }

    @Override
    public List<Category> getAll() throws Exception {
        String sql = "SELECT * FROM categories ORDER BY id DESC";
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Category> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("base_price_factor"),
                    rs.getDouble("weekend_multiplier"),
                    rs.getDouble("late_fee_per_day")
            ));
        }
        return list;
    }
}







