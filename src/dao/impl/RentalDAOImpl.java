/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.impl;

/**
 *
 * @author LENOVO
 */


import dao.RentalDAO;
import entity.Rental;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAOImpl implements RentalDAO {

    @Override
    public boolean add(Rental r) throws Exception {
        String sql = "INSERT INTO rentals (equipment_id, customer_id, branch_id, start_date, end_date, status, payment_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, r.getEquipmentId());
        pst.setInt(2, r.getCustomerId());
        pst.setInt(3, r.getBranchId());
        pst.setDate(4, Date.valueOf(r.getStartDate()));
        pst.setDate(5, Date.valueOf(r.getEndDate()));
        pst.setString(6, r.getStatus());
        pst.setString(7, r.getPaymentStatus()); // Handle payment status

        return pst.executeUpdate() > 0;
    }

    @Override
    public List<Rental> getRentalsByDateRange(String startDate, String endDate) throws Exception {
        String sql = "SELECT * FROM rentals WHERE start_date >= ? AND end_date <= ?";

        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, startDate);
        pst.setString(2, endDate);

        ResultSet rs = pst.executeQuery();
        List<Rental> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Rental(
                    rs.getInt("id"),
                    rs.getInt("equipment_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("branch_id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getString("payment_status")
            ));
        }
        return list;
    }

    @Override
    public boolean update(Rental r) throws Exception {
        // Update code here
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        // Delete code here
        return false;
    }

    @Override
    public Rental getById(int id) throws Exception {
        // Get by ID code here
        return null;
    }

    @Override
    public List<Rental> getAll() throws Exception {
        // Get all code here
        return null;
    }
}
