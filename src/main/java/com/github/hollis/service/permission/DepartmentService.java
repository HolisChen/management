package com.github.hollis.service.permission;

import com.github.hollis.dao.BaseEntity;
import com.github.hollis.dao.entity.DepartmentEntity;
import com.github.hollis.dao.repository.DepartmentRepository;
import com.github.hollis.domain.vo.base.Tree;
import com.github.hollis.domain.vo.permission.DepartmentTree;
import com.github.hollis.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DepartmentService extends CRUDService<DepartmentEntity, Integer, DepartmentRepository> {

    private final DepartmentRepository departmentRepository;

    @Override
    protected DepartmentRepository getDao() {
        return departmentRepository;
    }

//    @Cacheable(cacheNames = "department" ,key = "'tree'")
    public List<DepartmentTree> queryTree() {
        return queryTree(Collections.emptyList());
    }
    public List<DepartmentTree> queryTree(List<Integer> needCheckedDepIds) {
        List<DepartmentTree> resultList = new ArrayList<>();
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        Map<Integer, DepartmentTree> departmentMap = departmentEntities.stream()
                .map(DepartmentTree::from)
                .collect(Collectors.toMap(DepartmentTree::getId, Function.identity()));

        for (DepartmentEntity entity : departmentEntities) {
            DepartmentTree currentNode = departmentMap.get(entity.getId());
            if (needCheckedDepIds.contains(currentNode.getId())) {
                currentNode.setChecked(true);
            }
            if (Objects.equals(entity.getParentDepartmentId(), -1)) {
                //根部门
                resultList.add(currentNode);
            } else {
                //子部门
                Optional.ofNullable(departmentMap.get(entity.getParentDepartmentId()))
                        .ifPresent(tree -> {
                            if (null == tree.getChildren()) {
                                tree.setChildren(new ArrayList<>());
                            }
                            tree.getChildren().add(currentNode);
                        });
            }
        }
        return resultList;
    }

    public List<DepartmentEntity> findAll() {
        return departmentRepository.findAll();
    }
}
