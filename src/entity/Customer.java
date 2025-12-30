/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author LENOVO
 */
public class Customer {
    private int id;
    private String name;
    private String nic;
    private String contact;
    private String email;
    private int membershipId;

    public Customer() {}

    public Customer(int id, String name, String nic, String contact, String email, int membershipId) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.contact = contact;
        this.email = email;
        this.membershipId = membershipId;
    }

    // getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getNic() { return nic; }
    public String getContact() { return contact; }
    public String getEmail() { return email; }
    public int getMembershipId() { return membershipId; }

    // setters ✅
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setNic(String nic) { this.nic = nic; }
    public void setContact(String contact) { this.contact = contact; }
    public void setEmail(String email) { this.email = email; }
    public void setMembershipId(int membershipId) { this.membershipId = membershipId; }
}