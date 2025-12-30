/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author LENOVO
 */


public class Branch {

    private int id;
    private String code;
    private String name;
    private String address;
    private String contact;

    public Branch() {}

    public Branch(int id, String code, String name, String address, String contact) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getContact() { return contact; }

    public void setId(int id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setContact(String contact) { this.contact = contact; }
}

