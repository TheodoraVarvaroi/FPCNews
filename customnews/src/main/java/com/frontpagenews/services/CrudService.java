package com.frontpagenews.services;

import java.util.List;

public interface CrudService<T> {
    T save(T entity);
    List<T> getAll();
    T getById(String id);
    void delete(String id);
}