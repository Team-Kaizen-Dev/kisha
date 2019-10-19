package com.teamkaizen.kisha.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DatalogEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public DatalogEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
