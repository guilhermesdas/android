package com.example.contatos.model;

import java.util.List;
import java.util.Optional;

// https://www.baeldung.com/java-dao-pattern
// T : A generic type is a generic class or interface that is parameterized over types.
public interface DAO<T> {

    Optional<T> get(long id);

    List<T> getAll();

    boolean add(T t);

    boolean update(T t);

    boolean delete(long id);

}
