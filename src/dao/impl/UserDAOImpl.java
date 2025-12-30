/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

/**
 *
 * @author LENOVO
 */
import dao.UserDAO;
import entity.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;

public class UserDAOImpl implements UserDAO {

    private String sha256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(input.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString(); // hex string
    }

    @Override
    public User login(String username, String password) throws Exception {

        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, username.trim());
        pst.setString(2, sha256(password.trim())); // ✅ hash entered password

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPasswordHash(rs.getString("password_hash"));
            u.setRole(rs.getString("role"));

            // branch_id can be NULL
            Integer branchId = (Integer) rs.getObject("branch_id");
            u.setBranchId(branchId == null ? 0 : branchId);

            return u;
        }

        return null;
    }
}