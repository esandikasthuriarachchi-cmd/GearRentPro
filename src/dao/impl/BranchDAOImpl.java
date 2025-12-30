/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

/**
 *
 * @author LENOVO
 */

import dao.BranchDAO;
import entity.Branch;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAOImpl implements BranchDAO {

    @Override
    public boolean add(Branch b) throws Exception {
        String sql = "INSERT INTO branches (code, name, address, contact) VALUES (?,?,?,?)";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, b.getCode());
        pst.setString(2, b.getName());
        pst.setString(3, b.getAddress());
        pst.setString(4, b.getContact());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean update(Branch b) throws Exception {
        String sql = "UPDATE branches SET code=?, name=?, address=?, contact=? WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, b.getCode());
        pst.setString(2, b.getName());
        pst.setString(3, b.getAddress());
        pst.setString(4, b.getContact());
        pst.setInt(5, b.getId());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM branches WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        return pst.executeUpdate() > 0;
    }

    @Override
    public Branch getById(int id) throws Exception {
        String sql = "SELECT * FROM branches WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) return mapRow(rs);
        return null;
    }

    @Override
    public List<Branch> getAll() throws Exception {
        String sql = "SELECT * FROM branches ORDER BY id DESC";
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Branch> list = new ArrayList<>();
        while (rs.next()) list.add(mapRow(rs));
        return list;
    }

    private Branch mapRow(ResultSet rs) throws Exception {
        return new Branch(
                rs.getInt("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("contact")
        );
    }
}
