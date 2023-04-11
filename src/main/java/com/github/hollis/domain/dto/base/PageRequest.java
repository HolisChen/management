package com.github.hollis.domain.dto.base;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PageRequest {
    private Integer pageNo = 1;
    private Integer pageSize = 20;
    private List<SortField> sort;
    public Pageable toPageable() {
        if (CollectionUtils.isEmpty(sort)) {
            return org.springframework.data.domain.PageRequest.of(Math.max(0, this.getPageNo() - 1), pageSize);
        }
        List<Sort.Order> orderList = sort.stream()
                .map(item -> {
                    if ("asc".equalsIgnoreCase(item.getDirection())) {
                        return Sort.Order.asc(item.getField());
                    }
                    return Sort.Order.desc(item.getField());
                }).collect(Collectors.toList());
        return org.springframework.data.domain.PageRequest.of(Math.max(0, this.getPageNo() - 1), pageSize, Sort.by(orderList));
    }

    @Data
    public static class SortField {
        private String field;
        private String direction;
    }
}
