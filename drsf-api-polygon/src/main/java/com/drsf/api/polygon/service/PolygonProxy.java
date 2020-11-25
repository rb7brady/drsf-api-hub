package com.drsf.api.polygon.service;

import com.dsrf.api.meta.HttpQueryMeta;
import com.dsrf.api.meta.QueryMeta;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PolygonProxy {

//    private static final String BASE_URL = "https://api.polygon.io/";
//
    private static WebClient buildWebClient(String url) {
        return WebClient.builder().baseUrl(url).exchangeStrategies(ExchangeStrategies.builder().codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024)).build()).build();
    }
//
    public static Mono<Object> getMonoObject(HttpQueryMeta queryMeta) {
        return buildWebClient((String)queryMeta.get("URL")).get()
                .uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray())
                .retrieve().bodyToMono(Object.class);
    }


    public static String getFullParamaterizedURL(HttpQueryMeta queryMeta) {
        StringBuilder uriString = new StringBuilder();
        uriString.append(queryMeta.get("URI"));

        if (!CollectionUtils.isEmpty((Map<String, Object>) queryMeta.get("optionalParams"))){
            uriString.append("?").append(
                ((HashMap<String, Object>) queryMeta.get("optionalParams")).keySet().stream().map(
                        k -> k + "={" + k + "}"
                ).collect(Collectors.joining("&")));
        }
        return uriString.toString();
    }

//    public Mono query(HttpQueryMeta queryMeta) {
//
//         getMonoObject(queryMeta);
//    }

}
