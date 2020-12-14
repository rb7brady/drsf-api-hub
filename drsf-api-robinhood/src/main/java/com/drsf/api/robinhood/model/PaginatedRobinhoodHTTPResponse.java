package com.drsf.api.robinhood.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaginatedRobinhoodHTTPResponse<T> {

    public PaginatedRobinhoodHTTPResponse(){}

    @JsonProperty("next")
    String next;
    @JsonProperty("previous")
    String previous;
    @JsonProperty("results")
    List<T> results;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "PaginatedRobinhoodHTTPResponse{" +
                "next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }

}
