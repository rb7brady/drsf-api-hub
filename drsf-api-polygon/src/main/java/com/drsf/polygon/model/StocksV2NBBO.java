package com.drsf.polygon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StocksV2NBBO {
    public StocksV2NBBO() {}
    @JsonProperty("T")
    String T;
    @JsonProperty("t")
    String t;
    @JsonProperty("y")
    double y;
    @JsonProperty("f")
    double f;
    @JsonProperty("q")
    double q;
    @JsonProperty("p")
    double p;
    @JsonProperty("x")
    double x;
    @JsonProperty("s")
    double s;
    @JsonProperty("P")
    double P;
    @JsonProperty("X")
    double X;
    @JsonProperty("S")
    double S;

    @Override
    public String toString() {
        return "StocksV2NBBO{" +
                "T='" + T + '\'' +
                ", t='" + t + '\'' +
                ", y=" + y +
                ", f=" + f +
                ", q=" + q +
                ", p=" + p +
                ", x=" + x +
                ", s=" + s +
                ", P=" + P +
                ", X=" + X +
                ", S=" + S +
                '}';
    }
}

