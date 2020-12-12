package com.drsf.api;

import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.*;

public class MonoFluxModelTests {



    public LinkedHashMap<String, Object> createModel() {

        LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
        List<LinkedHashMap<String,Object>> results = new ArrayList<LinkedHashMap<String, Object>>();
        // map.put
        results.add(generateResult("AAPL", "2020-05-08", "2020-05-14", "2020-05-11", 0.82));
        results.add(generateResult("AAPL", "2020-02-07", "2020-02-13", "2020-02-10", 0.77));

        map.put("results", results);
        map.put("status", "OK");
        map.put("count", 2);
        return map;

    }


    public LinkedHashMap<String,Object> generateResult(String ticker, String exDate, String payDate, String recDate, Double amount) {
        LinkedHashMap<String,Object> results = new LinkedHashMap<String, Object>();
        results.put("ticker", ticker);
        results.put("exDate", exDate);
        results.put("paymentDate", payDate);
        results.put("recordDate", recDate);
        results.put("amount", amount);
        return results;
    }


    @Test
    public void testMono() {
        //Mono<Object> myMono = Mono.just
       // Mono.just(createModel());

    }

}
