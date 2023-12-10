package ru.netris.testassignment.aggregation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import ru.netris.testassignment.aggregation.service.AggregationService;
import ru.netris.testassignment.aggregation.client.dto.Camera;

@Controller
public class AggregationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationController.class);

    private final AggregationService aggregationService;

    @Autowired
    public AggregationController(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @GetMapping("/cameras")
    public Flux<Camera> getAllCameras() {
        return aggregationService.getAllCameras()
                .doOnError(e -> LOGGER.error("Impossible to get cameras", e))
                .onErrorResume(e -> Flux.empty());
    }
}
