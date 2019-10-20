package com.teamkaizen.kisha.api;

import com.teamkaizen.kisha.datalog.DataLog;
import com.teamkaizen.kisha.datalog.DataLogRequest;
import com.teamkaizen.kisha.datalog.DataLogService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@RestController
@RequestMapping("/api/dataLog")
public class DatalogResource {

    private final DataLogService dataLogService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public DatalogResource(DataLogService dataLogService, SimpMessagingTemplate simpMessagingTemplate) {
        this.dataLogService = dataLogService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("/saveDataLog")
    public DataLog save(@RequestBody DataLogRequest dataLogRequest) {
        return dataLogService.saveDataLog(dataLogRequest);
    }

    @GetMapping("/list")
    public List<DataLog> findAll() {
        return dataLogService.findAll();
    }

    @GetMapping("/test")
    public void test() throws Exception {
        final DataLog dataLog = new DataLog();
        dataLog.setUserId(1);
        dataLog.setAddress("Jaro");
        dataLog.setFullName("Nikko");
        dataLog.setLat(121.11111);
        dataLog.setLng(79.33333);
        dataLog.setTypeOfDisaster(0);
        Thread.sleep(1000); // simulated delay
        simpMessagingTemplate.convertAndSend("/topic/datalog", dataLog);
    }
}
