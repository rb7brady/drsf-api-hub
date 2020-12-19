package com.drsf.api.robinhood.service;

import com.drsf.api.robinhood.model.PaginatedRobinhoodHTTPResponse;
import com.dsrf.api.meta.HttpQueryMeta;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class RobinhoodProxy<T> {

    @Async
    public Mono<PaginatedRobinhoodHTTPResponse<T>> queryAsMono(HttpQueryMeta queryMeta, ParameterizedTypeReference<PaginatedRobinhoodHTTPResponse<T>> typeReference) {
        WebClient client = buildWebClient((String)queryMeta.get("URL"));
        WebClient.RequestHeadersUriSpec<?> webRequest = queryMeta.hasBody()?client.post():client.get();


        if (queryMeta.hasHeaders()) {
            ((HashMap<String, Object>)queryMeta.get("headers"))
                    .entrySet()
                    .stream()
                    .forEach(entry -> webRequest.header(entry.getKey(), (String)entry.getValue()));
        }

        if (queryMeta.hasURI()) {
            webRequest.uri(getFullParamaterizedURL(queryMeta), queryMeta.getAllParamValues().toArray());
        }

        if (webRequest instanceof WebClient.RequestBodyUriSpec && queryMeta.hasBody()) {
            ((WebClient.RequestBodyUriSpec)webRequest).bodyValue(queryMeta.get("body"));
        }

        return webRequest.retrieve().bodyToMono(typeReference);

    }

    public Mono<?> queryAsMono(HttpQueryMeta queryMeta, Class expectedClass) {
        WebClient client = buildWebClient((String)queryMeta.get("URL"));
        WebClient.RequestHeadersUriSpec<?> webRequest = queryMeta.hasBody()?client.post():client.get();


        if (queryMeta.hasHeaders()) {
            ((HashMap<String, Object>)queryMeta.get("headers"))
                    .entrySet()
                    .stream()
                    .forEach(entry -> webRequest.header(entry.getKey(), (String)entry.getValue()));
        }

        if (queryMeta.hasURI()) {
            webRequest.uri(getFullParamaterizedURL(queryMeta), queryMeta.getAllParamValues().toArray());
        }

        if (webRequest instanceof WebClient.RequestBodyUriSpec && queryMeta.hasBody()) {
            ((WebClient.RequestBodyUriSpec)webRequest).bodyValue(queryMeta.get("body"));
        }

        return webRequest.retrieve().bodyToMono(expectedClass);

    }

    public Flux<T> queryAsFlux(HttpQueryMeta queryMeta, Class expectedClass) {
        WebClient client = buildWebClient((String)queryMeta.get("URL"));
        WebClient.RequestHeadersUriSpec<?> webRequest = queryMeta.hasBody()?client.post():client.get();


        if (queryMeta.hasHeaders()) {
            ((HashMap<String, Object>)queryMeta.get("headers"))
                    .entrySet()
                    .stream()
                    .forEach(entry -> webRequest.header(entry.getKey(), (String)entry.getValue()));
        }

        if (queryMeta.hasURI()) {
            webRequest.uri(getFullParamaterizedURL(queryMeta), queryMeta.getAllParamValues().toArray());
        }

        if (webRequest instanceof WebClient.RequestBodyUriSpec && queryMeta.hasBody()) {
            ((WebClient.RequestBodyUriSpec)webRequest).bodyValue(queryMeta.get("body"));
        }

        return webRequest.retrieve().bodyToFlux(expectedClass);

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
        if (queryMeta.hasRequiredParams()) {
            uriString
                    .append(((HashMap<String, Object>) queryMeta.get("requiredParams")).keySet().stream().map(k -> "{" + k + "}").collect(Collectors.joining("/")));
        }

        if (queryMeta.hasOptionalParams()){
            uriString
                    .append("?")
                    .append(((HashMap<String, Object>) queryMeta.get("optionalParams")).keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.joining("&")));
        }
        return uriString.toString();
    }
}
