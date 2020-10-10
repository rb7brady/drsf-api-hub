package com.drsf.polygon.config;


import com.drsf.polygon.model.GroupedDaily;
import com.drsf.polygon.model.HistoricQuotes;
import com.drsf.polygon.model.OpenClose;

public enum PolygonEndpoint {
    GROUPED_DAILY("/v2/aggs/grouped/locale/US/market/STOCKS/{sym}?apiKey=", GroupedDaily.class),
    OPEN_CLOSE("/v1/open-close/{sym}/{date}?apiKey={key}", OpenClose.class),
    HISTORIC_NBBO("/v2/ticks/stocks/nbbo/{sym}/{date}?apiKey={key}", HistoricQuotes.class);

    public String baseUrl = "https://api.polygon.io/";


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


