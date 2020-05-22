package edu.depaul.ntessema.jaxrs.data.repository;

/**
 * This interface is intended to mimic a typical JPA Repository to some extent.
 * Refer to https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
 * Only a subset of the methods in a JPA CrudRepository are exposed in the interface.
 */
public interface Repository<T, ID> {
    T findById(ID id);
    Iterable<T> findAll();
    Iterable<T> findAllById(Iterable<ID> ids);
    T save(T quote);
    T update(T quote);
    boolean delete(ID id);
    boolean existsById(ID id);
    int count();
    Iterable<ID> getIds();
}
