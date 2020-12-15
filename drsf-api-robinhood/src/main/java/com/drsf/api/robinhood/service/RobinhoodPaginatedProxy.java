package com.drsf.api.robinhood.service;

import com.drsf.api.robinhood.model.Order;
import com.drsf.api.robinhood.model.PaginatedRobinhoodHTTPResponse;
import com.dsrf.api.meta.HttpQueryMeta;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.stream.Collectors;

@Component
public class RobinhoodPaginatedProxy {

    public Flux<PaginatedRobinhoodHTTPResponse> queryOrderAsFlux(HttpQueryMeta queryMeta) {
        return initializeWebRequest(queryMeta).retrieve().bodyToFlux(PaginatedRobinhoodHTTPResponse.class);
    }
    public Mono<Order> queryOrderAsMono(HttpQueryMeta queryMeta) {
        return initializeWebRequest(queryMeta).retrieve().bodyToMono(Order.class);
    }

    public Mono<Object> queryAsMono(HttpQueryMeta queryMeta) {
             return initializeWebRequest(queryMeta).retrieve().bodyToMono(Object.class);
    }

    public Flux<PaginatedRobinhoodHTTPResponse> queryAsFlux(HttpQueryMeta queryMeta) {
        return initializeWebRequest(queryMeta).retrieve().bodyToFlux(PaginatedRobinhoodHTTPResponse.class);
    }

    private static WebClient.RequestHeadersUriSpec<?> initializeWebRequest(HttpQueryMeta query) {
        WebClient client = buildWebClient((String)query.get("URL"));
        WebClient.RequestHeadersUriSpec<?> webRequest = query.hasBody()?client.post():client.get();
        if (query.hasHeaders()) {
            ((HashMap<String, Object>)query.get("headers"))
                    .entrySet()
                    .stream()
                    .forEach(entry -> webRequest.header(entry.getKey(), (String)entry.getValue()));
        }

        if (query.hasURI()) {
            webRequest.uri(getFullParamaterizedURL(query), query.getAllParamValues().toArray());
        }

        if (webRequest instanceof WebClient.RequestBodyUriSpec && query.hasBody()) {
            ((WebClient.RequestBodyUriSpec)query).bodyValue(query.get("body"));
        }
        return webRequest;
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

