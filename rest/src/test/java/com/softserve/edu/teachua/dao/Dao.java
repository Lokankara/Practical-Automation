package com.softserve.edu.teachua.dao;

import java.util.List;

public interface Dao<T> {

    long create(T entity);

    List<T> getAll();

    T getById(int id);

    void update(T entity);

    boolean deleteById(int id);

    void saveToFile(T t);
}
