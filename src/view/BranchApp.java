/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author LENOVO
 */


import dao.BranchDAO;
import dao.impl.BranchDAOImpl;
import entity.Branch;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BranchApp extends JFrame {

    private final BranchDAO branchDAO = new BranchDAOImpl();

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtCode, txtName, txtAddress, txtContact;

    public BranchApp() {
        setTitle("GearRentPro - Branch Management");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // so menu stays open

        initUI();
        loadBranches();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Branch Form"));

        txtId = new JTextField();
        txtId.setEditable(false);

        txtCode = new JTextField();
        txtName = new JTextField();
        txtAddress = new JTextField();
        txtContact = new JTextField();

        formPanel.add(new JLabel("ID"));
        formPanel.add(txtId);

        formPanel.add(new JLabel("Code"));
        formPanel.add(txtCode);

        formPanel.add(new JLabel("Name"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Address"));
        formPanel.add(txtAddress);

        formPanel.add(new JLabel("Contact"));
        formPanel.add(txtContact);

        // ===== BUTTONS =====
        JPanel btnPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnRefresh = new JButton("Refresh");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);
        btnPanel.add(btnRefresh);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(btnPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"ID", "Code", "Name", "Address", "Contact"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // prevent typing into table
            }
        };

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Branch List"));
        add(sp, BorderLayout.CENTER);

        // ===== CLICK ROW -> FILL FORM =====
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtCode.setText(model.getValueAt(row, 1).toString());
                txtName.setText(model.getValueAt(row, 2).toString());
                txtAddress.setText(model.getValueAt(row, 3).toString());
                txtContact.setText(model.getValueAt(row, 4).toString());
            }
        });

        // ===== BUTTON ACTIONS =====
        btnAdd.addActionListener(e -> addBranch());
        btnUpdate.addActionListener(e -> updateBranch());
        btnDelete.addActionListener(e -> deleteBranch());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadBranches());
    }

    private void loadBranches() {
        try {
            model.setRowCount(0);
            List<Branch> list = branchDAO.getAll();
            for (Branch b : list) {
                model.addRow(new Object[]{
                        b.getId(),
                        b.getCode(),
                        b.getName(),
                        b.getAddress(),
                        b.getContact()
                });
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void addBranch() {
        try {
            String code = txtCode.getText().trim();
            String name = txtName.getText().trim();
            String address = txtAddress.getText().trim();
            String contact = txtContact.getText().trim();

            if (code.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please fill all fields");
                return;
            }

            Branch b = new Branch(0, code, name, address, contact);
            boolean ok = branchDAO.add(b);

            JOptionPane.showMessageDialog(this, ok ? "✅ Branch Added!" : "❌ Add Failed");

            loadBranches();
            clearForm();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateBranch() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Select a row first");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            String code = txtCode.getText().trim();
            String name = txtName.getText().trim();
            String address = txtAddress.getText().trim();
            String contact = txtContact.getText().trim();

            if (code.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please fill all fields");
                return;
            }

            Branch b = new Branch(id, code, name, address, contact);
            boolean ok = branchDAO.update(b);

            JOptionPane.showMessageDialog(this, ok ? "✅ Branch Updated!" : "❌ Update Failed");

            loadBranches();
            clearForm();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteBranch() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Select a row first");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete branch ID " + id + " ?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = branchDAO.delete(id);
                JOptionPane.showMessageDialog(this, ok ? "✅ Branch Deleted!" : "❌ Delete Failed");
                loadBranches();
                clearForm();
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtCode.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        table.clearSelection();
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BranchApp().setVisible(true));
    }
}
