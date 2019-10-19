package com.teamkaizen.kisha.node;

public class NodeException extends Exception {

    public static final String MCU_NOT_DETECTED = "Microcontroller not detected.";
    public static final String MCU_CONNECT_FAILURE = "Microcontroller connection failure.";
    public static final String MCU_DISCONNECT_FAILURE = "Microcontroller disconnection failure.";
    public static final String MCU_DATA_TRANSMIT_FAILURE = "Microcontroller data transmit failure.";

    public NodeException(String message) {
        super(message);
    }
}
