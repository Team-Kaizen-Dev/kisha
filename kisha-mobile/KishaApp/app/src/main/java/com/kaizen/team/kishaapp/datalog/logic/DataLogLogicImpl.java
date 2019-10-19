package com.kaizen.team.kishaapp.datalog.logic;


import com.kaizen.team.kishaapp.datalog.data.DataLog;
import com.kaizen.team.kishaapp.datalog.service.DataLogService;
import com.kaizen.team.kishaapp.datalog.service.DataLogServiceImpl;
import com.satellite.retrofit.retrofit.ServiceHandler;

/**
 * Created by Jesli Albert Bautista on 10/20/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class DataLogLogicImpl implements DataLogLogic {
    private static DataLogLogic INSTANCE;


    private DataLogService service;

    private DataLogLogicImpl(ServiceHandler hander) {
        this.service = new DataLogServiceImpl(hander);
    }

    public static DataLogLogic getInstance(ServiceHandler handler){
        if(INSTANCE == null){
            INSTANCE = new DataLogLogicImpl(handler);
        }
        return INSTANCE;
    }

    @Override
    public void saveLog(DataLog log) throws Exception {
        service.saveDataLog(log);
    }
}
