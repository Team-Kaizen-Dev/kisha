package com.kaizen.team.kishaapp.datalog.service;


import com.kaizen.team.kishaapp.datalog.data.DataLog;

/**
 * Created by Jesli Albert Bautista on 10/20/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public interface DataLogService {
    String SAVE_LOG = "/api/dataLog/saveDataLog";

    DataLog saveDataLog(DataLog dataLog) throws Exception;
}
