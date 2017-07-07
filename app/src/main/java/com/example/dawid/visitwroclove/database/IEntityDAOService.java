package com.example.dawid.visitwroclove.database;

import java.util.List;

/**
 * Created by Dawid on 06.07.2017.
 */

public interface IEntityDAOService<T> {
    void add(T entity);
    void deleteById(final int id);
    T getById(final int id);
    List<T> getAll();
}
