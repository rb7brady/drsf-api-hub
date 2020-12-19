package com.drsf.api.robinhood.model;

import com.drsf.api.entities.AccountOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ManyToOne;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Execution {
    @JsonProperty("price")
    protected Object price;
    @JsonProperty("quantity")
    protected Object quantity;
    @JsonProperty("settlement_date")
    protected Object settlementDate;
    @JsonProperty("timestamp")
    protected Object timestamp;

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getQuantity() {
        return quantity;
    }

    public void setQuantity(Object quantity) {
        this.quantity = quantity;
    }

    public Object getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Object settlementDate) {
        this.settlementDate = settlementDate;
    }
}
