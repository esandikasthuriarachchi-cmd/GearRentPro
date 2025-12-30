/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author LENOVO
 */


import entity.Rental;
import java.util.List;

public interface RentalDAO {
    // CRUD operations
    boolean add(Rental rental) throws Exception;
    boolean update(Rental rental) throws Exception;
    boolean delete(int id) throws Exception;
    Rental getById(int id) throws Exception;
    List<Rental> getAll() throws Exception;

    // Method to get rentals by date range
    List<Rental> getRentalsByDateRange(String startDate, String endDate) throws Exception;
}
