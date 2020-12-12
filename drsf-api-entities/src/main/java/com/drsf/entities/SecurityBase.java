package com.drsf.entities;

import java.io.Serializable;

public abstract class SecurityBase implements Serializable {

    protected String ticker;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
