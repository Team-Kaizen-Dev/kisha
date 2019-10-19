package com.teamkaizen.kisha.event;

import com.teamkaizen.kisha.datalog.DataLogRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DatalogEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public DatalogEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishOnReportReceive(DataLogRequest request) {
        DatalogEvent event = new DatalogEvent(this, request);
        applicationEventPublisher.publishEvent(event);
    }
}
