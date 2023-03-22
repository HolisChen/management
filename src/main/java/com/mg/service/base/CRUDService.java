package com.mg.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class CRUDService<T, ID, Rep extends JpaRepository<T, ID>> {
    protected abstract Rep getDao();

    public T save(T value) {
        return getDao().save(value);
    }

    public Optional<T> getById(ID id) {
        return getDao().findById(id);
    }

    public void deleteById(ID id) {
        getDao().deleteById(id);
    }

    public void deleteByIds(Collection<ID> ids) {
        getDao().deleteAllById(ids);
    }
}
