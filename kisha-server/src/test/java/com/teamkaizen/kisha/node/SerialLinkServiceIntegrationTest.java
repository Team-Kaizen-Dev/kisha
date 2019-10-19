package com.teamkaizen.kisha.node;

import com.teamkaizen.kisha.KishaApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SerialLinkServiceIntegrationTest extends KishaApplicationTests {

    @Autowired
    private SerialLinkService serialLinkService;

    @Test
    public void test() {
        waitForSerialConnection();
        readSerialData();
    }

    private void readSerialData() {
        while (true) {
            String data = serialLinkService.readData(2_000);
            System.out.println(data);
        }
    }

    private void waitForSerialConnection() {
        while (!serialLinkService.isLinked()) {
            try {
                serialLinkService.linkUp();
            } catch (NodeException e) {
                e.printStackTrace();
            }
        }
    }
}
