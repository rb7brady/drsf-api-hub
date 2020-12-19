package com.drsf.api.robinhood;

public enum Endpoint {
    ORDERS("/orders/");

    public static String baseUrl = "https://api.robinhood.com/";

    String url;

    Endpoint(String url){
        this.url = url;
    }

    public String getURI() {
        return this.url;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }


}
