package com.github.hollis.service;

import com.github.hollis.dao.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public abstract class CRUDService<T, ID extends Serializable, Rep extends JpaRepository<T, ID>> {
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

    public void logicDeleteById(ID id, Integer userId) {
        Rep dao = getDao();
        if (dao instanceof BaseRepository) {
            ((BaseRepository) dao).logicDelete(id, userId);
            return;
        }
        throw new UnsupportedOperationException("不支持逻辑删除！");
    }

    public void logicDeleteById(Iterable<ID> ids, Integer userId) {
        Rep dao = getDao();
        if (dao instanceof BaseRepository) {
            ((BaseRepository) dao).logicDelete(ids, userId);
            return;
        }
        throw new UnsupportedOperationException("不支持逻辑删除！");
    }

    public void saveAll(Collection<T> entities) {
        getDao().saveAll(entities);
    }
}
