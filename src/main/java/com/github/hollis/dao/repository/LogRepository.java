package com.github.hollis.dao.repository;

import com.github.hollis.dao.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogRepository extends JpaRepository<LogEntity, Integer>, JpaSpecificationExecutor<LogEntity> {
}
