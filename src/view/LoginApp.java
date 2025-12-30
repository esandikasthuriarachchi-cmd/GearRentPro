/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author LENOVO
 */
import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class LoginApp extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private final UserDAO userDAO = new UserDAOImpl();

    public LoginApp() {
        setTitle("GearRentPro - Login");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Login ✅");
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        try {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Enter username + password");
                return;
            }

            User user = userDAO.login(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this,
                        "✅ Login Success!\nRole: " + user.getRole());

                // ✅ OPEN MENU (next step)
                new MenuApp(user).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid login");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginApp().setVisible(true));
    }
}