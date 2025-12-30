/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author LENOVO
 */
import entity.Branch;
import java.util.List;

public interface BranchDAO {
    boolean add(Branch b) throws Exception;
    boolean update(Branch b) throws Exception;
    boolean delete(int id) throws Exception;
    Branch getById(int id) throws Exception;
    List<Branch> getAll() throws Exception;
}
