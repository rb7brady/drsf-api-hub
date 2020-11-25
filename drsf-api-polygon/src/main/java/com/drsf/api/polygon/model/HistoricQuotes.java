package com.drsf.api.polygon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricQuotes {
    public HistoricQuotes(){}
    @JsonProperty("results_count")
    int resultsCount;
    @JsonProperty("db_latency")
    int dbLatency;
    @JsonProperty("success")
    boolean success;
    @JsonProperty("ticker")
    String ticker;
    @JsonProperty("results")
    List<StocksV2NBBO> results;

    @Override
    public String toString() {
        return "HistoricQuotes{" +
                "resultsCount=" + resultsCount +
                ", dbLatency=" + dbLatency +
                ", success=" + success +
                ", ticker='" + ticker + '\'' +
                ", results=" + results +
                '}';
    }
}
