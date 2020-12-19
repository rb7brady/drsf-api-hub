package com.drsf.api.polygon.model;

import com.drsf.api.polygon.annotations.persist;

import java.util.Date;


public class DividendResult {

    String ticker;
    @persist
    Date exDate;
    @persist
    Date paymentDate;
    @persist(key = true)
    Date recordDate;
    @persist
    Date declaredDate;
    @persist
    double amount;

    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public Date getExDate() {
        return exDate;
    }
    public void setExDate(Date exDate) {
        this.exDate = exDate;
    }
    public Date getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    public Date getRecordDate() {
        return recordDate;
    }
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Date getDeclaredDate() {
        return declaredDate;
    }
    public void setDeclaredDate(Date declaredDate) {
        this.declaredDate = declaredDate;
    }
}
