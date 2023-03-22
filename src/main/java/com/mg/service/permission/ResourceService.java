package com.mg.service.permission;

import com.mg.dao.BaseEntity;
import com.mg.dao.entity.ResourceEntity;
import com.mg.dao.repository.ResourceRepository;
import com.mg.domain.vo.base.MenuTree;
import com.mg.enums.ResourceTypeEnum;
import com.mg.mapper.ResourceMapper;
import com.mg.service.base.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceService extends CRUDService<ResourceEntity, Integer, ResourceRepository> {
    private final ResourceRepository repository;
    private final ResourceMapper resourceMapper;

    public List<ResourceEntity> getByResourceIds(List<Integer> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return Collections.emptyList();
        }
        return repository.findByIdInAndDeleteAtIsNull(resourceIds);
    }

    public Optional<ResourceEntity> getById(Integer id) {
        return this.getByResourceIds(Collections.singletonList(id))
                .stream()
                .findFirst();
    }

    public Optional<ResourceEntity> getByCode(String resourceCode) {
        return repository.findByResourceCodeAndDeleteAtIsNull(resourceCode);
    }

    public Integer addResource(ResourceEntity entity) {
        if (this.getByCode(entity.getResourceCode()).isPresent()) {
            throw new IllegalArgumentException("资源code已存在");
        }
        Integer parentId = entity.getParentId();
        if (parentId != null && parentId != 0 && CollectionUtils.isEmpty(this.getByResourceIds(Collections.singletonList(parentId)))) {
            throw new IllegalArgumentException("父级资源不存在");
        }
        super.save(entity);
        return entity.getId();
    }

    public void removeResources(List<Integer> resourceIds) {
        List<Integer> needDeleted = this.getByResourceIds(resourceIds)
                .stream().map(BaseEntity::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(needDeleted)) {
            return;
        }
        this.deleteByIds(needDeleted);
    }

    public List<MenuTree> getMenuTree() {
        List<ResourceEntity> resources = repository.findAllByResourceTypeIn(ResourceTypeEnum.menuAndCollection());
        return buildChildren(0, resources);
    }

    private List<MenuTree> buildChildren(Integer parentId, List<ResourceEntity> sources) {
        List<ResourceEntity> childList = sources.stream().filter(item -> Objects.equals(item.getParentId(), parentId))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childList)) {
            return Collections.emptyList();
        }
        sources.removeAll(childList);
        return childList.stream()
                .map(item -> {
                    MenuTree menuTree = resourceMapper.entityToMenuTree(item);
                    menuTree.setChildren(buildChildren(item.getId(), sources));
                    return menuTree;
                })
                .collect(Collectors.toList());

    }

    @Override
    protected ResourceRepository getDao() {
        return repository;
    }
}
