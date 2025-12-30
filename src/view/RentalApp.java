/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author LENOVO
 */
import dao.impl.RentalDAOImpl;
import entity.Rental;
import dao.RentalDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RentalApp extends JFrame {

    private final RentalDAO rentalDAO = new RentalDAOImpl();
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtEquipmentId, txtCustomerId, txtBranchId, txtStartDate, txtEndDate, txtStatus, txtPaymentStatus;

    public RentalApp() {
        setTitle("GearRentPro - Rental Management");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        loadRentals();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Rental Form"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtEquipmentId = new JTextField();
        txtCustomerId = new JTextField();
        txtBranchId = new JTextField();
        txtStartDate = new JTextField();
        txtEndDate = new JTextField();
        txtStatus = new JTextField("Active");
        txtPaymentStatus = new JTextField("Unpaid");

        formPanel.add(new JLabel("ID"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Equipment ID"));
        formPanel.add(txtEquipmentId);
        formPanel.add(new JLabel("Customer ID"));
        formPanel.add(txtCustomerId);
        formPanel.add(new JLabel("Branch ID"));
        formPanel.add(txtBranchId);
        formPanel.add(new JLabel("Start Date"));
        formPanel.add(txtStartDate);
        formPanel.add(new JLabel("End Date"));
        formPanel.add(txtEndDate);
        formPanel.add(new JLabel("Status"));
        formPanel.add(txtStatus);
        formPanel.add(new JLabel("Payment Status"));
        formPanel.add(txtPaymentStatus);

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

        model = new DefaultTableModel(new String[]{"ID", "Equipment ID", "Customer ID", "Branch ID", "Start Date", "End Date", "Status", "Payment Status"}, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Rental List"));
        add(sp, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addRental());
        btnUpdate.addActionListener(e -> updateRental());
        btnDelete.addActionListener(e -> deleteRental());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadRentals());
    }

    private void loadRentals() {
        try {
            model.setRowCount(0);
            List<Rental> rentals = rentalDAO.getAll();
            for (Rental rental : rentals) {
                model.addRow(new Object[]{
                        rental.getId(),
                        rental.getEquipmentId(),
                        rental.getCustomerId(),
                        rental.getBranchId(),
                        rental.getStartDate(),
                        rental.getEndDate(),
                        rental.getStatus(),
                        rental.getPaymentStatus()
                });
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void addRental() {
        try {
            int equipmentId = Integer.parseInt(txtEquipmentId.getText().trim());
            int customerId = Integer.parseInt(txtCustomerId.getText().trim());
            int branchId = Integer.parseInt(txtBranchId.getText().trim());
            String startDate = txtStartDate.getText().trim();
            String endDate = txtEndDate.getText().trim();
            String status = txtStatus.getText().trim();
            String paymentStatus = txtPaymentStatus.getText().trim();

            Rental rental = new Rental(0, equipmentId, customerId, branchId, LocalDate.parse(startDate), LocalDate.parse(endDate), status, paymentStatus);

            boolean ok = rentalDAO.add(rental);
            JOptionPane.showMessageDialog(this, ok ? "✅ Rental Added!" : "❌ Add Failed");

            loadRentals();
            clearForm();
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateRental() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Select a row first");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            int equipmentId = Integer.parseInt(txtEquipmentId.getText().trim());
            int customerId = Integer.parseInt(txtCustomerId.getText().trim());
            int branchId = Integer.parseInt(txtBranchId.getText().trim());
            String startDate = txtStartDate.getText().trim();
            String endDate = txtEndDate.getText().trim();
            String status = txtStatus.getText().trim();
            String paymentStatus = txtPaymentStatus.getText().trim();

            Rental rental = new Rental(id, equipmentId, customerId, branchId, LocalDate.parse(startDate), LocalDate.parse(endDate), status, paymentStatus);

            boolean ok = rentalDAO.update(rental);
            JOptionPane.showMessageDialog(this, ok ? "✅ Rental Updated!" : "❌ Update Failed");

            loadRentals();
            clearForm();
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteRental() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Select a row first");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete rental ID " + id + " ?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = rentalDAO.delete(id);
                JOptionPane.showMessageDialog(this, ok ? "✅ Rental Deleted!" : "❌ Delete Failed");
                loadRentals();
                clearForm();
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtEquipmentId.setText("");
        txtCustomerId.setText("");
        txtBranchId.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");
        txtStatus.setText("Active");
        txtPaymentStatus.setText("Unpaid");
        table.clearSelection();
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RentalApp().setVisible(true));
    }
}
