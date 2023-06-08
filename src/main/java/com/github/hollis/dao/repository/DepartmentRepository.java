package com.github.hollis.dao.repository;

import com.github.hollis.dao.BaseRepository;
import com.github.hollis.dao.entity.DepartmentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends BaseRepository<DepartmentEntity, Integer> {
}
