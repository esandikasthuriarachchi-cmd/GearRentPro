/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author LENOVO
 */


import dao.CategoryDAO;
import dao.impl.CategoryDAOImpl;
import entity.Category;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryApp extends JFrame {

    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtBasePrice, txtWeekend, txtLateFee;

    public CategoryApp() {
        setTitle("GearRentPro - Category Management");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadCategories();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Category Form"));

        txtId = new JTextField();
        txtId.setEditable(false);

        txtName = new JTextField();
        txtBasePrice = new JTextField();
        txtWeekend = new JTextField();
        txtLateFee = new JTextField();

        form.add(new JLabel("ID"));
        form.add(txtId);

        form.add(new JLabel("Name"));
        form.add(txtName);

        form.add(new JLabel("Base Price Factor"));
        form.add(txtBasePrice);

        form.add(new JLabel("Weekend Multiplier"));
        form.add(txtWeekend);

        form.add(new JLabel("Late Fee / Day"));
        form.add(txtLateFee);

        // ===== BUTTONS =====
        JPanel buttons = new JPanel(new GridLayout(1, 5, 10, 10));
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

        JPanel left = new JPanel(new BorderLayout());
        left.add(form, BorderLayout.CENTER);
        left.add(buttons, BorderLayout.SOUTH);

        add(left, BorderLayout.WEST);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Base Price", "Weekend Multiplier", "Late Fee"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int r = table.getSelectedRow();
                txtId.setText(model.getValueAt(r, 0).toString());
                txtName.setText(model.getValueAt(r, 1).toString());
                txtBasePrice.setText(model.getValueAt(r, 2).toString());
                txtWeekend.setText(model.getValueAt(r, 3).toString());
                txtLateFee.setText(model.getValueAt(r, 4).toString());
            }
        });

        // actions
        btnAdd.addActionListener(e -> addCategory());
        btnUpdate.addActionListener(e -> updateCategory());
        btnDelete.addActionListener(e -> deleteCategory());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadCategories());
    }

    private void loadCategories() {
        try {
            model.setRowCount(0);
            List<Category> list = categoryDAO.getAll();
            for (Category c : list) {
                model.addRow(new Object[]{
                        c.getId(),
                        c.getName(),
                        c.getBasePriceFactor(),
                        c.getWeekendMultiplier(),
                        c.getLateFeePerDay()
                });
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void addCategory() {
        try {
            Category c = new Category(
                    0,
                    txtName.getText(),
                    Double.parseDouble(txtBasePrice.getText()),
                    Double.parseDouble(txtWeekend.getText()),
                    Double.parseDouble(txtLateFee.getText())
            );

            boolean ok = categoryDAO.add(c);
            JOptionPane.showMessageDialog(this, ok ? "✅ Category Added" : "❌ Failed");
            loadCategories();
            clearForm();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateCategory() {
        try {
            Category c = new Category(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    Double.parseDouble(txtBasePrice.getText()),
                    Double.parseDouble(txtWeekend.getText()),
                    Double.parseDouble(txtLateFee.getText())
            );

            boolean ok = categoryDAO.update(c);
            JOptionPane.showMessageDialog(this, ok ? "✅ Category Updated" : "❌ Failed");
            loadCategories();
            clearForm();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteCategory() {
        try {
            int id = Integer.parseInt(txtId.getText());
            boolean ok = categoryDAO.delete(id);
            JOptionPane.showMessageDialog(this, ok ? "✅ Category Deleted" : "❌ Failed");
            loadCategories();
            clearForm();

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtBasePrice.setText("");
        txtWeekend.setText("");
        txtLateFee.setText("");
        table.clearSelection();
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}
