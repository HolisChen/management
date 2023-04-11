package com.github.hollis.domain.dto.permission;

import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.domain.dto.base.PageRequest;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class QueryUserDto extends PageRequest {
    private String loginId;
    private Byte status;

    public Specification<UserEntity> toSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(this.loginId)) {
                predicates.add(criteriaBuilder.equal(root.get("loginId"), this.loginId));
            }
            if (null != status) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
