package com.teamkaizen.kisha.api;

import com.teamkaizen.kisha.datalog.DataLog;
import com.teamkaizen.kisha.datalog.DataLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@RestController
@RequestMapping("/api/dataLog")
public class DatalogResource {
    private final DataLogService dataLogService;

    public DatalogResource(DataLogService dataLogService) {
        this.dataLogService = dataLogService;
    }

    @GetMapping("/list")
    public List<DataLog> findAll() {
        return dataLogService.findAll();
    }
}
