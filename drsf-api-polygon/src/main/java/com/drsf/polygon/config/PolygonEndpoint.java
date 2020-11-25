package com.drsf.polygon.config;


import com.drsf.polygon.model.*;

public enum PolygonEndpoint {
    GROUPED_DAILY("/v2/aggs/grouped/locale/US/market/STOCKS/{sym}?apiKey=", GroupedDaily.class),
    OPEN_CLOSE("/v1/open-close/{sym}/{date}?apiKey={key}", OpenClose.class),
    HISTORIC_NBBO("/v2/ticks/stocks/nbbo/{sym}/{date}?apiKey={key}", HistoricQuotes.class),
    FINANCIALS("/v2/reference/financials/{symbol}?apiKey={key}", Financials.class),
    DIVIDENDS("/v2/reference/dividends/{sym}?apiKey={key}",Dividends .class);

    public String baseUrl = "https://api.polygon.io/";
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

    public String build(String[] params, String apiKey) {
        return String.format(url, params) + apiKey;
    }

}


