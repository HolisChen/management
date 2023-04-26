package com.github.hollis.domain.dto.log;

import com.github.hollis.dao.entity.LogEntity;
import com.github.hollis.domain.dto.base.PageRequest;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class LogQueryDto extends PageRequest {
    private String operationType;
    private String operationTarget;
    private Integer successFlag;

    public Specification<LogEntity> toSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(this.operationType)) {
                predicates.add(criteriaBuilder.equal(root.get("operationType"), this.operationType));
            }
            if (StringUtils.hasText(this.operationTarget)) {
                predicates.add(criteriaBuilder.equal(root.get("operationTarget"), this.operationTarget));
            }
            if (null != successFlag) {
                predicates.add(criteriaBuilder.equal(root.get("successFlag"), successFlag));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
