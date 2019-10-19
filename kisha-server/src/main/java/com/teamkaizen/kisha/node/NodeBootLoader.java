package com.teamkaizen.kisha.node;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class NodeBootLoader {

    @Autowired
    private NodeService nodeService;
    @Autowired
    private SerialLinkService serialLinkService;
    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private Executor executor;
    @Value("${mcu.bootloader.run}")
    private boolean runOnStartup;

    @PostConstruct
    public void onReady() {
        if (runOnStartup) {
            executor.execute(() -> {
                try {
                    serialLinkService.linkUp();
                } catch (NodeException e) {
                    log.error("{0}", e);
                }
                while (true) {
                    String serialData = serialLinkService.readData(60_000);
                    nodeService.saveReport(serialData);
                }
            });

        }
    }
}
