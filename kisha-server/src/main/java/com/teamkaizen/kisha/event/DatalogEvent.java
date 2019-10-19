package com.teamkaizen.kisha.event;

import com.teamkaizen.kisha.datalog.DataLogRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Getter
public class DatalogEvent extends ApplicationEvent {

private DataLogRequest dataLogRequest;

    public DatalogEvent(Object source, DataLogRequest dataLogRequest ) {
        super(source);
        this.dataLogRequest = dataLogRequest;
    }
}
