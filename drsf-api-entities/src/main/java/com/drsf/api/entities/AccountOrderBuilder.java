package com.drsf.api.entities;

import java.util.Date;

public class AccountOrderBuilder {
    private long id;
    private LinkedAccount linkedAccount;
    private String ticker;
    private Boolean buy;
    private String type;
    private Double price;
    private Double quantity;
    private Date submitted;
    private String tif;

    public AccountOrderBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public AccountOrderBuilder setLinkedAccount(LinkedAccount linkedAccount) {
        this.linkedAccount = linkedAccount;
        return this;
    }

    public AccountOrderBuilder setTicker(String ticker) {
        this.ticker = ticker;
        return this;
    }

    public AccountOrderBuilder setBuy(Boolean buy) {
        this.buy = buy;
        return this;
    }

    public AccountOrderBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public AccountOrderBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public AccountOrderBuilder setQuantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public AccountOrderBuilder setSubmitted(Date submitted) {
        this.submitted = submitted;
        return this;
    }

    public AccountOrderBuilder setTif(String tif) {
        this.tif = tif;
        return this;
    }

    public AccountOrder createAccountOrder() {
        return new AccountOrder(id, linkedAccount, ticker, buy, type, price, quantity, submitted, tif);
    }
}