package com.github.hollis.controller;

import com.github.hollis.dao.entity.LogEntity;
import com.github.hollis.domain.dto.log.LogQueryDto;
import com.github.hollis.domain.vo.base.PageResponse;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.domain.vo.log.LogVo;
import com.github.hollis.mapper.LogMapper;
import com.github.hollis.service.base.LogService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/log")
@Api(tags = {"日志查询"})
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;
    private final LogMapper logMapper;
    @PostMapping("/page")
    public Result<PageResponse<LogVo>> pageQuery(@RequestBody LogQueryDto dto) {
        Page<LogEntity> page = logService.pageQuery(dto);
        List<LogVo> logVos = logMapper.entityToVo(page.getContent());
        return Result.success(PageResponse.from(page.getTotalElements(), logVos, dto));

    }
}
