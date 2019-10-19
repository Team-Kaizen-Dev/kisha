package com.teamkaizen.kisha.node;

import jssc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
@Slf4j
class SerialLinkServiceImpl implements SerialLinkService, SerialPortEventListener {

    private static final int BAUD_RATE = 9600;
    private static final int DATA_BITS = 8;
    private static final int STOP_BITS = 1;
    private static final int PARITY = 0;

    private SerialPort serialPort;
    private boolean serialReadReady = false;
    private final StringBuilder data = new StringBuilder();
    private boolean linked = false;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private Executor executor;
    @Value("${mcu.connectivity.log.show}")
    private boolean showMicrocontrollerConnectivityLog;
    @Value("${receiver.port.prefix}")
    private String receiverPortPrefix;

    @Override
    public boolean isLinked() {
        return linked;
    }

    @Override
    public void monitorConnectivity() {
        executor.execute(() -> {
            while (true) {
                String port = scanPorts();
                if (port == null || port.isEmpty()) {
                    if (showMicrocontrollerConnectivityLog) {
                        log.error("Microcontroller disconnected.");
                    }
                    linked = false;
                } else {
                    if (showMicrocontrollerConnectivityLog) {
                        log.info("Microcontroller connected.");
                    }
                }
                sleep(3000);
            }
        });
    }

    @Override
    public String linkUp() throws NodeException {
        String port = scanPorts();

        if (port.isEmpty()) {
            sleep(3000);
            throw new NodeException(NodeException.MCU_NOT_DETECTED);
        }
        serialPort = new SerialPort(port);

        try {
            serialPort.openPort();

            if (serialPort.isOpened()) {
                serialPort.setParams(BAUD_RATE, DATA_BITS, STOP_BITS, PARITY);
                serialPort.addEventListener(this);
            }
            linked = true;
            return serialPort.getPortName();
        } catch (SerialPortException e) {
            log.error("{}", e);
            throw new NodeException(NodeException.MCU_CONNECT_FAILURE);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void linkDown() throws NodeException {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            log.error("{}", e);
            throw new NodeException(NodeException.MCU_DISCONNECT_FAILURE);
        }
    }

    @Override
    public String scanPorts() {
        String[] ports = SerialPortList.getPortNames();
        String port = "";

        if (ports != null) {
            for (String tempPort : ports) {
                if (tempPort != null && tempPort.startsWith(receiverPortPrefix)) {
                    port = tempPort;
                    break;
                }
            }
        }
        return port;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.isRXCHAR()) {
            analyzeReadings();
        }
    }

    @Override
    public synchronized String readData(long timeOut) {
        while (!serialReadReady || data.toString().equals("")) {
            try {
                wait(timeOut);
            } catch (InterruptedException e) {
                log.warn("{}", e);
            }

            break;
        }

        String temp = data.toString();
        data.setLength(0);
        serialReadReady = false;
        return temp;
    }

    @Override
    public void transmitData(String msg) throws NodeException {
        if (serialPort == null || !serialPort.isOpened()) {
            throw new NodeException(NodeException.MCU_CONNECT_FAILURE);
        }
        try {
            boolean transmissionWasOk = serialPort.writeString(msg);

            if (!transmissionWasOk) {
                throw new NodeException(NodeException.MCU_DATA_TRANSMIT_FAILURE);
            } else {
                sleep(500);
            }
        } catch (SerialPortException e) {
            log.error("{}", e);
            throw new NodeException(NodeException.MCU_DATA_TRANSMIT_FAILURE);
        }
    }

    @Override
    public SerialPort getSerialPort() {
        return serialPort;
    }

    private synchronized void analyzeReadings() {
        try {
            byte[] buffer = serialPort.readBytes();

            for (byte b : buffer) {
                if (b == '\r' || b == '\n') {
                    serialReadReady = true;
                    notifyAll();
                } else {
                    data.append((char) b);
                }
            }
        } catch (SerialPortException e) {
            log.error("Error analyzing readings: {}", e);
        }
    }
}