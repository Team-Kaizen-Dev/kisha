package com.kaizen.team.kishaapp.datalog.service;


import com.kaizen.team.kishaapp.datalog.data.DataLog;
import com.satellite.retrofit.retrofit.IServiceHandler;

/**
 * Created by Jesli Albert Bautista on 10/20/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class DataLogServiceImpl implements DataLogService {
    private IServiceHandler handler;

    public DataLogServiceImpl(IServiceHandler handler) {
        this.handler = handler;
    }

    @Override
    public DataLog saveDataLog(DataLog dataLog) throws Exception {
        return (DataLog) handler.callForSyncPostObjectBody(SAVE_LOG, dataLog, "", DataLog.class);
    }
}
