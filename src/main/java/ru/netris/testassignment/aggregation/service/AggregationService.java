package ru.netris.testassignment.aggregation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.netris.testassignment.aggregation.client.dto.Camera;
import ru.netris.testassignment.aggregation.client.CamerasSourceClient;

@Service
public class AggregationService {
    private final CamerasSourceClient camerasSourceClient;

    @Autowired
    public AggregationService(CamerasSourceClient camerasSourceClient) {
        this.camerasSourceClient = camerasSourceClient;
    }

    public Flux<Camera> getAllCameras() {
        return camerasSourceClient.getAllCameras();
    }
}
