package com.github.hollis.service.permission;

import com.github.hollis.domain.vo.permission.ResourceTree;
import com.github.hollis.dao.entity.ResourceEntity;
import com.github.hollis.dao.repository.ResourceRepository;
import com.github.hollis.domain.dto.permission.UpdateResourceDto;
import com.github.hollis.mapper.ResourceMapper;
import com.github.hollis.service.CRUDService;
import com.github.hollis.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
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
        return repository.findAllById(resourceIds);
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

    public void updateResource(UpdateResourceDto dto) {
        Optional<ResourceEntity> exists = repository.findById(dto.getId());
        if (!exists.isPresent()) {
            throw new IllegalArgumentException("修改的资源未找到");
        }
        ResourceEntity entity = exists.get();
        if (!Objects.equals(entity.getResourceCode(),dto.getResourceCode())
            && this.getByCode(dto.getResourceCode()).isPresent()) {
            throw new IllegalArgumentException("资源code已存在");
        }
        dto.update(entity);
        repository.save(entity);
    }

    /**
     * 逻辑删除resource
     * @param resourceIds
     */
    public void removeResources(List<Integer> resourceIds) {
        Integer userId = UserUtil.getCurrentUserId();
        repository.logicDelete(resourceIds,userId);
    }

    public List<ResourceTree> getResourceTree(List<Byte> resourceTypes) {
        List<ResourceEntity> resources = repository.findAllByResourceTypeInAndDeleteAtIsNull(resourceTypes);
        return buildChildren(0, resources);
    }

    private List<ResourceTree> buildChildren(Integer parentId, List<ResourceEntity> sources) {
        List<ResourceEntity> childList = sources.stream().filter(item -> Objects.equals(item.getParentId(), parentId))
                .sorted(Comparator.comparing(ResourceEntity::getSort))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childList)) {
            return Collections.emptyList();
        }
        sources.removeAll(childList);
        return childList.stream()
                .map(item -> {
                    ResourceTree resourceTree = resourceMapper.entityToMenuTree(item);
                    resourceTree.setChildren(buildChildren(item.getId(), sources));
                    return resourceTree;
                })
                .collect(Collectors.toList());

    }

    /**
     * 通过userID获取所有有权限的资源
     * @param userId
     * @return
     */
    public List<ResourceEntity> getAuthorizedResources(Integer userId) {
        return repository.findAuthorizedByUserId(userId);
    }

    @Override
    protected ResourceRepository getDao() {
        return repository;
    }
}
