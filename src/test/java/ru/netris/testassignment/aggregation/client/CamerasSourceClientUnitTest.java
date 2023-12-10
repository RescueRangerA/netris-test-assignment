package ru.netris.testassignment.aggregation.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.netris.testassignment.aggregation.client.dto.Camera;
import ru.netris.testassignment.aggregation.client.dto.Source;
import ru.netris.testassignment.aggregation.client.dto.Token;

import static org.mockito.Mockito.*;
import static ru.netris.testassignment.aggregation.Helper.makeCamera;

public class CamerasSourceClientUnitTest {

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void testGetAllCameras() {
        WebClient webClient = mock(WebClient.class);
        WebClient.Builder builder = mock(WebClient.Builder.class);
        RequestHeadersUriSpec uriSpec = mock(RequestHeadersUriSpec.class);
        RequestHeadersSpec headersSpec = mock(RequestHeadersSpec.class);

        ResponseSpec responseSpec = mock(ResponseSpec.class);

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(Mockito.anyString())).thenReturn(headersSpec);
        when(headersSpec.accept(Mockito.any(MediaType.class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(builder.filter(any())).thenReturn(builder);
        when(builder.build()).thenReturn(webClient);

        Camera camera = makeCamera();
        Long cameraId = camera.id();
        String sourceDataUrl = "http://test.source";
        String tokenDataUrl = "http://test.token";

        Mono<Source> sourceMono = Mono.just(camera.source());
        Mono<Token> tokenMono = Mono.just(camera.token());

        when(responseSpec.bodyToFlux(CamerasSourceClient.RawCamera.class))
                .thenReturn(Flux.just(new CamerasSourceClient.RawCamera(cameraId, sourceDataUrl, tokenDataUrl)));
        when(responseSpec.bodyToMono(Source.class)).thenReturn(sourceMono);
        when(responseSpec.bodyToMono(Token.class)).thenReturn(tokenMono);

        CamerasSourceClient camerasSourceClient = new CamerasSourceClient(builder);

        Flux<Camera> cameraFlux = camerasSourceClient.getAllCameras();

        StepVerifier.create(cameraFlux)
                .expectNextMatches(fetchedCamera -> fetchedCamera.equals(camera))
                .verifyComplete();
    }
}