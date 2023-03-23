package com.mg.dao.entity;

import com.mg.dao.BaseEntity;
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
@Table(name = "t_resource", catalog = "management")
@DynamicInsert
@DynamicUpdate
public class ResourceEntity extends BaseEntity {

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "resource_code")
    private String resourceCode;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource_type")
    /**
     * 1 功能
     * 2 菜单
     * 3 集合
     */
    private Byte resourceType;

    @Column(name = "resource_description")
    private String resourceDescription;

    @Column(name = "resource_url")
    private String resourceUrl;

    private Integer sort;

    private String icon;
}
