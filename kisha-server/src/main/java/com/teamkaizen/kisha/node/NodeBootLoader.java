package com.teamkaizen.kisha.node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NodeBootLoader {

    @Autowired
    private NodeService nodeService;
    @Autowired
    private SerialLinkService serialLinkService;
    @Value("${mcu.bootloader.run}")
    private boolean runOnStartup;

    @PostConstruct
    public void onReady() throws NodeException {
        if (runOnStartup) {
            serialLinkService.linkUp();
            while (true) {
                String serialData = serialLinkService.readData(60_000);
                nodeService.saveReport(serialData);
            }
        }
    }
}
