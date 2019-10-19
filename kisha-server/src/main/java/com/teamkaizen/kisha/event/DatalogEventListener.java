package com.teamkaizen.kisha.event;

import com.teamkaizen.kisha.datalog.DataLogService;
import org.springframework.context.ApplicationListener;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
public class DatalogEventListener implements ApplicationListener<DatalogEvent> {

    private final DataLogService dataLogService;

    public DatalogEventListener(DataLogService dataLogService) {
        this.dataLogService = dataLogService;
    }

    @Override
    public void onApplicationEvent(DatalogEvent datalogEvent) {
        System.out.println("Received spring custom event - " + datalogEvent.getDataLogRequest());
        dataLogService.saveDataLog(datalogEvent.getDataLogRequest());
    }
}
