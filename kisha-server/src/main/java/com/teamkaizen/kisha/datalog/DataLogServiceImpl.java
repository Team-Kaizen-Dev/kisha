package com.teamkaizen.kisha.datalog;

import com.teamkaizen.kisha.user.User;
import com.teamkaizen.kisha.user.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Service
public class DataLogServiceImpl implements DataLogService {

    private final DataLogRepository dataLogRepository;
    private final UserRepository userRepository;

    public DataLogServiceImpl(DataLogRepository dataLogRepository, UserRepository userRepository) {
        this.dataLogRepository = dataLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DataLog saveDataLog(DataLogRequest dataLogRequest) {
        DataLog dataLog = new DataLog();

        dataLog.setMessage(dataLogRequest.getMessage());
        dataLog.setLat(dataLogRequest.getLat());
        dataLog.setLng(dataLogRequest.getLng());
        dataLog.setTypeOfDisaster(dataLogRequest.getTypeOfDisaster());
        dataLog.setTimeLogged(dataLogRequest.getTimeLogged());

        final User user = userRepository.findById(dataLogRequest.getUserId()).orElseThrow(() -> new IllegalArgumentException("User does not exists"));
        dataLog.setUserId(user.getId());
        dataLog.setFullName(user.getFullName());
        dataLog.setContactNumber(user.getContactNumber());
        dataLog.setAddress(user.getAddress());

        return dataLogRepository.save(dataLog);
    }

    @Override
    public List<DataLog> findAll() {
        return dataLogRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
    }
}
