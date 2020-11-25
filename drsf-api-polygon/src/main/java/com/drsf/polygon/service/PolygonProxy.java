package com.drsf.polygon.service;

import com.drsf.polygon.config.PolygonEndpoint;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PolygonProxy {
    private static final String BASE_URL = "https://api.polygon.io/";

    private static WebClient buildWebClient() {
        return WebClient.builder().baseUrl(BASE_URL).exchangeStrategies(ExchangeStrategies.builder().codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024)).build()).build();
    }

    public static  Mono<Object> getMonoObject(PolygonEndpoint URI, String ... params) {
        return buildWebClient().get().uri(URI.getURI(), params).retrieve().bodyToMono(Object.class);
    }

}
