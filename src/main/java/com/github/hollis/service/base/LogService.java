package com.github.hollis.service.base;

import com.github.hollis.dao.entity.LogEntity;
import com.github.hollis.dao.repository.LogRepository;
import com.github.hollis.domain.dto.log.LogQueryDto;
import com.github.hollis.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService extends CRUDService<LogEntity,Integer,LogRepository> {
    private final LogRepository logRepository;

    @Async
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveLog(LogEntity entity) {
        super.save(entity);
    }

    public Page<LogEntity> pageQuery(LogQueryDto queryDto) {
        return logRepository.findAll(queryDto.toSpecification(), queryDto.toPageable());

    }

    @Override
    protected LogRepository getDao() {
        return logRepository;
    }
}
