package edu.depaul.ntessema.jaxrs.repository;

import java.util.List;

public interface Repository<T> {
    T getOne(Integer id);
    List<T> getAll();
    Integer add(T quote);
    T update(Integer id, T quote);
    void delete(Integer id);
}
