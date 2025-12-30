/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author LENOVO
 */
import entity.Customer;
import java.util.List; 

public interface CustomerDAO {
     boolean add(Customer c) throws Exception;

    boolean update(Customer c) throws Exception;

    boolean delete(int id) throws Exception;

    Customer getById(int id) throws Exception;

    List<Customer> getAll() throws Exception;
    
}
