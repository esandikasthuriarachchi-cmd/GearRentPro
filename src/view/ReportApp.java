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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.RentalDAO;

public class ReportApp extends JFrame {

    private final RentalDAO rentalDAO = new RentalDAOImpl();
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtStartDate, txtEndDate;

    public ReportApp() {
        setTitle("GearRentPro - Rental Report");
        setSize(900, 500);  // Set the window size
        setLocationRelativeTo(null);  // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close the window properly

        initUI();  // Initialize the UI components
    }

    private void initUI() {
        setLayout(new BorderLayout());  // Set layout for the main frame

        // ===== FORM PANEL (Date Input for Report) =====
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));  // Create a grid layout for the form
        formPanel.setBorder(BorderFactory.createTitledBorder("Filter by Date"));  // Add title to the form panel

        txtStartDate = new JTextField();  // Text field for the start date
        txtEndDate = new JTextField();  // Text field for the end date

        // Add the labels and text fields to the form panel
        formPanel.add(new JLabel("Start Date (YYYY-MM-DD)"));
        formPanel.add(txtStartDate);
        formPanel.add(new JLabel("End Date (YYYY-MM-DD)"));
        formPanel.add(txtEndDate);

        // ===== BUTTON PANEL =====
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Create panel for the button
        JButton btnGenerateReport = new JButton("Generate Report");  // Create the button
        btnPanel.add(btnGenerateReport);  // Add the button to the panel

        // ===== TABLE PANEL =====
        model = new DefaultTableModel(
                new String[]{"Rental ID", "Customer ID", "Equipment ID", "Branch ID", "Start Date", "End Date", "Status", "Payment Status"}, 0
        );  // Define the columns for the table
        table = new JTable(model);  // Create a table with the defined model
        JScrollPane sp = new JScrollPane(table);  // Add the table inside a scroll pane
        sp.setBorder(BorderFactory.createTitledBorder("Rental List"));  // Add title to the table

        // ===== ADD COMPONENTS TO THE FRAME =====
        add(formPanel, BorderLayout.NORTH);  // Add the form panel (date inputs) to the top
        add(sp, BorderLayout.CENTER);  // Add the table to the center
        add(btnPanel, BorderLayout.SOUTH);  // Add the button panel to the bottom

        // ===== BUTTON ACTIONS =====
        btnGenerateReport.addActionListener(e -> generateReport());  // Add action listener for the button
    }

    private void generateReport() {
        try {
            String startDate = txtStartDate.getText().trim();  // Get the start date from the input
            String endDate = txtEndDate.getText().trim();  // Get the end date from the input

            // Ensure both fields are filled
            if (startDate.isEmpty() || endDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please provide both start and end dates");
                return;  // Exit if the input fields are empty
            }

            // Fetch rentals in the given date range
            List<Rental> rentals = rentalDAO.getRentalsByDateRange(startDate, endDate);

            if (rentals.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ No rentals found in this date range");
            } else {
                model.setRowCount(0);  // Clear previous results in the table
                for (Rental r : rentals) {
                    model.addRow(new Object[]{  // Add each rental to the table
                            r.getId(),
                            r.getCustomerId(),
                            r.getEquipmentId(),
                            r.getBranchId(),
                            r.getStartDate(),
                            r.getEndDate(),
                            r.getStatus(),
                            r.getPaymentStatus()  // Display payment status as well
                    });
                }
            }

        } catch (Exception ex) {
            showError(ex);  // Show error message if there's an exception
        }
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportApp().setVisible(true));  // Run the UI on the Event Dispatch Thread
    }
}
