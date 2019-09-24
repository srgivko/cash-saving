package by.sivko.cashsaving.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext
    EntityManager entityManager;

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public Optional<T> getById(PK id) {
        T t = this.entityManager.find(entityClass, id);
        return t != null ? Optional.of(t) : Optional.empty();
    }

    @Override
    public T add(T t) {
        this.entityManager.persist(t);
        return t;
    }

    @Override
    public T delete(T t) {
        this.entityManager.remove(t);
        return t;
    }

    @Override
    public T edit(T t) {
        return this.entityManager.merge(t);
    }

    @Override
    public List<T> getAll() {
        List<T> resultList = this.entityManager
                .createQuery(String.format("select t from %s t", entityClass.getSimpleName()), entityClass)
                .getResultList();
        return resultList != null ? resultList : Collections.emptyList();
    }

}
