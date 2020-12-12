package com.drsf.api.polygon.config;


import com.drsf.api.polygon.model.*;

import java.util.ServiceLoader;

public enum PolygonEndpoint {
    GROUPED_DAILY("/v2/aggs/grouped/locale/US/market/STOCKS/", GroupedDaily.class),
    OPEN_CLOSE("/v1/open-close/", OpenClose.class),
    HISTORIC_NBBO("/v2/ticks/stocks/nbbo/", HistoricQuotes.class),
    FINANCIALS("/v2/reference/financials/", Financials.class),
    DIVIDENDS("v2/reference/dividends/",Dividends.class);

    public static String baseUrl = "https://api.polygon.io/";
    private String apiKeyParam = "?apiKey={key}";


    String url;
    Class responseType;

    PolygonEndpoint(String url, Class responseType){
        this.url = url;
        this.responseType = responseType;

    }

    public Class getResponseType() {
        return this.responseType;
    }

    public String getURI() {
        return this.url;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public String build(String[] params, String apiKey) {
        return String.format(url, params) + apiKey;
    }

}


