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
import dao.CustomerDAO;
import dao.EquipmentDAO;
import dao.ReservationDAO;

import dao.impl.CategoryDAOImpl;
import dao.impl.CustomerDAOImpl;
import dao.impl.EquipmentDAOImpl;
import dao.impl.ReservationDAOImpl;

import entity.Category;
import entity.Customer;
import entity.Equipment;
import entity.Reservation;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws Exception {

        // ✅ DAO objects
        CategoryDAO cDao = new CategoryDAOImpl();
        EquipmentDAO eDao = new EquipmentDAOImpl();
        ReservationDAO rDao = new ReservationDAOImpl();
        CustomerDAO customerDAO = new CustomerDAOImpl(); // ✅ ONLY ONCE

        // =========================
        // ✅ 1) TEST CATEGORY (print)
        // =========================
        System.out.println("===== CATEGORIES =====");
        for (Category c : cDao.getAll()) {
            System.out.println(
                    c.getId() + " | " + c.getName()
                            + " | base=" + c.getBasePriceFactor()
                            + " | weekend=" + c.getWeekendMultiplier()
                            + " | late=" + c.getLateFeePerDay()
            );
        }

        // =========================
        // ✅ 2) TEST EQUIPMENT (print)
        // =========================
        System.out.println("\n===== EQUIPMENT =====");
        for (Equipment e : eDao.getAll()) {
            System.out.println(
                    e.getId() + " | " + e.getCode()
                            + " | " + e.getBrand()
                            + " | " + e.getModel()
                            + " | " + e.getDailyPrice()
                            + " | " + e.getStatus()
            );
        }

        // =====================================
        // ✅ 3) TEST RESERVATION (add + print)
        // =====================================
        System.out.println("\n===== RESERVATIONS (ADD TEST) =====");

        int equipmentId = 1;
        int customerId = 1;
        int branchId = 1;

        boolean rAdded = rDao.add(
                new Reservation(
                        0,
                        equipmentId,
                        customerId,
                        branchId,
                        LocalDate.of(2025, 12, 26),
                        LocalDate.of(2025, 12, 28),
                        "Active"
                )
        );

        System.out.println("✅ Reservation Added: " + rAdded);

        System.out.println("\n===== RESERVATIONS (LIST) =====");
        for (Reservation r : rDao.getAll()) {
            System.out.println(
                    r.getId()
                            + " | eq=" + r.getEquipmentId()
                            + " | cus=" + r.getCustomerId()
                            + " | branch=" + r.getBranchId()
                            + " | " + r.getStartDate()
                            + " -> " + r.getEndDate()
                            + " | " + r.getStatus()
            );
        }

        System.out.println("\n===== RESERVATIONS (CANCEL TEST) =====");
        boolean cancelled = rDao.cancel(3);
        System.out.println("✅ Cancelled: " + cancelled);

        System.out.println("\n===== RESERVATIONS (GET BY ID TEST) =====");
        Reservation one = rDao.getById(3);
        if (one != null) {
            System.out.println("✅ Found: " + one.getId() + " | " + one.getStatus());
        } else {
            System.out.println("❌ Not found");
        }

        System.out.println("\n===== RESERVATIONS (UPDATE TEST) =====");
        Reservation rr = rDao.getById(3);
        if (rr != null) {
            rr.setStatus("Active");
            boolean updated = rDao.update(rr);
            System.out.println("✅ Updated: " + updated);
        }

        // =========================
        // ✅ CUSTOMERS (ADD + LIST)
        // =========================
        System.out.println("\n===== CUSTOMERS (ADD TEST) =====");
        boolean cAdded = customerDAO.add(new Customer(
                0,
                "Nimal Perera",
                "200012345678",
                "0771234567",
                "nimal@gmail.com",
                1
        ));
        System.out.println("✅ Customer Added: " + cAdded);

        System.out.println("\n===== CUSTOMERS (LIST) =====");
        for (Customer c : customerDAO.getAll()) {
            System.out.println(
                    c.getId() + " | " + c.getName()
                            + " | NIC=" + c.getNic()
                            + " | " + c.getContact()
                            + " | " + c.getEmail()
                            + " | membership=" + c.getMembershipId()
            );
        }

        // =========================
        // ✅ CUSTOMERS (GET BY ID)
        // =========================
        System.out.println("\n===== CUSTOMERS (GET BY ID TEST) =====");
        Customer oneCus = customerDAO.getById(1);
        if (oneCus != null) {
            System.out.println("✅ Found: " + oneCus.getId() + " | " + oneCus.getName());
        } else {
            System.out.println("❌ Customer not found");
        }

        // =========================
        // ✅ CUSTOMERS (UPDATE)
        // =========================
        System.out.println("\n===== CUSTOMERS (UPDATE TEST) =====");
        if (oneCus != null) {
            oneCus.setName(oneCus.getName() + " (Updated)");
            boolean cusUpdated = customerDAO.update(oneCus);
            System.out.println("✅ Customer Updated: " + cusUpdated);
        }

        Customer updatedCus = customerDAO.getById(1);
        if (updatedCus != null) {
            System.out.println("✅ After Update: " + updatedCus.getId() + " | " + updatedCus.getName());
        }

        // =========================
        // ✅ CUSTOMERS (DELETE)
        // =========================
        System.out.println("\n===== CUSTOMERS (DELETE TEST) =====");

        // ✅ Use the correct existing ID here (you said 10 exists)
        boolean deleted = customerDAO.delete(10);
        System.out.println("✅ Customer Deleted: " + deleted);

        Customer deletedCus = customerDAO.getById(10);
        System.out.println("✅ After Delete (should be null): " + deletedCus);
    }
}
