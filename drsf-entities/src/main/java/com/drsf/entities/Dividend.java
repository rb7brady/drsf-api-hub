package com.drsf.api.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Dividend extends SecurityBase implements SecurityEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    protected Date exDate;
    protected Date paymentDate;
    protected Date recordDate;
    protected Date declaredDate;
    protected double amount;
    protected String ticker;

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

    public Date getDeclaredDate() {
        return declaredDate;
    }

    public void setDeclaredDate(Date declaredDate) {
        this.declaredDate = declaredDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String getTicker() {
        return ticker;
    }

    @Override
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
