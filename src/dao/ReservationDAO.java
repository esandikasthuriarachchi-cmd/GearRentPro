/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author LENOVO
 */


import entity.Reservation;
import java.util.List;

public interface ReservationDAO {

    boolean add(Reservation r) throws Exception;
    boolean update(Reservation r) throws Exception;
    boolean cancel(int id) throws Exception;
    Reservation getById(int id) throws Exception;
    List<Reservation> getAll() throws Exception;

    // New method to get reservations by date range
    List<Reservation> getReservationsByDateRange(String startDate, String endDate) throws Exception;
}
