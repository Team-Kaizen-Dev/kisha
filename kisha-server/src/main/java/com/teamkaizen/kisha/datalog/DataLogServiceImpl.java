package com.teamkaizen.kisha.datalog;

import com.teamkaizen.kisha.user.User;
import com.teamkaizen.kisha.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Service
@RequiredArgsConstructor
public class DataLogServiceImpl implements DataLogService {

    private final DataLogRepository dataLogRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

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

        dataLog = dataLogRepository.save(dataLog);
        simpMessagingTemplate.convertAndSend("/topic/datalog", dataLog);
        return dataLog;
    }

    @Override
    public List<DataLog> findAll() {
        return dataLogRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
    }
}
