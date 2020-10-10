package com.drsf.polygon;

import com.drsf.polygon.config.PolygonEndpoint;
import com.drsf.polygon.config.UserConf;
import com.drsf.polygon.model.HistoricQuotes;
import com.drsf.polygon.model.OpenClose;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;

public class PolygonRouter {

    public PolygonRouter(UserConf userConf) {

        //Request Generic Quote Example
        ///invokeRoute(userConf, "AAPL");


    }
    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();


    private void invokeRoute(UserConf userConf, String ... params) {
        String responseString = StatelessRestConsumer.getPolygonResponseRaw(PolygonEndpoint.OPEN_CLOSE.build(params, userConf.getAlpacaPublic()), restTemplateBuilder.build(), null);
        System.out.println(responseString);


        Object responseObject = StatelessRestConsumer.getPolygonResponse(PolygonEndpoint.OPEN_CLOSE.build(params, userConf.getAlpacaPublic()), restTemplateBuilder.build(), null, new ParameterizedTypeReference<HistoricQuotes>(){});

        System.out.println(responseObject.getClass().getSimpleName());
        if (responseObject instanceof String) {
            System.out.println(responseObject);
        } else {
            System.out.println(responseObject);
        }


        Object responseCast = StatelessRestConsumer.getPolygonResponseCasted(PolygonEndpoint.OPEN_CLOSE.build(params, userConf.getAlpacaPublic()), restTemplateBuilder.build(), null, HistoricQuotes.class);
        Object responseCast2 = StatelessRestConsumer.getPolygonResponseCasted(PolygonEndpoint.OPEN_CLOSE.build(params, userConf.getAlpacaPublic()), restTemplateBuilder.build(), null, OpenClose.class);


    }


}
