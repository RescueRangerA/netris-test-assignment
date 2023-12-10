package ru.netris.testassignment.aggregation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import ru.netris.testassignment.aggregation.service.AggregationService;
import ru.netris.testassignment.aggregation.client.dto.Camera;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.netris.testassignment.aggregation.Helper.makeCamera;

@WebMvcTest(AggregationController.class)
@AutoConfigureMockMvc
public class AggregationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AggregationService aggregationService;

    @Test
    public void whenGetAllCameras_thenStatus200() throws Exception {
        Camera camera = makeCamera();
        when(aggregationService.getAllCameras()).thenReturn(Flux.just(camera));

        mockMvc.perform(get("/cameras"))
                .andExpect(status().isOk());
    }
}