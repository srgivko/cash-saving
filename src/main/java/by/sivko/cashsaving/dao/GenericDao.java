package by.sivko.cashsaving.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, PK extends Serializable> {
    Optional<T> getById(PK id);

    T add(T t);

    T delete(T t);

    T edit(T t);

    List<T> getAll();
}
