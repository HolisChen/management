package com.mg.service.base;

import com.mg.dao.entity.ResourceEntity;
import com.mg.dao.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository repository;

    public List<ResourceEntity> getByResourceIds(List<Integer> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return Collections.emptyList();
        }
        return repository.findByIdInAndDeleteAtIsNull(resourceIds);
    }
}
