package com.teamkaizen.kisha.datalog;

import com.teamkaizen.kisha.KishaApplicationTests;
import com.teamkaizen.kisha.user.User;
import com.teamkaizen.kisha.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
class DataLogServiceTest extends KishaApplicationTests {

    @Autowired
    private DataLogService dataLogService;
    @Autowired
    private UserService userService;

    @Test
    public void saveDataLogTest() {
        final DataLogRequest dataLog = new DataLogRequest();
        dataLog.setUserId(1);
        dataLog.setLat(121.11111);
        dataLog.setLng(79.33333);
        dataLog.setTypeOfDisaster(0);
        final DataLog savedDataLog = dataLogService.saveDataLog(dataLog);
        assertNotNull(savedDataLog);
    }

    @Test
    public void findAllTest() {
        final User user = new User();
        user.setPassword(UUID.randomUUID().toString());
        final User register = userService.register(user);
        final DataLogRequest dataLog = new DataLogRequest();
        dataLog.setUserId(register.getId());
        dataLog.setLat(121.11111);
        dataLog.setLng(79.33333);
        dataLog.setTypeOfDisaster(0);
        dataLogService.saveDataLog(dataLog);
        final List<DataLog> all = dataLogService.findAll();
        assertFalse(all.isEmpty());
    }
}