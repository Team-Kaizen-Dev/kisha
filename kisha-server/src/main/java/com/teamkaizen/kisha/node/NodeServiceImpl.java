package com.teamkaizen.kisha.node;

import com.teamkaizen.kisha.datalog.DataLogRequest;
import com.teamkaizen.kisha.event.DatalogEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class NodeServiceImpl implements NodeService {

    @Autowired
    private DatalogEventPublisher datalogEventPublisher;

    @Override
    public void saveReport(String payload) {
        if (!payload.startsWith("DATALOG: ")) {
            return;
        }
        payload = payload.replace("DATALOG: ", "");
        String[] tokens = payload.split("-");
        if (tokens.length != 4) {
            throw new IllegalArgumentException("Invalid report tokens.");
        }

        long id = Long.valueOf(tokens[0]);
        int hazardType = Integer.valueOf(tokens[1]);
        String[] coordinateTokens = tokens[2].split(",");
        double latitude = Double.valueOf(coordinateTokens[0]);
        double longitude = Double.valueOf(coordinateTokens[1]);
        String message = tokens[3];

        DataLogRequest request = new DataLogRequest();
        request.setUserId(id);
        request.setTypeOfDisaster(hazardType);
        request.setLat(latitude);
        request.setLng(longitude);
        request.setMessage(message);
        datalogEventPublisher.publishOnReportReceive(request);
    }
}
