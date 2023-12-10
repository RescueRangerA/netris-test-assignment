package ru.netris.testassignment.aggregation.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import ru.netris.testassignment.aggregation.client.CamerasSourceClient;
import ru.netris.testassignment.aggregation.client.dto.Camera;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static ru.netris.testassignment.aggregation.Helper.makeCamera;

public class AggregationServiceUnitTest {

    @Test
    void testGetAllCameras() {
        CamerasSourceClient mockClient = Mockito.mock(CamerasSourceClient.class);
        AggregationService service = new AggregationService(mockClient);
        Camera expectedCamera = makeCamera();
        when(mockClient.getAllCameras()).thenReturn(Flux.just(expectedCamera));

        Flux<Camera> result = service.getAllCameras();

        assertTrue(
                Objects.requireNonNull(result.collectList().block()).contains(expectedCamera),
                "Test if returned flux contains expectedCamera"
        );
    }
}