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

public class Reservation {

    private int id;
    private int equipmentId;
    private int customerId;
    private int branchId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String paymentStatus;

    // Constructor for creating new reservations
    public Reservation(int equipmentId, int customerId, int branchId, int branchId1, LocalDate startDate, LocalDate endDate, String status) {
        this.equipmentId = equipmentId;
        this.customerId = customerId;
        this.branchId = branchId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    // Constructor for getting reservations from DB (when ID is known)
    public Reservation(int id, int equipmentId, int customerId, int branchId, LocalDate startDate, LocalDate endDate, String status, String paymentStatus) {
    this.id = id;
    this.equipmentId = equipmentId;
    this.customerId = customerId;
    this.branchId = branchId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.paymentStatus = paymentStatus;
}

    // Getters and setters
    public int getId() { return id; }
    public int getEquipmentId() { return equipmentId; }
    public int getCustomerId() { return customerId; }
    public int getBranchId() { return branchId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public String getPaymentStatus() { return paymentStatus; }

    public void setId(int id) { this.id = id; }
    public void setEquipmentId(int equipmentId) { this.equipmentId = equipmentId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setBranchId(int branchId) { this.branchId = branchId; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setStatus(String status) { this.status = status; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}

