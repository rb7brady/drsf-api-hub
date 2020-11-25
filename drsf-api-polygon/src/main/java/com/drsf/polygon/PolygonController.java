package com.drsf.polygon;

import com.drsf.polygon.config.PolygonEndpoint;
import com.drsf.polygon.config.UserConf;
import com.drsf.polygon.model.DividendResult;
import com.drsf.polygon.model.Dividends;
import com.drsf.polygon.model.HistoricQuotes;
import com.drsf.polygon.model.OpenClose;
import com.drsf.polygon.service.DataStoreService;
import com.drsf.polygon.service.MockUserConfigurationService;
import com.drsf.polygon.service.PolygonProxy;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
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
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RestController
public class PolygonController {
    MockUserConfigurationService mockUserConfigurationService = new MockUserConfigurationService();
    private UserConf userConf = mockUserConfigurationService.findConf();

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

    @GetMapping("/oc")
    public String oc(@RequestParam(value = "sym") String sym) {
        PolygonReqResWebClient client = new PolygonReqResWebClient();
        return client.getOC(sym, userConf.getAlpacaPublic());
    }

    /**
     * This method correctly invokes the web service for NBBO. It fails to print out to the browser
     * or console log.
     *
     * NOTE: FLUX FROM CLIENT

     */
    @GetMapping("/nbbo")
    public String nbbo(@RequestParam(value = "sym") String sym, @RequestParam(value = "date") String date) {
        PolygonReqResWebClient client = new PolygonReqResWebClient();

        return client.listNBBO(sym, date, userConf.getAlpacaPublic()).collectList().toString();
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
//    @GetMapping("/flux")
//    public Flux<?> testGenericFluxResponse(@RequestParam(value = "sym") String sym, @RequestParam(value = "date") String date) {
//        PolygonReqResWebClient client = new PolygonReqResWebClient();
//       // return client.g
//    }


    @GetMapping("/div")
    public void div(@RequestParam(value = "sym") String sym) {

        Mono<Object> obj = PolygonProxy.getMonoObject(PolygonEndpoint.DIVIDENDS, sym,userConf.getAlpacaPublic());


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
                            method.invoke(p, DateTimeFormatter.ISO_LOCAL_DATE.parse(new Gson().fromJson(member.getValue(), String.class)).getLong(ChronoField.EPOCH_DAY));
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
        Gson gson = new GsonBuilder().registerTypeAdapter(DividendResult.class, deserializer).create();
        gson.fromJson(obj.block().toString(), Dividends.class);

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
