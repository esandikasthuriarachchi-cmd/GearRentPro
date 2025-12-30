/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author LENOVO
 */

import entity.Equipment;
import java.util.List;

public interface EquipmentDAO {
    boolean add(Equipment e) throws Exception;
    boolean update(Equipment e) throws Exception;
    boolean delete(int id) throws Exception;
    Equipment getById(int id) throws Exception;
    List<Equipment> getAll() throws Exception;
}
