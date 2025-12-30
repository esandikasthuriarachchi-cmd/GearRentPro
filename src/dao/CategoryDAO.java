/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author LENOVO
 */

import entity.Category;
import java.util.List;

public interface CategoryDAO {
    boolean add(Category c) throws Exception;
    boolean update(Category c) throws Exception;
    boolean delete(int id) throws Exception;
    Category getById(int id) throws Exception;
    List<Category> getAll() throws Exception;
    
}
