package com.drsf.polygon;

import com.drsf.polygon.config.PolygonEndpoint;
import com.drsf.polygon.config.UserConf;
import com.drsf.polygon.model.HistoricQuotes;
import com.drsf.polygon.model.OpenClose;
import com.drsf.polygon.service.MockUserConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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


    private WebClient buildWebClient() {
        //return null;
        return WebClient.builder().baseUrl("https://api.polygon.io/").exchangeStrategies(ExchangeStrategies.builder().codecs(config -> config.defaultCodecs().maxInMemorySize(16*1024*1024)).build()).build();
    }

}
