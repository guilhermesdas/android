package com.example.loja.model;

import java.util.ArrayList;

public interface DAO {

    boolean create(Object obj);
    boolean update(int id, Object obj);
    boolean delete(int id);
    Object get(int id);
    ArrayList<Object> getList();

}