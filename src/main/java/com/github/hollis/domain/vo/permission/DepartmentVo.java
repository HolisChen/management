package com.github.hollis.domain.vo.permission;

import com.github.hollis.domain.vo.base.BaseVo;
import lombok.Data;

@Data
public class DepartmentVo extends BaseVo {
    private String departmentName;
    private String departmentDescription;
    private Integer parentDepartmentId;
}
