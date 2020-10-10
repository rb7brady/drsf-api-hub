package com.drsf.polygon;

import com.drsf.polygon.config.PolygonEndpoint;
import com.drsf.polygon.model.HistoricQuotes;
import com.drsf.polygon.model.OpenClose;
import com.drsf.polygon.model.StocksV2NBBO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PolygonReqResWebClient {

    private static final String BASE_URL = "https://api.polygon.io/";
    private static final String OC = "/v1/open-close/{sym}/2019-02-01?apiKey={key}";
    private WebClient webClient = buildWebClient();
            //WebClient.create(BASE_URL);
    //private WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");

    private WebClient buildWebClient() {
        //return null;
        return WebClient.builder().baseUrl(BASE_URL).exchangeStrategies(ExchangeStrategies.builder().codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024)).build()).build();
    }

    private Mono<ClientResponse>  responseMono = webClient.get().accept(MediaType.APPLICATION_JSON).exchange();


    public String getResult() {
        return ">> Result Mono<ClientResponse> = " + responseMono.flatMap(res -> res.bodyToMono(String.class)).block();
    }


    public Flux<OpenClose> getOpenClose(String sym, String key) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        return webClient.get().uri(OC, sym, key).retrieve().bodyToFlux(OpenClose.class);
    }

    public String getOC(String sym, String key) {
        return ">> Get Flux<OpenClose> retrieve example = " + getOpenClose(sym, key).collectList().doOnNext(a -> System.out.println(a)).subscribe();
    }

    public Flux<Object> listNBBO(String ... params) {
        return webClient.get().uri(PolygonEndpoint.HISTORIC_NBBO.getURI(), params).exchange().flatMapMany(a -> a.bodyToFlux(Object.class));
    }

    public String getNBBO(String ... params) {
        StringBuilder stringBuilder = new StringBuilder();
        return ">> Result Flux<NBBO> = " + listNBBO(params).collectList().doOnNext(a -> System.out.println(a))
                .subscribe();
    }

    public Mono<HistoricQuotes> getMonoNBBO(String ... params) {
        return buildWebClient().get().uri(PolygonEndpoint.HISTORIC_NBBO.getURI(), params).retrieve().bodyToMono(HistoricQuotes.class);
    }


}
