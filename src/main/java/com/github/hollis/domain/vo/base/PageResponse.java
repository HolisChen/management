package com.github.hollis.domain.vo.base;

import com.github.hollis.domain.dto.base.PageRequest;
import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Long total;
    private List<T> contents;


    public static <T> PageResponse<T> from(Long total, List<T> contents, PageRequest pageRequest) {
        PageResponse<T> response = new PageResponse<>();
        response.setTotal(total);
        response.setPageNo(pageRequest.getPageNo());
        response.setPageSize(pageRequest.getPageSize());
        response.setContents(contents);
        return response;
    }
}
