package com.drsf.api.repositories;

import com.drsf.api.entities.AccountOrder;
import com.drsf.api.entities.AccountOrderBuilder;
import com.drsf.api.entities.LinkedAccount;
import com.drsf.api.postgres.repositories.AccountOrderRepository;
import com.drsf.api.robinhood.Endpoint;
import com.drsf.api.robinhood.RobinhoodResponsePropertyURL;
import com.drsf.api.robinhood.model.Order;
import com.drsf.api.robinhood.model.OrderBuilder;
import com.drsf.api.robinhood.model.PaginatedRobinhoodHTTPResponse;
import com.drsf.api.robinhood.serializers.AccountOrderSerializer;
import com.drsf.api.robinhood.service.RobinhoodPaginatedProxy;
import com.dsrf.api.meta.HttpQueryMeta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OrdersRepository extends PaginatedRobinhoodRepository<PaginatedRobinhoodHTTPResponse<Object>, Object> {
    ParameterizedTypeReference<PaginatedRobinhoodHTTPResponse<Object>> typeReference = new ParameterizedTypeReference<PaginatedRobinhoodHTTPResponse<Object>>(){};
    ParameterizedTypeReference<PaginatedRobinhoodHTTPResponse<Order>> orderTypeRef = new ParameterizedTypeReference<PaginatedRobinhoodHTTPResponse<Order>>(){};


    HashMap<String, Object> robinhoodInstrumentCache = new HashMap<>();

    @Autowired
    AccountOrderRepository accountOrderRepository;

    @Autowired
    RobinhoodPaginatedProxy robinhoodPaginatedProxy;


    LinkedAccount linkedAccount;




    public Flux<AccountOrder> findAccountOrders(String username, HttpQueryMeta query) {
        linkedAccount = linkedAccountRepository.findByUsernameAndType(username, "ROBINHOOD");

        query
                .putBaseEndpoint(Endpoint.getBaseUrl())
                .putParamaterizedURI(Endpoint.ORDERS.getURI())
                .putHeader("Authorization", "bearer " + linkedAccount.getBearerToken());

        Flux<AccountOrder> orderHose = getRawFlux(query).flatMap(
                order -> {
                            Order newOrder = new ObjectMapper().convertValue(order, Order.class);
                            return robinhoodPaginatedProxy.queryOrderAsMono(query.clearAll().putBaseEndpoint(newOrder.getInstrument().toString()))
                                .flatMap(instrument -> Mono.just(
                                        new AccountOrderBuilder().createAccountOrder()
                                    )
                                );
                        }
        );

//        Flux<Order> orderHose = getRawFlux(query).flatMap(
//                order -> robinhoodPaginatedProxy.queryAsMono(query.clearAll().putBaseEndpoint(order.get("instrument").toString()))
//                        .flatMap(instrument -> Mono.just(
//                                new OrderBuilder()
//                                        .setInstrument(((LinkedHashMap<String, String>)instrument).get("symbol"))
//                                        .setType(order.get("type"))
//                                        .setSide(order.get("side"))
//                                        .setPrice(order.get("price"))
//                                        .setQuantity(order.get("quantity"))
//                                        .setCreatedAt(order.get("createdAt"))
//                                        .createOrder())
//                        )
//        );

//        System.out.println("DEBUG");
        return orderHose;


    }

    public Flux<Order> getSomethingFlux(HttpQueryMeta query) {
        return fetchPage(query).expand(
                response -> {
                    if (StringUtils.isEmpty(response.getNext())) {
                        return Mono.empty();
                    }
                    return robinhoodPaginatedProxy.queryAsFlux(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
                }).flatMap(response -> Flux.fromIterable(response.getResults()));
    }


    public Flux<AccountOrder> findEnrichedOrders(String username, HttpQueryMeta query) {
        linkedAccount = linkedAccountRepository.findByUsernameAndType(username, "ROBINHOOD");

        query
                .putBaseEndpoint(Endpoint.getBaseUrl())
                .putParamaterizedURI(Endpoint.ORDERS.getURI())
                .putHeader("Authorization", "bearer " + linkedAccount.getBearerToken());

        Flux<AccountOrder> orderHose = getRawFlux(query).flatMap(
                order -> robinhoodPaginatedProxy.queryAsMono(query.clearAll().putBaseEndpoint(order.get("instrument").toString()))
                .flatMap(instrument -> {

//                    Order resultOrder = new OrderBuilder()
//                            .setType(order.get("type"))
//                            .setSide(order.get("side"))
//                            .setPrice(order.get("price"))
//                            .setQuantity(order.get("quantity"))
//                            .setCreatedAt(order.get("createdAt"))
//                            .createOrder();


                    Order baseOrder = new ObjectMapper().convertValue(order, Order.class);

                    Arrays.stream(baseOrder.getClass().getDeclaredFields()).filter(
                            f -> (f.isAnnotationPresent(RobinhoodResponsePropertyURL.class) && !f.getAnnotation(RobinhoodResponsePropertyURL.class).requiresAuthentication())
                    ).forEach(
                            f -> {
                                try {
                                    Class<?> clazz = Class.forName(baseOrder.getClass().getCanonicalName());
                                    Field field = clazz.getDeclaredField(f.getName());
                                    field.setAccessible(true);
                                    field.set(baseOrder, ((LinkedHashMap<String, String>)instrument).get("symbol"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    );



                    return Mono.just(
                           AccountOrderSerializer.toEntity(baseOrder)
//                                    new OrderBuilder()
//                                            //.setInstrument(((LinkedHashMap<String, String>)instrument).get("symbol"))
//                                            .setType(order.get("type"))
//                                            .setSide(order.get("side"))
//                                            .setPrice(order.get("price"))
//                                            .setQuantity(order.get("quantity"))
//                                            .setCreatedAt(order.get("createdAt"))
//                                            .createOrder());
                    );
                        }
                )
        );

        System.out.println("DEBUG");
        return orderHose;


    }

//    public Flux<Order> getOrdersFlux(HttpQueryMeta query) {
//        return fetchPage(query).expand(
//                response -> {
//                    if (StringUtils.isEmpty(response.getNext())) {
//                        return Mono.empty();
//                    }
//                    return fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
//                }).flatMap(response -> Flux.fromIterable((List<Order>)response.getResults()));
//    }
//    public Mono<PaginatedRobinhoodHTTPResponse> fetchPage(HttpQueryMeta query) {
//        return robinhoodProxy.queryAsMono(query, typeReference);
//    }



    @Override
    public List<Object> findAll(String username, HttpQueryMeta query) {

        linkedAccount = linkedAccountRepository.findByUsernameAndType(username, "ROBINHOOD");

        query
             .putBaseEndpoint(Endpoint.getBaseUrl())
             .putParamaterizedURI(Endpoint.ORDERS.getURI())
             .putHeader("Authorization", "bearer " + linkedAccount.getBearerToken());

        PaginatedRobinhoodHTTPResponse response;
       // PaginatedRobinhoodHTTPResponse<Order> response;
        List<Object> allResults = new ArrayList<>();


        Mono<List<Order>> ordersPublisher = getOrders(query);

        Flux<Order> getOrdersFlux = getOrdersFlux(query);

        Flux<String> instrumentHose = instrumentHose(query);
        Mono<List<String>> instrumentMonoHose = instrumentMonoHose(query);
        //Flux<Object> instrumentStrings = getOrdersFlux.map(o -> o.getInstrument()).distinct().map(
                //i -> (robinhoodProxy.queryAsMono())
        //);
//        Flux<LinkedHashMap> instruments =
//
//
//                getOrdersFlux.map(o -> o.getInstrument()).distinct().map(
//                i -> (Mono<LinkedHashMap> )robinhoodProxy.queryAsMono(new HttpQueryMeta().putBaseEndpoint((String)i), Object.class).flatMap(
//                        r -> Flux.from((Mono<LinkedHashMap>)r)
//                ).
//        );

//        Flux<LinkedHashMap> instrumentsd = ordersPublisher.map(o -> o.getInstrument()).distinct().map(
//                i -> (Mono<LinkedHashMap>) robinhoodProxy.queryAsMono(new HttpQueryMeta().putBaseEndpoint((String)i), Object.class)
//        );

//        Flux.zip(getOrdersFlux, instruments, (o, i) -> {
//            if(((String) o.getInstrument()).contains(((LinkedHashMap)i).get("url").toString())){
//        }
//        });

//        Flux.zip(getOrdersFlux, instruments, (o, i) -> {
//            String oName = (String)o.getInstrument();
//            ((LinkedHashMap)i).get("url")
//               //if (icontains(oName)) {
//
//
//           })
//        })

        //Gets us a flux of instruments.
//        Flux<Order> test = getOrdersFlux.flatMap(
//                order -> {
//                    if (order.getInstrument() != null) {
//
//                        return robinhoodProxy
//                                .queryAsMono(new HttpQueryMeta().putBaseEndpoint((String)order.getInstrument()), Object.class).subscribe(o -> order.setInstrument(o));
//                    }
//                    return order;
//                }
//        );


        //.subscribe(o -> o.forEach(oe -> accountOrderRepository.save(AccountOrderSerializer.toEntity(oe))));
        do {
            //response = (PaginatedRobinhoodHTTPResponse<Order>) robinhoodProxy.queryAsMono(query, typeReference).block();
           //(Mono<PaginatedRobinhoodHTTPResponse<Order>>)

//           robinhoodProxy.queryAsMono(query, typeReference).subscribe(
//                    new BaseSubscriber<PaginatedRobinhoodHTTPResponse>() {
//                        @Override
//                        protected void hookOnSubscribe(Subscription subscription) {
//                            System.out.println("Subscribied to PaginatedRobinhoodHttpResponse!");
//                            //subscription.
//                            //accountOrderRepository.save();
//                            //request(1);
//                            //super.hookOnSubscribe(subscription);
//                        }
//
//                        @Override
//                        protected void hookOnNext(PaginatedRobinhoodHTTPResponse value) {
//                            super.hookOnNext(value);
//                        }
//
//                        @Override
//                        protected void hookOnComplete() {
//                            super.hookOnComplete();
//                        }
//
//
//                    }
//
//            ).;

//            robinhoodProxy.queryAsMono(query, typeReference).log()
//                    .doOnSubscribe(subs -> System.out.println("Subscribed: " + subs))
//                    .doOnRequest(request -> System.out.println("Request: " + request))
//                    .doOnSuccess(complete -> page((PaginatedRobinhoodHTTPResponse)complete))
//                    .subscribe(o -> accept((PaginatedRobinhoodHTTPResponse) o));

//            if (response!=null) {
//                //Flux<?> fluxTest = Flux.empty();
//                //Flux.fromIterable()
//
//                Flux.concat((response.getResults())).flatMap(t -> getEnrichmentMono(t).subscribe();
//                for (Order order : (List<Order>)response.getResults()) {
//                    Flux.concat(getEnrichmentMono(order));
//
//                }
//                ((List<Order>)response.getResults()).forEach(
//
//                        r -> {
//                            //fluxTest.merge(getEnrichmentMono(r));
//                           //enrichResult(r);
//                        }
//                );
//                System.out.println("TEST");
//
//            }



            //response = (PaginatedRobinhoodHTTPResponse<Order>) robinhoodProxy.queryAsMono(query, typeReference).block();

            //List<Order> paginatedOrders = response.getResults();


           // allResults.addAll(response.getResults());
//            query
//                    .clearAll()
//                    .putBaseEndpoint(response.getNext())
//                    .putHeader("Authorization", "bearer " + linkedAccount.getBearerToken());
        } while (false);
        //(response != null && response.getNext() != null);

         return allResults;

     }


//    public Flux<Object> instrumentHose2(HttpQueryMeta query) {
//        return fetchPage(query).expand(
//                response -> {
//                    if (StringUtils.isEmpty(response.getNext())) {
//                        return Mono.empty();
//                    }
//                    return
//                            fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()))
//                            .flatMapMany(m -> m.getResults()).toFlux();
//                })
//    }

    public Flux<String> instrumentHose(HttpQueryMeta query) {
        return fetchPage(query).expand(
                response -> {
                    if (StringUtils.isEmpty(response.getNext())) {
                        return Mono.empty();
                    }
                    return fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
                }).flatMap(response -> Flux.fromIterable((List<LinkedHashMap<String, Object>>)response.getResults()).map(m -> (String)m.get("instrument"))).distinct();
    }

    public Mono<List<String>> instrumentMonoHose(HttpQueryMeta query) {
        return fetchPage(query).expand(
                response -> {
                    if (StringUtils.isEmpty(response.getNext())) {
                        return Mono.empty();
                    }
                    return fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
                }).flatMap(response -> Flux.fromIterable((List<LinkedHashMap<String, Object>>)response.getResults()).map(m -> (String)m.get("instrument"))).distinct().collectList();
    }

    public Flux<LinkedHashMap<String, Object>> getRawFlux(HttpQueryMeta query) {
        return fetchPage(query).expand(
                response -> {
                    if (StringUtils.isEmpty(response.getNext())) {
                        return Mono.empty();
                    }
                    return fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
                }).flatMap(response -> Flux.fromIterable((List<LinkedHashMap<String, Object>>)response.getResults()));
    }

    public Mono<LinkedHashMap<String, Object>> fetchRawPage(HttpQueryMeta query) {
        return robinhoodProxy.queryAsMono(query, LinkedHashMap.class);
    }


    public Mono<List<LinkedHashMap<String, Object>>> getPageRaw(HttpQueryMeta query) {
        return fetchPage(query).expand(
                response -> {
                    if (StringUtils.isEmpty(response.getNext())) {
                        return Mono.empty();
                    }
                    return fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
                }).flatMap(response -> Flux.fromIterable((List<LinkedHashMap<String, Object>>)response.getResults())).collectList();
    }

    public Flux<Order> getOrdersFlux(HttpQueryMeta query) {
        return fetchPage(query).expand(
                response -> {
                    if (StringUtils.isEmpty(response.getNext())) {
                        return Mono.empty();
                    }
                    return fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
                }).flatMap(response -> Flux.fromIterable((List<Order>)response.getResults()));
    }




     public Mono<List<Order>> getOrders(HttpQueryMeta query) {
        return fetchPage(query).expand(
                response -> {
                    if (StringUtils.isEmpty(response.getNext())) {
                        return Mono.empty();
                    }
                    return fetchPage(query.clearAll().putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()).putBaseEndpoint(response.getNext()));
                }).flatMap(response -> Flux.fromIterable((List<Order>)response.getResults())).collectList();
     }

     public Mono<PaginatedRobinhoodHTTPResponse> fetchPage(HttpQueryMeta query) {
        return robinhoodProxy.queryAsMono(query, typeReference);
     }

//     public Flux<Order> fetchPageAsFlux(HttpQueryMeta query) {
//         Mono<PaginatedRobinhoodHTTPResponse<Order>> httpResponse = robinhoodProxy.queryAsMono(query, typeReference);
//         return ((List<Order>)httpResponse.flatMapMany(m -> m.getResults())).flux();
//     }

    private void page(PaginatedRobinhoodHTTPResponse response) {
        try {
            System.out.println("Attempting Next Page from Robinhood Paginated Response.");
            if (response.getNext()!= null) {
                robinhoodProxy.queryAsMono(
                        new HttpQueryMeta()
                            .putBaseEndpoint(response.getNext())
                            .putHeader("Authorization", "bearer " + linkedAccount.getBearerToken()), typeReference)
                        .doOnSubscribe(subs -> System.out.println("Subscribed: " + subs))
                        .doOnRequest(request -> System.out.println("Request: " + request))
                        .doOnSuccess(complete -> page((PaginatedRobinhoodHTTPResponse)complete))
                        .subscribe(o -> accept((PaginatedRobinhoodHTTPResponse) o));
            }
            //accountOrderRepository.saveAll(AccountOrderSerializer.toEntities(response.getResults()));
            //Files.write(Paths.get("app.log"), m.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IO Exception Logged.");
        }
    }
    private void accept(PaginatedRobinhoodHTTPResponse<Order> response) {
        try {
            //System.out.println(response.getResults().size());
            for (Order result : response.getResults()) {
                //robinhoodProxy.queryAsMono(new HttpQueryMeta().putBaseEndpoint(value), Object.class)
                enrichResult(result);
            }
            accountOrderRepository.saveAll(AccountOrderSerializer.toEntities(response.getResults()));
            //Files.write(Paths.get("app.log"), m.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IO Exception Logged.");
        }
    }


    @Async
     void enrichResult(Order result) {

         Arrays.stream(result.getClass().getDeclaredFields()).filter(
                 f -> (f.isAnnotationPresent(RobinhoodResponsePropertyURL.class) && !f.getAnnotation(RobinhoodResponsePropertyURL.class).requiresAuthentication())
         ).forEach(
                 f -> {
                     try {
                         Class<?> clazz = Class.forName(result.getClass().getCanonicalName());
                         Field field = clazz.getDeclaredField(f.getName());
                         field.setAccessible(true);
                         String value = (String) field.get(result);
                         field.set(result, robinhoodProxy.queryAsMono(new HttpQueryMeta().putBaseEndpoint(value), Object.class));
                         //Object linkedObject = robinhoodInstrumentCache.computeIfAbsent(value, k -> robinhoodProxy.queryAsMono(new HttpQueryMeta().putBaseEndpoint(k), Object.class).block());
                        // field.set(result,linkedObject);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
         );
        System.out.println("test");
     }

    @Async
    Mono<Object> getEnrichmentMono(Order result) {
        List<String> urls =
        Arrays.stream(result.getClass().getDeclaredFields()).filter(
                f -> (f.isAnnotationPresent(RobinhoodResponsePropertyURL.class) && !f.getAnnotation(RobinhoodResponsePropertyURL.class).requiresAuthentication())
        ).map(
                f -> {
                    try {
                        Class<?> clazz = Class.forName(result.getClass().getCanonicalName());
                        Field field = clazz.getDeclaredField(f.getName());
                        field.setAccessible(true);
                        String value = (String) field.get(result);
                        return value;
                        //Object linkedObject = robinhoodInstrumentCache.computeIfAbsent(value, k -> robinhoodProxy.queryAsMono(new HttpQueryMeta().putBaseEndpoint(k), Object.class).block());
                        // field.set(result,linkedObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).collect(Collectors.toList());
        if (urls != null) {
            System.out.println(urls.size());
            return robinhoodProxy.queryAsMono(new HttpQueryMeta().putBaseEndpoint(urls.get(0)), Object.class);
        }
        return null;
    }

}
