package com.drsf.api.polygon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    public Quote(){}

    @JsonProperty("T")
    String T;
    @JsonProperty("v")
    double v;
    @JsonProperty("o")
    double o;
    @JsonProperty("c")
    double c;
    @JsonProperty("h")
    double h;
    @JsonProperty("l")
    double l;
    @JsonProperty("t")
    double t;


    @Override
    public String toString() {
        return "Quote{" +
                "T='" + T + '\'' +
                ", v=" + v +
                ", o=" + o +
                ", c=" + c +
                ", h=" + h +
                ", l=" + l +
                ", t=" + t +
                '}';
    }
}
