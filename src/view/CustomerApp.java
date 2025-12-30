/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author LENOVO
 */
import dao.CustomerDAO;
import dao.impl.CustomerDAOImpl;
import entity.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerApp extends JFrame {

    private final CustomerDAO customerDAO = new CustomerDAOImpl();

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtNic, txtContact, txtEmail, txtMembership;

    public CustomerApp() {
        setTitle("GearRentPro - Customer Management");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        loadCustomers();
    }

    private void initUI() {
        // ---------- Form Panel ----------
        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Customer Form"));

        txtId = new JTextField();
        txtId.setEditable(false);

        txtName = new JTextField();
        txtNic = new JTextField();
        txtContact = new JTextField();
        txtEmail = new JTextField();
        txtMembership = new JTextField();

        form.add(new JLabel("ID"));
        form.add(txtId);

        form.add(new JLabel("Name"));
        form.add(txtName);

        form.add(new JLabel("NIC"));
        form.add(txtNic);

        form.add(new JLabel("Contact"));
        form.add(txtContact);

        form.add(new JLabel("Email"));
        form.add(txtEmail);

        form.add(new JLabel("Membership ID"));
        form.add(txtMembership);

        // ---------- Buttons ----------
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnRefresh = new JButton("Refresh");

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);
        buttons.add(btnRefresh);

        // ---------- Table ----------
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "NIC", "Contact", "Email", "Membership"}, 0
        );
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Customers"));

        // Layout
        JPanel left = new JPanel(new BorderLayout(10, 10));
        left.add(form, BorderLayout.CENTER);
        left.add(buttons, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(left, BorderLayout.WEST);
        add(sp, BorderLayout.CENTER);

        // ---------- Events ----------
        btnAdd.addActionListener(e -> addCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadCustomers());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                fillFormFromTable();
            }
        });
    }

    private void loadCustomers() {
        try {
            model.setRowCount(0);
            List<Customer> list = customerDAO.getAll();
            for (Customer c : list) {
                model.addRow(new Object[]{
                        c.getId(),
                        c.getName(),
                        c.getNic(),
                        c.getContact(),
                        c.getEmail(),
                        c.getMembershipId()
                });
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void addCustomer() {
        try {
            String name = txtName.getText().trim();
            String nic = txtNic.getText().trim();
            String contact = txtContact.getText().trim();
            String email = txtEmail.getText().trim();
            int membershipId = Integer.parseInt(txtMembership.getText().trim());

            if (name.isEmpty() || nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and NIC are required ❗");
                return;
            }

            boolean ok = customerDAO.add(new Customer(0, name, nic, contact, email, membershipId));
            JOptionPane.showMessageDialog(this, ok ? "✅ Customer Added" : "❌ Add Failed");

            loadCustomers();
            clearForm();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Membership ID must be a number ❗");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateCustomer() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a customer first 👆");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            String nic = txtNic.getText().trim();
            String contact = txtContact.getText().trim();
            String email = txtEmail.getText().trim();
            int membershipId = Integer.parseInt(txtMembership.getText().trim());

            boolean ok = customerDAO.update(new Customer(id, name, nic, contact, email, membershipId));
            JOptionPane.showMessageDialog(this, ok ? "✅ Customer Updated" : "❌ Update Failed");

            loadCustomers();
            clearForm();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID/Membership ID must be numbers ❗");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteCustomer() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a customer first 👆");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete customer ID " + id + " ?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = customerDAO.delete(id);
                JOptionPane.showMessageDialog(this, ok ? "✅ Customer Deleted" : "❌ Delete Failed");
                loadCustomers();
                clearForm();
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        txtId.setText(model.getValueAt(row, 0).toString());
        txtName.setText(model.getValueAt(row, 1).toString());
        txtNic.setText(model.getValueAt(row, 2).toString());
        txtContact.setText(model.getValueAt(row, 3).toString());
        txtEmail.setText(model.getValueAt(row, 4).toString());
        txtMembership.setText(model.getValueAt(row, 5).toString());
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtNic.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtMembership.setText("");
        table.clearSelection();
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerApp().setVisible(true));
    }
}
