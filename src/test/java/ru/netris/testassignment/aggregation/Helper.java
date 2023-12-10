package ru.netris.testassignment.aggregation;

import ru.netris.testassignment.aggregation.client.dto.Camera;
import ru.netris.testassignment.aggregation.client.dto.Source;
import ru.netris.testassignment.aggregation.client.dto.Token;

public class Helper {
    public static Camera makeCamera() {
        return new Camera(
                1L,
                new Source(Source.Type.LIVE, "rtsp://127.0.0.1/1"),
                new Token("fa4b588e-249b-11e9-ab14-d663bd873d93", 120)
        );
    }
}
