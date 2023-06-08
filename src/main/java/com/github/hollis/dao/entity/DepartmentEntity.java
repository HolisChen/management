package com.github.hollis.dao.entity;

import com.github.hollis.dao.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_department", catalog = "management")
@DynamicInsert
@DynamicUpdate
public class DepartmentEntity extends BaseEntity {

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_description")
    private String departmentDescription;

    @Column(name = "parent_department_id")
    private Integer parentDepartmentId;
}
