package com.drsf.api.robinhood.service;

import com.drsf.api.robinhood.model.PaginatedRobinhoodHTTPResponse;
import com.dsrf.api.meta.HttpQueryMeta;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

@Repository
public class RobinhoodProxy<T> {


    public Mono<T> queryAsMono(HttpQueryMeta queryMeta) {
        // WebClient webclient = buildWebClient((String)queryMeta.get("URL"));

        //WebClient.ResponseSpec responseSpec = webclient.get().uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray()).accept(MediaType.APPLICATION_JSON).retrieve();
//        return webclient.get().uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray()).accept(MediaType.APPLICATION_JSON).exchange().flatMap(
//                clientResponse -> clientResponse.bodyToMono(LinkedHashMap.class).
        //return responseSpec.bodyToMono(LinkedHashMap.class);
        WebClient.RequestHeadersSpec<?> client;
        if (queryMeta.get("body") != null) {
            client = buildWebClient((String)queryMeta.get("URL"))
                    .post()
                    .uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray())
                    .bodyValue(queryMeta.get("body"));

        } else {
            if (queryMeta.get("URI") != null ) {
                client = buildWebClient((String) queryMeta.get("URL")).get().uri(getFullParamaterizedURL(queryMeta), queryMeta.getAllParamValues().toArray());
            } else {
                client = buildWebClient((String) queryMeta.get("URL")).get();

            }
        }


        if (queryMeta.get("headers") != null) {
            ((HashMap<String, Object>)queryMeta
                    .get("headers"))
                    .entrySet()
                    .stream()
                    .forEach(entry -> client.header(entry.getKey(), (String)entry.getValue()));
        }

        return client.retrieve().bodyToMono((Class)queryMeta.get("expectedClass"));
    }

    private static WebClient buildWebClient(String url) {
        return WebClient
                .builder()
                .baseUrl(url)
                .exchangeStrategies(
                        ExchangeStrategies
                                .builder()
                                .codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024))
                                .build())

                .build();
    }

    private static String getFullParamaterizedURL(HttpQueryMeta queryMeta) {
        StringBuilder uriString = new StringBuilder();
        uriString.append(queryMeta.get("URI"));
        if (!CollectionUtils.isEmpty((Map<String, Object>) queryMeta.get("requiredParams"))) {
            uriString.append(((HashMap<String, Object>) queryMeta.get("requiredParams"))
                    .keySet()
                    .stream()
                    .map(k -> "{" + k + "}")
                    .collect(Collectors.joining("/")));
        }

        if (!CollectionUtils.isEmpty((Map<String, Object>) queryMeta.get("optionalParams"))){
            uriString.append("?").append(
                    ((HashMap<String, Object>) queryMeta.get("optionalParams")).keySet().stream().map(
                            k -> k + "={" + k + "}"
                    ).collect(Collectors.joining("&")));
        }
        return uriString.toString();
    }
}
