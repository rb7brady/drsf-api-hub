package com.drsf.api.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Execution {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    protected AccountOrder accountOrder;

    protected Double price;
    protected Double quantity;
    protected Date settlementDate;
    protected Date timestamp;

    public Execution() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountOrder getAccountOrder() {
        return accountOrder;
    }

    public void setAccountOrder(AccountOrder accountOrder) {
        this.accountOrder = accountOrder;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }
}
