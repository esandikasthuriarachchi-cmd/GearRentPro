/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

/**
 *
 * @author LENOVO
 */
import dao.EquipmentDAO;
import entity.Equipment;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAOImpl implements EquipmentDAO {

    @Override
    public boolean add(Equipment e) throws Exception {
        String sql = "INSERT INTO equipment (code, category_id, branch_id, brand, model, daily_price, deposit, status) " +
                     "VALUES (?,?,?,?,?,?,?,?)";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, e.getCode());
        pst.setInt(2, e.getCategoryId());
        pst.setInt(3, e.getBranchId());
        pst.setString(4, e.getBrand());
        pst.setString(5, e.getModel());
        pst.setDouble(6, e.getDailyPrice());
        pst.setDouble(7, e.getDeposit());
        pst.setString(8, e.getStatus());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean update(Equipment e) throws Exception {
        String sql = "UPDATE equipment SET code=?, category_id=?, branch_id=?, brand=?, model=?, daily_price=?, deposit=?, status=? " +
                     "WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, e.getCode());
        pst.setInt(2, e.getCategoryId());
        pst.setInt(3, e.getBranchId());
        pst.setString(4, e.getBrand());
        pst.setString(5, e.getModel());
        pst.setDouble(6, e.getDailyPrice());
        pst.setDouble(7, e.getDeposit());
        pst.setString(8, e.getStatus());
        pst.setInt(9, e.getId());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM equipment WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);
        return pst.executeUpdate() > 0;
    }

    @Override
    public Equipment getById(int id) throws Exception {
        String sql = "SELECT * FROM equipment WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return mapRow(rs);
        }
        return null;
    }

    @Override
    public List<Equipment> getAll() throws Exception {
        String sql = "SELECT * FROM equipment ORDER BY id DESC";
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Equipment> list = new ArrayList<>();
        while (rs.next()) {
            list.add(mapRow(rs));
        }
        return list;
    }

    private Equipment mapRow(ResultSet rs) throws Exception {
        return new Equipment(
                rs.getInt("id"),
                rs.getString("code"),
                rs.getInt("category_id"),
                rs.getInt("branch_id"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getDouble("daily_price"),
                rs.getDouble("deposit"),
                rs.getString("status")
        );
    }
}
