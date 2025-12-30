/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author LENOVO
 */


import java.time.LocalDate;

public class Report {

    private int rentalId;
    private int customerId;
    private int equipmentId;
    private int branchId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Active, Cancelled
    private String paymentStatus; // Paid, Unpaid

    // Constructor to initialize the fields
    public Report(int rentalId, int customerId, int equipmentId, int branchId, LocalDate startDate, LocalDate endDate, String status, String paymentStatus) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.equipmentId = equipmentId;
        this.branchId = branchId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    // Getters and setters for each field
    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Rental ID: " + rentalId + ", Customer ID: " + customerId + ", Equipment ID: " + equipmentId +
                ", Branch ID: " + branchId + ", Start Date: " + startDate + ", End Date: " + endDate + ", Status: " + status +
                ", Payment Status: " + paymentStatus;
    }
}
