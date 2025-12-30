/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

/**
 *
 * @author LENOVO
 */
import dao.CustomerDAO;
import entity.Customer;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CustomerDAOImpl implements CustomerDAO {

     @Override
    public boolean add(Customer c) throws Exception {
        String sql = "INSERT INTO customers (name, nic, contact, email, membership_id) VALUES (?, ?, ?, ?, ?)";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, c.getName());
        pst.setString(2, c.getNic());
        pst.setString(3, c.getContact());
        pst.setString(4, c.getEmail());
        pst.setInt(5, c.getMembershipId());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean update(Customer c) throws Exception {
        String sql = "UPDATE customers SET name=?, nic=?, contact=?, email=?, membership_id=? WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, c.getName());
        pst.setString(2, c.getNic());
        pst.setString(3, c.getContact());
        pst.setString(4, c.getEmail());
        pst.setInt(5, c.getMembershipId());
        pst.setInt(6, c.getId());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM customers WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        return pst.executeUpdate() > 0;
    }

    @Override
    public Customer getById(int id) throws Exception {
        String sql = "SELECT * FROM customers WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("nic"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getInt("membership_id")
            );
        }
        return null;
    }

    @Override
    public List<Customer> getAll() throws Exception {
        String sql = "SELECT * FROM customers ORDER BY id DESC";
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Customer> list = new ArrayList<>();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("nic"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getInt("membership_id")
            );
            list.add(c);
        }
        return list;
    }
}
    
