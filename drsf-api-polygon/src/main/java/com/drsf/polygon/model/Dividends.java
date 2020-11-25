package com.drsf.polygon.model;

import java.util.Date;
import java.util.List;

public class Dividends {

    String status;
    int count;
    List<DividendResult> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DividendResult> getResults() {
        return results;
    }

    public void setResults(List<DividendResult> results) {
        this.results = results;
    }

}



