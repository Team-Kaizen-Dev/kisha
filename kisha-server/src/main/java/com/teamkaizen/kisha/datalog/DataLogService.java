package com.teamkaizen.kisha.datalog;

import java.util.List;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
public interface DataLogService {
    DataLog saveDataLog(DataLogRequest dataLogRequest);
    List<DataLog> findAll();
}
