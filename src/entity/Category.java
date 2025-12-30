/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author LENOVO
 */
public class Category {
    
    private int id;
    private String name;
    private double basePriceFactor;
    private double weekendMultiplier;
    private double lateFeePerDay;

    public Category() {}

    public Category(int id, String name, double basePriceFactor, double weekendMultiplier, double lateFeePerDay) {
        this.id = id;
        this.name = name;
        this.basePriceFactor = basePriceFactor;
        this.weekendMultiplier = weekendMultiplier;
        this.lateFeePerDay = lateFeePerDay;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBasePriceFactor() { return basePriceFactor; }
    public void setBasePriceFactor(double basePriceFactor) { this.basePriceFactor = basePriceFactor; }

    public double getWeekendMultiplier() { return weekendMultiplier; }
    public void setWeekendMultiplier(double weekendMultiplier) { this.weekendMultiplier = weekendMultiplier; }

    public double getLateFeePerDay() { return lateFeePerDay; }
    public void setLateFeePerDay(double lateFeePerDay) { this.lateFeePerDay = lateFeePerDay; }
}

