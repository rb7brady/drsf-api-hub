package com.drsf.api.polygon.service;

import com.drsf.api.IProxy;
import com.dsrf.api.meta.HttpQueryMeta;
import com.dsrf.api.meta.QueryMeta;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PolygonProxy implements IProxy {


    @Override
    public Object query(QueryMeta queryMeta) {
        return null;
    }

    @Override
    public Mono<Object> queryAsMono(HttpQueryMeta queryMeta) {

        return buildWebClient((String)queryMeta.get("URL")).get()
                .uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray())
                .retrieve().bodyToMono(Object.class);
    }

    public Mono<LinkedHashMap> queryAsMono2(HttpQueryMeta queryMeta) {
        WebClient webclient = buildWebClient((String)queryMeta.get("URL"));

        //WebClient.ResponseSpec responseSpec = webclient.get().uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray()).accept(MediaType.APPLICATION_JSON).retrieve();
        return webclient.get().uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray()).accept(MediaType.APPLICATION_JSON).exchange().flatMap(
                clientResponse -> clientResponse.bodyToMono(LinkedHashMap.class).doOnSuccess(

                        body -> {
                            if (clientResponse.statusCode().isError()) {
                                System.out.println(body);
                            }

                        }));
        //return responseSpec.bodyToMono(LinkedHashMap.class);
//        return buildWebClient((String)queryMeta.get("URL")).get()
//                .uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray())
//                .retrieve().bodyToMono(Object.class);
    }
    public Mono<ResponseEntity<Object>> queryAsMonoEntity(HttpQueryMeta queryMeta) {
        return buildWebClient((String)queryMeta.get("URL")).get()
                .uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray())
                .retrieve().toEntity(Object.class);
    }
//    @Override
//    public Mono<Object> queryAsMono(HttpQueryMeta queryMeta, {t -> t}) {
//        return buildWebClient((String)queryMeta.get("URL")).get()
//                .uri(getFullParamaterizedURL(queryMeta),queryMeta.getAllParamValues().toArray())
//                .retrieve().bodyToMono(Object.class);
//    }


    @Override
    public Flux<?> queryAsFlux(QueryMeta queryMeta) {
        return null;
    }

    private static WebClient buildWebClient(String url) {
        return WebClient.builder().baseUrl(url).exchangeStrategies(ExchangeStrategies.builder().codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024)).build()).build();
    }

    private static String getFullParamaterizedURL(HttpQueryMeta queryMeta) {
        StringBuilder uriString = new StringBuilder();
        uriString
                .append(queryMeta.get("URI"))
                .append(((HashMap<String, Object>) queryMeta.get("requiredParams"))
                        .keySet()
                        .stream()
                        .map(k -> "{" + k + "}")
                        .collect(Collectors.joining("/")));

        if (!CollectionUtils.isEmpty((Map<String, Object>) queryMeta.get("optionalParams"))){
            uriString.append("?").append(
                    ((HashMap<String, Object>) queryMeta.get("optionalParams")).keySet().stream().map(
                            k -> k + "={" + k + "}"
                    ).collect(Collectors.joining("&")));
        }
        return uriString.toString();
    }

}
