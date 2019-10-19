package com.teamkaizen.kisha.node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NodeBootLoader {

    @Autowired
    private NodeService nodeService;

    @PostConstruct
    public void onReady() {
//        nodeService.saveReport("2-1-10.3458293,122.16342-test_message");
    }
}
