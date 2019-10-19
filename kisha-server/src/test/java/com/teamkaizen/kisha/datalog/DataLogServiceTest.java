package com.teamkaizen.kisha.datalog;

import com.teamkaizen.kisha.KishaApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
class DataLogServiceTest extends KishaApplicationTests {

    @Autowired
    private DataLogService dataLogService;

    @Test
    public void saveDataLogTest() {
        final DataLog dataLog = new DataLog();
        dataLog.setContactNumber("1");
        dataLog.setUserId(1);
        dataLog.setFullName("Nikko Norris");
        dataLog.setLat(121.11111);
        dataLog.setLng(79.33333);
        dataLog.setTypeOfHazard("file");
        dataLog.setMessage("hello");
        final DataLog savedDataLog = dataLogService.saveDataLog(dataLog);
        assertNotNull(savedDataLog);
    }

    @Test
    public void findAllTest() {
        final DataLog dataLog = new DataLog();
        dataLog.setContactNumber("1");
        dataLog.setUserId(1);
        dataLog.setFullName("Nikko Norris");
        dataLog.setLat(121.11111);
        dataLog.setLng(79.33333);
        dataLog.setTypeOfHazard("file");
        dataLog.setMessage("hello");
        dataLogService.saveDataLog(dataLog);
        final List<DataLog> all = dataLogService.findAll();
        assertFalse(all.isEmpty());
    }
}