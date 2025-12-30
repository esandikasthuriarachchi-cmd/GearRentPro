/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author LENOVO
 */
import dao.EquipmentDAO;
import dao.impl.EquipmentDAOImpl;
import entity.Equipment;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EquipmentApp extends JFrame {

    private final EquipmentDAO equipmentDAO = new EquipmentDAOImpl();

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtCode, txtCategoryId, txtBranchId, txtBrand, txtModel, txtDailyPrice, txtDeposit, txtStatus;

    public EquipmentApp() {
        setTitle("GearRentPro - Equipment Management");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        loadEquipment();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Equipment Form"));

        txtId = new JTextField();
        txtId.setEditable(false);

        txtCode = new JTextField();
        txtCategoryId = new JTextField("1"); // default
        txtBranchId = new JTextField("1");   // default
        txtBrand = new JTextField();
        txtModel = new JTextField();
        txtDailyPrice = new JTextField();
        txtDeposit = new JTextField("0");    // default
        txtStatus = new JTextField("Available"); // default

        formPanel.add(new JLabel("ID"));
        formPanel.add(txtId);

        formPanel.add(new JLabel("Code"));
        formPanel.add(txtCode);

        formPanel.add(new JLabel("Category ID"));
        formPanel.add(txtCategoryId);

        formPanel.add(new JLabel("Branch ID"));
        formPanel.add(txtBranchId);

        formPanel.add(new JLabel("Brand"));
        formPanel.add(txtBrand);

        formPanel.add(new JLabel("Model"));
        formPanel.add(txtModel);

        formPanel.add(new JLabel("Daily Price"));
        formPanel.add(txtDailyPrice);

        formPanel.add(new JLabel("Deposit"));
        formPanel.add(txtDeposit);

        formPanel.add(new JLabel("Status"));
        formPanel.add(txtStatus);

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
        model = new DefaultTableModel(
                new String[]{"ID", "Code", "Category ID", "Branch ID", "Brand", "Model", "Daily Price", "Deposit", "Status"}, 0
        );
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Equipment List"));
        add(sp, BorderLayout.CENTER);

        // ===== CLICK ROW -> FILL FORM =====
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();

                txtId.setText(model.getValueAt(row, 0).toString());
                txtCode.setText(model.getValueAt(row, 1).toString());
                txtCategoryId.setText(model.getValueAt(row, 2).toString());
                txtBranchId.setText(model.getValueAt(row, 3).toString());
                txtBrand.setText(model.getValueAt(row, 4).toString());
                txtModel.setText(model.getValueAt(row, 5).toString());
                txtDailyPrice.setText(model.getValueAt(row, 6).toString());
                txtDeposit.setText(model.getValueAt(row, 7).toString());
                txtStatus.setText(model.getValueAt(row, 8).toString());
            }
        });

        // ===== BUTTON ACTIONS =====
        btnAdd.addActionListener(e -> addEquipment());
        btnUpdate.addActionListener(e -> updateEquipment());
        btnDelete.addActionListener(e -> deleteEquipment());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadEquipment());
    }

    private void loadEquipment() {
        try {
            model.setRowCount(0);
            List<Equipment> list = equipmentDAO.getAll();

            for (Equipment e : list) {
                model.addRow(new Object[]{
                        e.getId(),
                        e.getCode(),
                        e.getCategoryId(),
                        e.getBranchId(),
                        e.getBrand(),
                        e.getModel(),
                        e.getDailyPrice(),
                        e.getDeposit(),
                        e.getStatus()
                });
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void addEquipment() {
        try {
            String code = txtCode.getText().trim();
            int categoryId = Integer.parseInt(txtCategoryId.getText().trim());
            int branchId = Integer.parseInt(txtBranchId.getText().trim());
            String brand = txtBrand.getText().trim();
            String modelTxt = txtModel.getText().trim();
            double dailyPrice = Double.parseDouble(txtDailyPrice.getText().trim());
            double deposit = Double.parseDouble(txtDeposit.getText().trim());
            String status = txtStatus.getText().trim();

            if (code.isEmpty() || brand.isEmpty() || modelTxt.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please fill all fields");
                return;
            }

            Equipment eq = new Equipment(0, code, categoryId, branchId, brand, modelTxt, dailyPrice, deposit, status);

            boolean ok = equipmentDAO.add(eq);
            JOptionPane.showMessageDialog(this, ok ? "✅ Equipment Added!" : "❌ Add Failed");

            loadEquipment();
            clearForm();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "❌ Category ID / Branch ID must be integers, Daily Price & Deposit must be numbers");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateEquipment() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Select a row first");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            String code = txtCode.getText().trim();
            int categoryId = Integer.parseInt(txtCategoryId.getText().trim());
            int branchId = Integer.parseInt(txtBranchId.getText().trim());
            String brand = txtBrand.getText().trim();
            String modelTxt = txtModel.getText().trim();
            double dailyPrice = Double.parseDouble(txtDailyPrice.getText().trim());
            double deposit = Double.parseDouble(txtDeposit.getText().trim());
            String status = txtStatus.getText().trim();

            Equipment eq = new Equipment(id, code, categoryId, branchId, brand, modelTxt, dailyPrice, deposit, status);

            boolean ok = equipmentDAO.update(eq);
            JOptionPane.showMessageDialog(this, ok ? "✅ Equipment Updated!" : "❌ Update Failed");

            loadEquipment();
            clearForm();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "❌ Category ID / Branch ID must be integers, Daily Price & Deposit must be numbers");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteEquipment() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Select a row first");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete equipment ID " + id + " ?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = equipmentDAO.delete(id);
                JOptionPane.showMessageDialog(this, ok ? "✅ Equipment Deleted!" : "❌ Delete Failed");
                loadEquipment();
                clearForm();
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtCode.setText("");
        txtCategoryId.setText("1");
        txtBranchId.setText("1");
        txtBrand.setText("");
        txtModel.setText("");
        txtDailyPrice.setText("");
        txtDeposit.setText("0");
        txtStatus.setText("Available");
        table.clearSelection();
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EquipmentApp().setVisible(true));
    }
}


