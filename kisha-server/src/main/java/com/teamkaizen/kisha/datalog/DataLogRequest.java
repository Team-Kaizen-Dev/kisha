package com.teamkaizen.kisha.datalog;

import lombok.Data;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Data
public class DataLogRequest {
    private long userId;
    private int typeOfDisaster;
    private String message;
    private double lat;
    private double lng;
    private long timeLogged;
}
