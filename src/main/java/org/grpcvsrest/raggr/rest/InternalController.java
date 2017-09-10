package org.grpcvsrest.raggr.rest;

import org.grpcvsrest.raggr.datapump.DataPump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InternalController {

    private final List<DataPump> dataPumps;

    @Autowired
    public InternalController(List<DataPump> dataPumps) {
        this.dataPumps = dataPumps;
    }


    @PostMapping("/pump")
    public void pump() {
        for(DataPump dp : dataPumps) {
            dp.fetchAndSave();
        }
    }
}
