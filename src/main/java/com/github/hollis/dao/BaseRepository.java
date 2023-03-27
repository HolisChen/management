package com.github.hollis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    @Override
    @Query("select e from #{#entityName} e where e.deleteAt is null")
    List<T> findAll();

    @Override
    @Query("select e from #{#entityName} e where e.id in ?1 and e.deleteAt is null ")
    List<T> findAllById(Iterable<ID> ids);

    @Override
    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleteAt is null")
    T getOne(ID id);

    @Override
    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleteAt is null")
    T getById(ID id);

    @Override
    default boolean existsById(ID id) {
        return getOne(id) != null;
    }

    @Override
    default Optional<T> findById(ID id) {
        return Optional.ofNullable(getById(id));
    }

    @Query("update #{#entityName} e set e.deleteAt = CURRENT_TIMESTAMP , e.deleteBy = ?2 WHERE e.id in ?1 and e.deleteAt is null")
    @Transactional
    @Modifying
    int logicDelete(Iterable<ID> ids, Integer userId);

    @Query("update #{#entityName} e set e.deleteAt = CURRENT_TIMESTAMP , e.deleteBy = ?2 WHERE e.id = ?1 and e.deleteAt is null")
    @Transactional
    @Modifying
    int logicDelete(ID id, Integer userId);
}
