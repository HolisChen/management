package com.github.hollis.domain.vo.permission;

import com.github.hollis.dao.entity.DepartmentEntity;
import com.github.hollis.domain.vo.base.Tree;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DepartmentTree extends Tree<DepartmentTree> {
    private Integer id;
    private String departmentName;
    private String departmentDescription;

    private boolean checked;


    public static DepartmentTree from(DepartmentEntity departmentEntity) {
        DepartmentTree departmentTree = new DepartmentTree();
        departmentTree.setId(departmentEntity.getId());
        departmentTree.setDepartmentName(departmentEntity.getDepartmentName());
        departmentTree.setDepartmentDescription(departmentEntity.getDepartmentDescription());
        return departmentTree;
    }
}
