/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author LENOVO
 */


import entity.User;

import javax.swing.*;
import java.awt.*;

public class MenuApp extends JFrame {

    public MenuApp(User user) {

        String role = user.getRole(); // Admin / Staff / BranchManager
        setTitle("GearRentPro - Menu (" + role + ")");
        setSize(520, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnCustomers = new JButton("Customers");
        JButton btnEquipment = new JButton("Equipment");
        JButton btnRentals = new JButton("Rentals");
        JButton btnBranch = new JButton("Branch");
        JButton btnCategory = new JButton("Category");
        JButton btnReports = new JButton("Reports");

        panel.add(btnCustomers);
        panel.add(btnEquipment);
        panel.add(btnRentals);
        panel.add(btnBranch);
        panel.add(btnCategory);
        panel.add(btnReports);

        add(panel);

        // ✅ open your existing UIs
        btnCustomers.addActionListener(e -> new CustomerApp().setVisible(true));
        btnEquipment.addActionListener(e -> new EquipmentApp().setVisible(true));
        btnBranch.addActionListener(e -> new BranchApp().setVisible(true));


        // placeholders for now
        btnBranch.addActionListener(e -> new BranchApp().setVisible(true));
        btnCategory.addActionListener(e -> new CategoryApp().setVisible(true));

        // ✅ ROLE CONTROL (based on your DB enum: Admin / BranchManager / Staff)
        if (role == null) role = "";

        if (role.equalsIgnoreCase("Staff")) {
            // Staff: only Customers/Equipment/Rentals
            btnBranch.setEnabled(false);
            btnCategory.setEnabled(false);
            btnReports.setEnabled(false);

        } else if (role.equalsIgnoreCase("BranchManager")) {
            // BranchManager: can manage Category, but no Reports (you can change)
            btnBranch.setEnabled(false);   // if only admin should manage branches
            btnReports.setEnabled(false);

        } else if (role.equalsIgnoreCase("Admin")) {
            // Admin: everything enabled ✅
        } else {
            // unknown role -> safest: disable admin features
            btnBranch.setEnabled(false);
            btnCategory.setEnabled(false);
            btnReports.setEnabled(false);
        }
    }
}

