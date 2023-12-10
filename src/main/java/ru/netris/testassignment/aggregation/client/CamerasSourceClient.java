package ru.netris.testassignment.aggregation.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.netris.testassignment.aggregation.client.dto.Camera;
import ru.netris.testassignment.aggregation.client.dto.Source;
import ru.netris.testassignment.aggregation.client.dto.Token;

@Component
public class CamerasSourceClient {

    private static final String CAMERAS_URL = "https://run.mocky.io/v3/bc34ce01-90c6-4266-93f1-07591afad12e";

    private final WebClient client;

    record RawCamera(Long id, String sourceDataUrl, String tokenDataUrl) {
    }

    public CamerasSourceClient(WebClient.Builder builder) {
        this.client = builder.filter(new WebClientLoggingFilter()).build();
    }

    public Flux<Camera> getAllCameras() {
        return client
                .get()
                .uri(CAMERAS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RawCamera.class)
                .map(this::mapRawCameraToCameraMono)
                .flatMapSequential(mono -> mono) // Convert Flux<Mono<Camera>> to Flux<Camera>
                ;
    }

    private Mono<Camera> mapRawCameraToCameraMono(RawCamera rawCamera) {
        Mono<Source> sourceMono = client
                .get()
                .uri(rawCamera.sourceDataUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Source.class)
                ;

        Mono<Token> tokenMono = client
                .get()
                .uri(rawCamera.tokenDataUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Token.class)
                ;

        return Mono
                .zip(sourceMono, tokenMono)
                .map(tuple -> new Camera(rawCamera.id(), tuple.getT1(), tuple.getT2()))
                ;
    }
}
