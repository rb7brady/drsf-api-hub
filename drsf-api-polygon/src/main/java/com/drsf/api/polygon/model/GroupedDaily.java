package com.drsf.api.polygon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupedDaily {
    public GroupedDaily(){}
    String status;
    int queryCount;
    int resultsCount;
    boolean adjusted;
    List<Quote> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    public boolean isAdjusted() {
        return adjusted;
    }

    public void setAdjusted(boolean adjusted) {
        this.adjusted = adjusted;
    }

    public List<Quote> getResults() {
        return results;
    }

    public void setResults(List<Quote> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GroupedDaily{" +
                "status='" + status + '\'' +
                ", queryCount=" + queryCount +
                ", resultsCount=" + resultsCount +
                ", adjusted=" + adjusted +
                ", results=" + results +
                '}';
    }
}
