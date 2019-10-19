package com.teamkaizen.kisha.node;

import com.teamkaizen.kisha.KishaApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeServiceIntegrationTest extends KishaApplicationTests {

    @Autowired
    private NodeService nodeService;

    @Test
    public void saveReportTest() {
        String message = "DATALOG: 1-2-10.3458293,122.16342-test_message";
        nodeService.saveReport(message);
    }
}
