package com.teamkaizen.kisha.datalog;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Service
public class DataLogServiceImpl implements DataLogService {

    private final DataLogRepository dataLogRepository;

    public DataLogServiceImpl(DataLogRepository dataLogRepository) {
        this.dataLogRepository = dataLogRepository;
    }

    @Override
    public DataLog saveDataLog(DataLog dataLog) {
        return dataLogRepository.save(dataLog);
    }

    @Override
    public List<DataLog> findAll() {
        return dataLogRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
    }
}
