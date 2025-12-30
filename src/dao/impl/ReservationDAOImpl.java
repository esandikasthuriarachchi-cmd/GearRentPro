/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

/**
 *
 * @author LENOVO
 */




import dao.ReservationDAO;
import entity.Reservation;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public boolean add(Reservation r) throws Exception {
        String sql = "INSERT INTO reservations (equipment_id, customer_id, branch_id, start_date, end_date, status, payment_status) "
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
    public List<Reservation> getReservationsByDateRange(String startDate, String endDate) throws Exception {
    String sql = "SELECT * FROM reservations WHERE start_date >= ? AND end_date <= ?";

    Connection con = DBConnection.getConnection();
    PreparedStatement pst = con.prepareStatement(sql);
    pst.setString(1, startDate);
    pst.setString(2, endDate);

    ResultSet rs = pst.executeQuery();
    List<Reservation> list = new ArrayList<>();

    while (rs.next()) {
        list.add(new Reservation(
                rs.getInt("id"),
                rs.getInt("equipment_id"),
                rs.getInt("customer_id"),
                rs.getInt("branch_id"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getString("status"),
                rs.getString("payment_status") // Now this should match the constructor
        ));
    }

    return list;
}


    @Override
    public boolean update(Reservation r) throws Exception {
        String sql = "UPDATE reservations SET equipment_id=?, customer_id=?, branch_id=?, start_date=?, end_date=?, status=?, payment_status=? WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, r.getEquipmentId());
        pst.setInt(2, r.getCustomerId());
        pst.setInt(3, r.getBranchId());
        pst.setDate(4, java.sql.Date.valueOf(r.getStartDate()));
        pst.setDate(5, java.sql.Date.valueOf(r.getEndDate()));
        pst.setString(6, r.getStatus());
        pst.setString(7, r.getPaymentStatus()); // Handle payment status
        pst.setInt(8, r.getId());

        return pst.executeUpdate() > 0;
    }

    @Override
    public boolean cancel(int id) throws Exception {
        String sql = "UPDATE reservations SET status='Cancelled' WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        return pst.executeUpdate() > 0;
    }

    @Override
    public Reservation getById(int id) throws Exception {
        String sql = "SELECT * FROM reservations WHERE id=?";
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new Reservation(
                    rs.getInt("id"),
                    rs.getInt("equipment_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("branch_id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getString("payment_status")
            );
        }
        return null;
    }

    @Override
    public List<Reservation> getAll() throws Exception {
        String sql = "SELECT * FROM reservations ORDER BY id DESC";
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Reservation> list = new ArrayList<>();
        while (rs.next()) {
            Reservation r = new Reservation(
                    rs.getInt("id"),
                    rs.getInt("equipment_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("branch_id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getString("payment_status")
            );
            list.add(r);
        }
        return list;
    }
}

