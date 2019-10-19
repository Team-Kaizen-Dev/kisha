package com.teamkaizen.kisha.node;

import jssc.SerialPort;

public interface SerialLinkService {

    boolean isLinked();
    void monitorConnectivity();
    String linkUp() throws NodeException;
    void linkDown() throws NodeException;
    String scanPorts();
    String readData(long time);
    void transmitData(String msg) throws NodeException;
    SerialPort getSerialPort();
}
