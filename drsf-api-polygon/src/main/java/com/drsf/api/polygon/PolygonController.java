package com.drsf.api.polygon;

import com.drsf.api.polygon.config.PolygonEndpoint;
import com.drsf.api.polygon.config.UserConf;
import com.drsf.api.polygon.model.DividendResult;
import com.drsf.api.polygon.model.Dividends;
import com.drsf.api.polygon.model.HistoricQuotes;
import com.drsf.api.polygon.service.MockUserConfigurationService;
import com.drsf.api.polygon.service.PolygonProxy;
import com.drsf.api.entities.Dividend;
import com.dsrf.api.meta.HttpQueryMeta;
import com.google.gson.*;
import com.drsf.api.postgres.repositories.DividendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PolygonController {
    MockUserConfigurationService mockUserConfigurationService = new MockUserConfigurationService();
    private UserConf userConf = mockUserConfigurationService.findConf();

    @Autowired
    DividendRepository repository;

//    @Autowired
//    SimpleJpaRepository simpleJpaRepository;
//
//    @Autowired
//    SimpleJpaRepository<Dividend, Long> simpleJpaRepository;
    
    @Autowired
    PolygonProxy polygonProxy;

    private void PolygonController() {
        MockUserConfigurationService mockUserConfigurationService = new MockUserConfigurationService();
        this.userConf = mockUserConfigurationService.findConf();
    }

    @GetMapping("/trigger")
    public String trigger() {
        PolygonReqResWebClient client = new PolygonReqResWebClient();
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        return client.getResult();
    }

    /**
     * example: http://localhost:8080/oc?sym=AAPL&date=2020-01-03
     */
    @GetMapping("/oc")
    public Object oc(@RequestParam(value = "sym") String sym, @RequestParam(value="date") String date) {

        HttpQueryMeta query = new HttpQueryMeta();
        query.putBaseEndpoint(PolygonEndpoint.getBaseUrl());
        query.putParamaterizedURI(PolygonEndpoint.OPEN_CLOSE.getURI());
        query.putURIParamValue(sym);
        query.putURIParamValue(date);
        query.putParameterizedOptional("apiKey", userConf.getAlpacaPublic());
        return polygonProxy.queryAsMono(query).block();

    }


    /**
     * http://localhost:8080/nbbo?sym=AAPL&date=2020-01-03
     * NOTE: FLUX FROM CLIENT

     */
    @GetMapping("/nbbo")
    public Object nbbo(@RequestParam(value = "sym") String sym, @RequestParam(value = "date") String date) {

        HttpQueryMeta query = new HttpQueryMeta();
        query.putBaseEndpoint(PolygonEndpoint.getBaseUrl());
        query.putParamaterizedURI(PolygonEndpoint.HISTORIC_NBBO.getURI());
        query.putURIParamValue(sym);
        query.putURIParamValue(date);
        query.putParameterizedOptional("apiKey", userConf.getAlpacaPublic());
        return polygonProxy.queryAsMono(query).block();
        //">> Result Flux<NBBO> = " + listNBBO(params).collectList().doOnNext(a -> System.out.println(a)).subscribe();
    }


    //TODO: SANDBOX
    /**
     * Sandbox method for testing and experimenting. This method will return a massive text response to the browser.
     *  Example URL: http://localhost:8080/mono?sym=AAPL&date=2020-10-01
     *
     *  NOTE: Usage of .retreive in place of .exchange here.
     *
     *  Also updated to use a mono instead of a flux. Not sure why it works as a mono.
     *
     * @param sym
     * @param date
     * @return
     */
    @GetMapping("/mono")
    public Mono<HistoricQuotes> testGenericMonoResponse(@RequestParam(value = "sym") String sym, @RequestParam(value = "date") String date) {
        PolygonReqResWebClient client = new PolygonReqResWebClient();
        return client.getMonoNBBO(sym, date, userConf.getAlpacaPublic());
    }


    @GetMapping("/mono/financial")
    public Object fin(@RequestParam(value = "symbol") String sym) {
        PolygonReqResWebClient client = new PolygonReqResWebClient();
        Mono<Object> obj = client.getMonoObject(sym,userConf.getAlpacaPublic());
        try {
            System.out.println(obj.block());
            System.out.println(obj.toString());
            Files.write(Paths.get("app.log"), Collections.singleton("A"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @GetMapping("/financials")
    public Object finance(@RequestParam(value = "sym") String sym) {
        HttpQueryMeta query = new HttpQueryMeta();
        query.putBaseEndpoint(PolygonEndpoint.getBaseUrl());
        query.putParamaterizedURI(PolygonEndpoint.FINANCIALS.getURI());
        query.putURIParamValue(sym);
        query.putParameterizedOptional("apiKey", userConf.getAlpacaPublic());
        return polygonProxy.queryAsMono(query).block();

    }
    
    //TODO: SANDBOX
    /**
     * Sandbox method for testing and experimenting. This method will return a massive text response to the browser.
     *  Example URL: http://localhost:8080/mono?sym=AAPL&date=2020-10-01
     *
     *  NOTE: Usage of .retreive in place of .exchange here.
     *
     *  Also updated to use a mono instead of a flux. Not sure why it works as a mono.
     *
     * @param sym
     *
     * @return
     */
//    @GetMapping("/flux")
//    public Flux<?> testGenericFluxResponse(@RequestParam(value = "sym") String sym, @RequestParam(value = "date") String date) {
//        PolygonReqResWebClient client = new PolygonReqResWebClient();
//       // return client.g
//    }


    @GetMapping("/div")
    public Object div(@RequestParam(value = "sym") String sym) {

        HttpQueryMeta query = new HttpQueryMeta();
        query.putBaseEndpoint(PolygonEndpoint.getBaseUrl());
        query.putParamaterizedURI(PolygonEndpoint.DIVIDENDS.getURI());
        query.putURIParamValue(sym);
        query.putParameterizedOptional("apiKey", userConf.getAlpacaPublic());

        //String filePath = "src/main/resources/dataStore/" + "dividends/" + sym + ".csv";
        //Mono<Object> convertedMonoEntity = polygonProxy.queryAsMono(query);
        ///Mono<LinkedHashMap> convertedMonoEntity2 = polygonProxy.queryAsMono2(query);

        //Mono<String> convertedMono = (Mono<String>) polygonProxy.queryAsMono(query).map(m -> ((HashMap<?,?>)m).getOrDefault("test", null));

//        polygonProxy
//                .queryAsMono(query)
//                .flatMapIterable(
//                        m->
//                                (List) ((List)
//                                        ((LinkedHashMap)m).get("results"))
//                                            .stream()
//                                            .map(
//                                                    i->((LinkedHashMap)i).get("exDate"))
//                                            .collect(Collectors.toList()));


        Mono<String> convertedMono = (Mono<String>) polygonProxy.queryAsMono(query).map(m -> ((HashMap<?,?>)m).getOrDefault("test", null));
        polygonProxy.queryAsMono(query).subscribe(

                m -> {
                    //Gson gson = new GsonBuilder().registerTypeAdapter(DividendResult.class, deserializer).create();

                    Dividends dividend = new Gson().fromJson(m.toString(), Dividends.class);

                    for (DividendResult div : dividend.getResults()) {
                        Dividend dividendJPA = new Dividend();
                        dividendJPA.setTicker(div.getTicker());
                        dividendJPA.setRecordDate(div.getRecordDate());
                        dividendJPA.setPaymentDate(div.getPaymentDate());
                        dividendJPA.setExDate(div.getExDate());
                        dividendJPA.setAmount(div.getAmount());
                        dividendJPA.setDeclaredDate(div.getDeclaredDate());

                        try {
                            repository.save(dividendJPA);
                        } catch (DataIntegrityViolationException e) {
                            //TODO Add Optional Logging.
                            //This Catch Block handles Duplicate inserts and is expected to be hit often.
                        }
                    }
                }

        );
//        polygonProxy
//                .queryAsMono(query)
//                .flatMapIterable(
//                        m->
//                                (List) ((List)
//                                        ((LinkedHashMap)m).get("results"))
//                                            .stream()
//                                            .map(
//                                                    i->((LinkedHashMap)i).get("exDate"))
//                                            .collect(Collectors.toList()));

        ((Flux<String>)polygonProxy.queryAsMono(query).flatMapIterable(
                m->
                        (List) ((List)
                                ((LinkedHashMap)m).get("results"))
                                .stream()
                                .map(
                                        i->((LinkedHashMap<String,String>)i).get("exDate"))
                                .collect(Collectors.toList()))).subscribe(f -> {
            try {
                System.out.println("WRITE TEST");
                //Files.write(Paths.get("app.log"), "APP LOG WRITE".getBytes());
                Files.write(Paths.get("/Users/ryanbrady/Workspace/store/test.csv"), f.getBytes());
            } catch (IOException e) {

                e.printStackTrace();
            }
        });

//        ((Flux<String>)polygonProxy.queryAsMono(query).flatMapIterable(
//                m->
//                        (List) ((List)
//                                ((LinkedHashMap)m).get("results"))
//                                .stream()
//                                .map(
//                                        i->((LinkedHashMap<String,String>)i).get("exDate"))
//                                .collect(Collectors.toList()))).subscribe(f -> {
//            try {
//                Files.write(Paths.get("app.log"), "TEST LOG".getBytes());
//
//                Files.write(Paths.get("/Users/ryanbrady/Workspace/store/test.csv"), f.getBytes());
//                Files.write(Paths.get("/Users/ryanbrady/Workspace/store/test.csv"), f.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).dispose();


       // LocalStorageCSVProxy.save(convertedMono, filePath);

        //Flux<String> convertedMono = polygonProxy.queryAsMono(query).flatMapMany(m -> ((LinkedHashMap<String,Object>)m).get("results"));
       // LocalStorageCSVProxy.save((Mono<String>) polygonProxy.queryAsMono(query).flatMap(m -> ((LinkedHashMap<String,Object>)m).get("results").toString()), filePath);


        //return polygonProxy.queryAsMono(query).block();
//        Mono<Object> obj = PolygonProxy.getMonoObject(PolygonEndpoint.DIVIDENDS, sym,userConf.getAlpacaPublic());
//
//        Map<String, Object> polygonQuery = new HashMap<>();
//
//
//
        JsonDeserializer<DividendResult> deserializer = new JsonDeserializer<DividendResult>() {
            @Override
            public DividendResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                DividendResult p = new DividendResult();

                for (Map.Entry<String, JsonElement> member : jsonElement.getAsJsonObject().entrySet()) {
                    try {
                        Method method = Arrays
                                .stream(p.getClass().getMethods())
                                .filter(m -> m.getName().equalsIgnoreCase("set" + member.getKey()))
                                .findFirst()
                                .get();
                        Field field = p.getClass().getDeclaredField(member.getKey());
                        if (field.getName().contains("Date")) {
                            method.invoke(p, DateTimeFormatter.ISO_LOCAL_DATE.parse(new Gson().fromJson(member.getValue(), String.class)).get(ChronoField.EPOCH_DAY));
                        } else {
                            method.invoke(p, new Gson().fromJson(member.getValue(),field.getType()));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                return p;
            }
        };


//        Mono<Object> obj = polygonProxy.queryAsMono(query);
//
//        Gson gson = new GsonBuilder().registerTypeAdapter(DividendResult.class, deserializer).create();
//
//        Dividends dividend = new Gson().fromJson(obj.block().toString(), Dividends.class);
//
//        for (DividendResult div : dividend.getResults()) {
//            Dividend dividendJPA = new Dividend();
//            dividendJPA.setTicker(div.getTicker());
//            dividendJPA.setRecordDate(div.getRecordDate());
//            dividendJPA.setPaymentDate(div.getPaymentDate());
//            dividendJPA.setExDate(div.getExDate());
//            dividendJPA.setAmount(div.getAmount());
//            dividendJPA.setDeclaredDate(div.getDeclaredDate());
//
//            dividendRepository.save(dividendJPA);
//
//        }

        return null;

//        Gson gson = new GsonBuilder().registerTypeAdapter(DividendResult.class, deserializer).create();
//        gson.fromJson(obj.block().toString(), Dividends.class);

//        JsonDeserializer<DividendResult> deserializer = new JsonDeserializer<Dividends>() {
//            @Override
//            public Dividends deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                JsonObject jsonObject = jsonElement.getAsJsonObject();
//
//                //Long unixDate =
//
//            }
//        }


        //new GsonBuilder().
        //DataStoreService.merge(String, sym, gson.fromJson(obj.block().toString(), Dividends.class));




        //Dividends dividend = new Gson().fromJson(obj.block().toString(), Dividends.class);

//        try {
////            System.out.println(obj.block());
////            System.out.println(obj.toString());
////            Files.write(Paths.get("app.log"), Collections.singleton("A"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //return obj;
    }

    private WebClient buildWebClient() {
        //return null;
        return WebClient.builder().baseUrl("https://api.polygon.io/").exchangeStrategies(ExchangeStrategies.builder().codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024)).build()).build();
    }

}
