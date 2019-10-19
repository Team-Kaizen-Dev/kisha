package com.kaizen.team.kishaapp.datalog.logic;


import com.kaizen.team.kishaapp.datalog.data.DataLog;

/**
 * Created by Jesli Albert Bautista on 10/20/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public interface DataLogLogic {
    void saveLog(DataLog log) throws Exception;

}
