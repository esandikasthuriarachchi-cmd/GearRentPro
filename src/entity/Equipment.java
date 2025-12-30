/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author LENOVO
 */
public class Equipment {
    private int id;
    private String code;
    private int categoryId;
    private int branchId;
    private String brand;
    private String model;
    private double dailyPrice;
    private double deposit;
    private String status;

    public Equipment() {}

    public Equipment(int id, String code, int categoryId, int branchId,
                     String brand, String model, double dailyPrice,
                     double deposit, String status) {
        this.id = id;
        this.code = code;
        this.categoryId = categoryId;
        this.branchId = branchId;
        this.brand = brand;
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.deposit = deposit;
        this.status = status;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public int getCategoryId() { return categoryId; }
    public int getBranchId() { return branchId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getDailyPrice() { return dailyPrice; }
    public double getDeposit() { return deposit; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setBranchId(int branchId) { this.branchId = branchId; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setDailyPrice(double dailyPrice) { this.dailyPrice = dailyPrice; }
    public void setDeposit(double deposit) { this.deposit = deposit; }
    public void setStatus(String status) { this.status = status; }
}