package com.drsf.api.repositories;

import com.drsf.api.entities.LinkedAccount;
import com.drsf.api.postgres.repositories.AccountOrderRepository;
import com.drsf.api.robinhood.Endpoint;
import com.drsf.api.robinhood.RobinhoodResponsePropertyURL;
import com.drsf.api.robinhood.model.Order;
import com.drsf.api.robinhood.model.PaginatedRobinhoodHTTPResponse;
import com.drsf.api.robinhood.service.RobinhoodPaginatedProxy;
import com.dsrf.api.meta.HttpQueryMeta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class OrdersRepository extends PaginatedRobinhoodRepository<PaginatedRobinhoodHTTPResponse<Object>, Object> {
    ParameterizedTypeReference<PaginatedRobinhoodHTTPResponse<Object>> typeReference = new ParameterizedTypeReference<PaginatedRobinhoodHTTPResponse<Object>>(){};


    @Autowired
    AccountOrderRepository accountOrderRepository;

    @Autowired
    RobinhoodPaginatedProxy robinhoodPaginatedProxy;

    LinkedAccount linkedAccount;

    public Flux<LinkedHashMap<String, Object>> getRawFlux(HttpQueryMeta query) {
        return fetchPage(query).expand(
                response -> {
                    if (StringUtils.isEmpty(response.getNext())) {
                        return Mono.empty();
                    }
                    return fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
                }).flatMap(response -> Flux.fromIterable((List<LinkedHashMap<String, Object>>)response.getResults()));
    }

     public Mono<PaginatedRobinhoodHTTPResponse> fetchPage(HttpQueryMeta query) {
         return robinhoodProxy.queryAsMono(query, typeReference);
     }


    public Flux<Order> findAllWithInstruments(String username, HttpQueryMeta query) {
        linkedAccount = linkedAccountRepository.findByUsernameAndType(username, "ROBINHOOD");

        query
                .putBaseEndpoint(Endpoint.getBaseUrl())
                .putParamaterizedURI(Endpoint.ORDERS.getURI())
                .putHeader("Authorization", "bearer " + linkedAccount.getBearerToken());

        return getRawFlux(query).flatMap(r -> {
            Order o = new ObjectMapper().convertValue(r, Order.class);
            return robinhoodPaginatedProxy.queryAsMono(new HttpQueryMeta().putBaseEndpoint(o.getInstrument().toString()))
                    .flatMap(instrument -> {
                                Arrays.stream(o.getClass().getDeclaredFields()).filter(
                                        f -> (f.isAnnotationPresent(RobinhoodResponsePropertyURL.class) && !f.getAnnotation(RobinhoodResponsePropertyURL.class).requiresAuthentication())
                                ).forEach(
                                        f -> {
                                            try {
                                                Class<?> clazz = Class.forName(o.getClass().getCanonicalName());
                                                Field field = clazz.getDeclaredField(f.getName());
                                                field.setAccessible(true);
                                                field.set(o, ((LinkedHashMap<String, String>) instrument).get("symbol"));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                );
                                return Mono.just(o);
                            }
                    );
        });
    }

    public Flux<Field> findURLFields(Class responseClass) {
        return Flux.fromStream(Arrays
                .stream(responseClass.getDeclaredFields())
                .filter(f ->
                        (
                                f.isAnnotationPresent(RobinhoodResponsePropertyURL.class) &&
                                !f.getAnnotation(RobinhoodResponsePropertyURL.class).requiresAuthentication()
                        )
                )
        );
    }

    @Override
    List<Object> findAll(String username, HttpQueryMeta query) {
        return (List<Object>)findAllWithInstruments(username,query);
    }

}
